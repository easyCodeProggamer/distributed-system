package com.example.upload.fileUpload.controller;

import com.example.upload.fileUpload.model.ContentData;
import com.example.upload.fileUpload.service.ContentProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private ContentProducerService contentProducerService;
    private static final String UPLOAD_DIR = "temp-producer-uploads/";

    @PostMapping
    public ResponseEntity<String> uploadChunk(@RequestParam("file") MultipartFile file,
                                              @RequestParam("chunkId") int chunkId,
                                              @RequestParam("fileName") String fileName,
                                              @RequestParam("fileId") String fileId,
                                              @RequestParam("isFinalChunk") boolean isFinalChunk,
                                              @RequestParam(value = "originalFileHash", required = false) String originalFileHash) throws IOException {
        ContentData contentData = getContentDataObject(file,chunkId,fileName,fileId,isFinalChunk,originalFileHash,true);
        try {
            File dir = new File(UPLOAD_DIR + fileId);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path path = Paths.get(dir.getAbsolutePath(), String.valueOf(chunkId));
            Files.write(path, file.getBytes());


            if (isFinalChunk) {
                // If this is the final chunk, trigger the merge, verification, and upload
                boolean isVerified = mergeAndVerify(contentData);
                contentData.setFileCorrupted(!isVerified);
                if (isVerified) {
                    contentProducerService.send(contentData);
                    return ResponseEntity.ok("File uploaded, merged, and verified successfully.");
                } else {
                    contentProducerService.send(contentData);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File verification failed. Hashes do not match.");
                }
            }
            //Publish chunk
            contentProducerService.send(contentData);
            return ResponseEntity.ok("Chunk uploaded successfully");

        } catch (Exception e) {
            //Publish to all consumers to delete all corrupted chunks
            contentProducerService.send(contentData);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload chunk: " + e.getMessage());
        }
    }

    private boolean mergeAndVerify(ContentData contentData) throws IOException, NoSuchAlgorithmException {
        File dir = new File(UPLOAD_DIR + contentData.getFileId());
        File[] chunks = dir.listFiles();
        if (chunks == null) {
            return false;
        }

        Arrays.sort(chunks, Comparator.comparingInt(f -> Integer.parseInt(f.getName())));

        File mergedFile = new File(UPLOAD_DIR + contentData.getFileName());
        try (FileOutputStream fos = new FileOutputStream(mergedFile, false)) { // Use false to overwrite if file exists
            for (File chunk : chunks) {
                Files.copy(chunk.toPath(), fos);
                chunk.delete();
            }
        }
        dir.delete();

        // 3. Generate hash of the merged file
        String mergedFileHash = calculateFileHash(mergedFile);

        // 4. Compare the hashes
        if (contentData.getOriginalFileHash().equals(mergedFileHash)) {
            System.out.println("File verification successful. Hashes match.");
            // Hashes match, proceed with Google Drive upload
            uploadToGoogleDrive(mergedFile, contentData.getFileName());
            return true;
        } else {
            System.err.println("File verification failed. Hashes do not match.");
            System.err.println("Client hash: " + contentData.getOriginalFileHash());
            System.err.println("Server hash: " + mergedFileHash);
            mergedFile.delete(); // Clean up the corrupt merged file
            return false;
        }
    }

    /**
     * Calculates the SHA-256 hash of a file.
     * @param file The file to hash.
     * @return The hex-encoded hash string.
     */
    private String calculateFileHash(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                sha256.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = sha256.digest();

        // Convert byte array to hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // uploadToGoogleDrive method remains the same, but now it also deletes the file
    private void uploadToGoogleDrive(File file, String fileName) {
        try {
            // ... (call googleDriveService)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //file.delete(); // Important: Clean up the merged file after upload
        }
    }

    private ContentData getContentDataObject(MultipartFile file, int chunkId, String fileName, String fileId, boolean isFinalChunk,
     String originalFileHash, boolean isFileCorrupted) throws IOException {
        ContentData contentData = new ContentData();
        contentData.setFile(file.getBytes());
        contentData.setChunkId(chunkId);
        contentData.setFileId(fileId);
        contentData.setFileName(fileName);
        contentData.setFinalChunk(isFinalChunk);
        contentData.setOriginalFileHash(originalFileHash);
        contentData.setFileCorrupted(isFileCorrupted);
        return contentData;
    }
}

