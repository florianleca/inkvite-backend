package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.FileReaderException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import com.flolecinc.inkvitebackend.file.FileManager;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReferenceImageServiceTest {

    public static final String S3_IMAGE_URL = "https://s3.supabase.com/bucket/image.jpg";
    public static final byte[] MOCK_IMAGE_BYTES = new byte[]{1, 2, 3};
    public static final MockMultipartFile MOCK_MULTIPART_FILE = new MockMultipartFile("file", "image.jpg", "image/jpeg", MOCK_IMAGE_BYTES);

    @Mock
    private FileManager fileManager;

    @Mock
    private Tika tika;

    @InjectMocks
    private ReferenceImageService referenceImageService;

    @Test
    void uploadImageFromDevice_nominal() {
        // Given
        when(tika.detect(MOCK_IMAGE_BYTES)).thenReturn("image/jpeg");
        when(fileManager.uploadFileToServer(anyString(), eq(MOCK_IMAGE_BYTES), eq("image/jpeg"))).thenReturn(S3_IMAGE_URL);

        // When
        TempImageDTO result = referenceImageService.uploadImageFromDevice(MOCK_MULTIPART_FILE);

        // Then
        assertNotNull(result);
        assertNotNull(result.imagePath());
        assertEquals(S3_IMAGE_URL, result.imageUrl());
    }

    @Test
    void uploadImageFromDevice_imageUploadException() throws IOException {
        // Given
        MultipartFile badFile = mock(MultipartFile.class);
        when(badFile.getBytes()).thenThrow(new IOException());
        when(badFile.getOriginalFilename()).thenReturn("image.jpg");

        // When & Then
        FileReaderException exception = assertThrows(FileReaderException.class, () -> referenceImageService.uploadImageFromDevice(badFile));
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