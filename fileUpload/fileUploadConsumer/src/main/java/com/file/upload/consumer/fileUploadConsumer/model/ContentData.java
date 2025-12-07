package com.file.upload.consumer.fileUploadConsumer.model;


public class ContentData {

    private byte[] file;
    private int chunkId;
    private String fileName;
    private String fileId;
    private boolean isFinalChunk;
    private String originalFileHash;
    private boolean isFileCorrupted;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getChunkId() {
        return chunkId;
    }

    public void setChunkId(int chunkId) {
        this.chunkId = chunkId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isFinalChunk() {
        return isFinalChunk;
    }

    public void setFinalChunk(boolean finalChunk) {
        isFinalChunk = finalChunk;
    }

    public String getOriginalFileHash() {
        return originalFileHash;
    }

    public void setOriginalFileHash(String originalFileHash) {
        this.originalFileHash = originalFileHash;
    }

    public boolean isFileCorrupted() {
        return isFileCorrupted;
    }

    public void setFileCorrupted(boolean fileCorrupted) {
        isFileCorrupted = fileCorrupted;
    }
}
