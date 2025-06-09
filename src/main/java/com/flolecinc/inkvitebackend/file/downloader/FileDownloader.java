package com.flolecinc.inkvitebackend.file.downloader;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface FileDownloader {

    byte[] getBytesFromFileUrl(URL url) throws IOException;

    byte[] getBytesFromMultipartFile(MultipartFile file) throws IOException;

    URL validateUrl(String stringUrl);

}
