package com.flolecinc.inkvitebackend.file.downloader;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class FileDownloaderImpl implements FileDownloader {

    @Override
    public byte[] getBytesFromFileUrl(String url) throws IOException {
        try (InputStream inputStream = new URL(url).openStream()) {
            return inputStream.readAllBytes();
        }
    }

    @Override
    public byte[] getBytesFromMultipartFile(MultipartFile file) throws IOException {
        return file.getBytes();
    }

}
