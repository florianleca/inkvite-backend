package com.flolecinc.inkvitebackend.file.downloader;

import com.flolecinc.inkvitebackend.exceptions.InvalidDownloadURLException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FileDownloaderImpl implements FileDownloader {

    @Override
    public byte[] getBytesFromFileUrl(URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            return inputStream.readAllBytes();
        }
    }

    @Override
    public byte[] getBytesFromMultipartFile(MultipartFile file) throws IOException {
        return file.getBytes();
    }

    @Override
    public URL validateUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            throw new InvalidDownloadURLException(e);
        }
        if (!"https".equalsIgnoreCase(url.getProtocol())) {
            throw new InvalidDownloadURLException("Unauthorized protocole : " + url.getProtocol());
        }
        return url;
    }

}
