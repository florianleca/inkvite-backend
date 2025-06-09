package com.flolecinc.inkvitebackend.file.manager;

public interface FileManager {

    /**
     * Uploads a file to a server and returns the URL of the uploaded file.
     *
     * @param fileName   the name of the file to be uploaded
     * @param fileBytes  the byte array of the file to be uploaded
     * @param contentType the content type of the file
     * @return the URL of the uploaded file
     */
    String uploadFileToServer(String fileName, byte[] fileBytes, String contentType);

}
