package com.file.upload.consumer.fileUploadConsumer.service;

import com.file.upload.consumer.fileUploadConsumer.model.ContentData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;

@Service
public class FileProcessorService {


    public void processFile(ContentData contentData, String consumerName , String uploadDir) throws IOException, NoSuchAlgorithmException {
        String currentDirectory =  uploadDir + consumerName+ contentData.getFileId();
        File dir = new File(currentDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path path = Paths.get(dir.getAbsolutePath(), String.valueOf(contentData.getChunkId()));
            Files.write(path, contentData.getFile());

            if(contentData.isFileCorrupted()){
                dir.delete();
                return;
            }
            if (contentData.isFinalChunk()) {
                mergeAndVerify(contentData,currentDirectory);
            }
    }

    private void mergeAndVerify(ContentData contentData,String uploadDir) throws IOException, NoSuchAlgorithmException {
        File dir = new File(uploadDir);
        File[] chunks = dir.listFiles();
        if (chunks == null) {
            return;
        }

        Arrays.sort(chunks, Comparator.comparingInt(f -> Integer.parseInt(f.getName())));

        File mergedFile = new File(uploadDir + contentData.getFileName());
        try (FileOutputStream fos = new FileOutputStream(mergedFile, false)) { // Use false to overwrite if file exists
            for (File chunk : chunks) {
                Files.copy(chunk.toPath(), fos);
                chunk.delete();
            }
        }
        dir.delete();


    }
}
