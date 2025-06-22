package com.flolecinc.inkvitebackend.file;

import com.flolecinc.inkvitebackend.exceptions.S3UploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3FileManagerTest {

    public static final String FILE_NAME = "image.jpg";
    public static final byte[] FILE_BYTES = new byte[]{1, 2, 3};
    public static final String CONTENT_TYPE = "image/jpeg";

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private S3FileManager s3S3FileManager;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(s3S3FileManager, "bucketName", "test-bucket");
        ReflectionTestUtils.setField(s3S3FileManager, "fileUrlFormat", "https://s3.amazonaws.com/%s/%s");
    }

    @Test
    void uploadFileToS3_nominal() {
        // Given
        SdkHttpResponse mockSdkHttpResponse = SdkHttpResponse.builder()
                .statusCode(200)
                .build();
        PutObjectResponse mockResponse = mock(PutObjectResponse.class);
        when(mockResponse.sdkHttpResponse()).thenReturn(mockSdkHttpResponse);
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(mockResponse);

        // When
        String fileUrl = s3S3FileManager.uploadFileToServer(FILE_NAME, FILE_BYTES, CONTENT_TYPE);

        // Then
        assertNotNull(fileUrl);
        assertEquals("https://s3.amazonaws.com/test-bucket/image.jpg", fileUrl);
    }

    @Test
    void uploadFileToS3_uploadError() {
        // Given
        SdkHttpResponse mockSdkHttpResponse = SdkHttpResponse.builder()
                .statusCode(500)
                .statusText("Internal Server Error")
                .build();
        PutObjectResponse mockResponse = mock(PutObjectResponse.class);
        when(mockResponse.sdkHttpResponse()).thenReturn(mockSdkHttpResponse);
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .thenReturn(mockResponse);

        // When & Then
        S3UploadException exception = assertThrows(S3UploadException.class, () -> s3S3FileManager.uploadFileToServer(FILE_NAME, FILE_BYTES, CONTENT_TYPE));
        assertEquals("Failed to upload a file to the S3 server: Internal Server Error", exception.getMessage());
    }

}