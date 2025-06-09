package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.ImageUploadException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferenceImageService {

    private final S3Client s3Client;
    private final Tika tika = new Tika();

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.imageUrlFormat}")
    private String imageUrlFormat;

    public TempImageDto uploadImageFromUrl(String url) {
        try (InputStream inputStream = new URL(url).openStream()) {
            return uploadImage(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new ImageUploadException("Failed to read image from URL: " + url, e);
        }
    }

    public TempImageDto uploadImageFromDevice(MultipartFile file) {
        try {
            return uploadImage(file.getBytes());
        } catch (IOException e) {
            throw new ImageUploadException("Failed to process uploaded file: " + file.getOriginalFilename(), e);
        }
    }

    private TempImageDto uploadImage(byte[] bytes) {
        String contentType = handleContentType(bytes);
        String imageKey = String.valueOf(UUID.randomUUID());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(imageKey)
                .contentType(contentType)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
        String imageUrl = String.format(imageUrlFormat, bucketName, imageKey);
        return new TempImageDto(imageKey, imageUrl);
    }

    /**
     * Validates the content type of the image.
     * Throws UnsupportedImageTypeException if the content type is not an image.
     *
     * @param bytes the byte array of the image
     * @return the content type of the image
     */
    private String handleContentType(byte[] bytes) {
        String contentType =  tika.detect(bytes);
        if (!contentType.startsWith("image/")) {
            throw new UnsupportedImageTypeException(contentType);
        }
        return contentType;
    }

}
