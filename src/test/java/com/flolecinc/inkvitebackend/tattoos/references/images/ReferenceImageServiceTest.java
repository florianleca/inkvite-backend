package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.FileReaderException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import com.flolecinc.inkvitebackend.file.downloader.FileDownloader;
import com.flolecinc.inkvitebackend.file.manager.FileManager;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReferenceImageServiceTest {

    public static final String WEB_IMAGE_URL = "https://example.com/image.jpg";
    public static final String S3_IMAGE_URL = "https://s3.supabase.com/bucket/image.jpg";
    public static final byte[] MOCK_IMAGE_BYTES = new byte[]{1, 2, 3};
    public static final MockMultipartFile MOCK_MULTIPART_FILE = new MockMultipartFile("file", "image.jpg", "image/jpeg", MOCK_IMAGE_BYTES);

    @Mock
    private FileDownloader fileDownloader;

    @Mock
    private FileManager fileManager;

    @Mock
    private Tika tika;

    @InjectMocks
    private ReferenceImageService referenceImageService;

    @Test
    void uploadImageFromUrl_nominal() throws IOException {
        // Given
        URL url = new URL(WEB_IMAGE_URL);
        when(fileDownloader.validateUrl(WEB_IMAGE_URL)).thenReturn(url);
        when(fileDownloader.getBytesFromFileUrl(url)).thenReturn(MOCK_IMAGE_BYTES);
        when(tika.detect(MOCK_IMAGE_BYTES)).thenReturn("image/jpeg");
        when(fileManager.uploadFileToServer(anyString(), eq(MOCK_IMAGE_BYTES), eq("image/jpeg"))).thenReturn(S3_IMAGE_URL);

        // When
        TempImageDto result = referenceImageService.uploadImageFromUrl(WEB_IMAGE_URL);

        // Then
        assertNotNull(result);
        assertNotNull(result.imagePath());
        assertEquals(S3_IMAGE_URL, result.imageUrl());
    }

    @Test
    void uploadImageFromUrl_imageUploadException() throws IOException {
        // Given
        URL url = new URL(WEB_IMAGE_URL);
        when(fileDownloader.getBytesFromFileUrl(url)).thenThrow(new IOException());

        // When & Then
        FileReaderException exception = assertThrows(FileReaderException.class, () -> referenceImageService.uploadImageFromUrl(url));
        assertEquals("Failed to read file from URL: " + WEB_IMAGE_URL, exception.getMessage());
    }

    @Test
    void uploadImageFromDevice_nominal() throws IOException {
        // Given
        when(fileDownloader.getBytesFromMultipartFile(MOCK_MULTIPART_FILE)).thenReturn(MOCK_IMAGE_BYTES);
        when(tika.detect(MOCK_IMAGE_BYTES)).thenReturn("image/jpeg");
        when(fileManager.uploadFileToServer(anyString(), eq(MOCK_IMAGE_BYTES), eq("image/jpeg"))).thenReturn(S3_IMAGE_URL);

        // When
        TempImageDto result = referenceImageService.uploadImageFromDevice(MOCK_MULTIPART_FILE);

        // Then
        assertNotNull(result);
        assertNotNull(result.imagePath());
        assertEquals(S3_IMAGE_URL, result.imageUrl());
    }

    @Test
    void uploadImageFromDevice_imageUploadException() throws IOException {
        // Given
        when(fileDownloader.getBytesFromMultipartFile(MOCK_MULTIPART_FILE)).thenThrow(new IOException());

        // When & Then
        FileReaderException exception = assertThrows(FileReaderException.class, () -> referenceImageService.uploadImageFromDevice(MOCK_MULTIPART_FILE));
        assertEquals("Failed to read uploaded file: image.jpg", exception.getMessage());
    }

    @Test
    void uploadImage_unsupportedImageTypeException() {
        // Given
        when(tika.detect(MOCK_IMAGE_BYTES)).thenReturn("text/plain");

        // When & Then
        UnsupportedImageTypeException exception = assertThrows(UnsupportedImageTypeException.class, () -> referenceImageService.uploadImage(MOCK_IMAGE_BYTES));
        assertEquals("Unsupported image type: text/plain", exception.getMessage());
    }

}