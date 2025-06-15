package com.flolecinc.inkvitebackend.file.manager;

import com.flolecinc.inkvitebackend.exceptions.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@RequiredArgsConstructor
public class S3FileManager implements FileManager {

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.fileUrlFormat}")
    private String fileUrlFormat;

    private final S3Client s3Client;

    @Override
    public String uploadFileToServer(String fileName, byte[] fileBytes, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();
        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
        if (!response.sdkHttpResponse().isSuccessful()) {
            throw new S3UploadException(
                    response.sdkHttpResponse().statusText().orElse("Unknown error"),
                    response.sdkHttpResponse().statusCode()
            );
        }
        return String.format(fileUrlFormat, bucketName, fileName);
    }

}
