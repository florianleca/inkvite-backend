package com.flolecinc.inkvitebackend.file.downloader;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDownloader {

    byte[] getBytesFromFileUrl(String url) throws IOException;

    byte[] getBytesFromMultipartFile(MultipartFile file) throws IOException;

}
