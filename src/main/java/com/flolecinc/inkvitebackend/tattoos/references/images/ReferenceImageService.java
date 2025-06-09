package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.FileReaderException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import com.flolecinc.inkvitebackend.file.downloader.FileDownloader;
import com.flolecinc.inkvitebackend.file.manager.FileManager;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceImageService {

    private final FileDownloader fileDownloader;
    private final FileManager fileManager;
    private final Tika tika;

    public TempImageDto uploadImageFromUrl(String stringUrl) {
        URL url = fileDownloader.validateUrl(stringUrl);
        return uploadImageFromUrl(url);
    }

    public TempImageDto uploadImageFromUrl(URL url) {
        byte[] imageBytes;
        try {
            imageBytes = fileDownloader.getBytesFromFileUrl(url);
        } catch (IOException e) {
            throw new FileReaderException("Failed to read file from URL: " + url, e);
        }
        return uploadImage(imageBytes);
    }

    public TempImageDto uploadImageFromDevice(MultipartFile file) {
        byte[] imageBytes;
        try {
            imageBytes = fileDownloader.getBytesFromMultipartFile(file);
        } catch (IOException e) {
            throw new FileReaderException("Failed to read uploaded file: " + file.getOriginalFilename(), e);
        }
        return uploadImage(imageBytes);
    }

    public TempImageDto uploadImage(byte[] imageBytes) {
        String imageName = String.valueOf(UUID.randomUUID());
        String contentType = tika.detect(imageBytes);
        if (!contentType.startsWith("image/")) {
            throw new UnsupportedImageTypeException(contentType);
        }
        String imageUrl = fileManager.uploadFileToServer(imageName, imageBytes, contentType);
        return new TempImageDto(imageName, imageUrl);
    }

}
