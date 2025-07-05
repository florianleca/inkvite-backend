package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.FileReaderException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import com.flolecinc.inkvitebackend.file.FileManager;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceImageService {

    private final FileManager fileManager;
    private final Tika tika;

    public TempImageDTO uploadImageFromDevice(MultipartFile file) {
        byte[] imageBytes;
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new FileReaderException("Failed to read uploaded file: " + file.getOriginalFilename(), e);
        }
        return uploadImage(imageBytes);
    }

    public TempImageDTO uploadImage(byte[] imageBytes) {
        String imageName = String.valueOf(UUID.randomUUID());
        String contentType = tika.detect(imageBytes);
        if (!contentType.startsWith("image/")) {
            throw new UnsupportedImageTypeException(contentType);
        }
        String imageUrl = fileManager.uploadFileToServer(imageName, imageBytes, contentType);
        return new TempImageDTO(imageName, imageUrl);
    }

}
