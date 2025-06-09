package com.flolecinc.inkvitebackend.tattoos.references.images;

import com.flolecinc.inkvitebackend.exceptions.FileReaderException;
import com.flolecinc.inkvitebackend.exceptions.InvalidDownloadURLException;
import com.flolecinc.inkvitebackend.exceptions.S3UploadException;
import com.flolecinc.inkvitebackend.exceptions.UnsupportedImageTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReferenceImageController.class)
class ReferenceImageControllerTest {

    public static final String S3_IMAGE_PATH = "image.jpg";
    public static final String WEB_IMAGE_URL = "https://example.com/image.jpg";
    public static final String S3_IMAGE_URL = "https://s3.supabase.com/bucket/image.jpg";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReferenceImageService referenceImageService;

    private TempImageDto s3TempImage;

    @BeforeEach
    void setUp() {
        s3TempImage = new TempImageDto(S3_IMAGE_PATH, S3_IMAGE_URL);
    }

    @Test
    void uploadImageFromUrl_nominal_serviceCalledAndStatusOk() throws Exception {
        // Given
        when(referenceImageService.uploadImageFromUrl(WEB_IMAGE_URL)).thenReturn(s3TempImage);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                        .param("url", WEB_IMAGE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imagePath").value(S3_IMAGE_PATH))
                .andExpect(jsonPath("$.imageUrl").value(S3_IMAGE_URL));
        verify(referenceImageService).uploadImageFromUrl(WEB_IMAGE_URL);
    }

    @Test
    void uploadImageFromDevice_nominal_serviceCalledAndStatusOk() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "image content".getBytes());
        when(referenceImageService.uploadImageFromDevice(file)).thenReturn(s3TempImage);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/references-images/upload-from-device")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imagePath").value(S3_IMAGE_PATH))
                .andExpect(jsonPath("$.imageUrl").value(S3_IMAGE_URL));
        verify(referenceImageService).uploadImageFromDevice(file);
    }

    @Test
    void uploadImage_errorWhileReadingFile_exceptionHandled() throws Exception {
        // Given
        when(referenceImageService.uploadImageFromUrl(WEB_IMAGE_URL)).thenThrow(new FileReaderException("error while reading file", null));

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                        .param("url", WEB_IMAGE_URL))
                .andExpect(status().isInternalServerError())
                .andReturn();
        assertEquals("{\"error\":\"error while reading file\"}", result.getResponse().getContentAsString());
    }

    @Test
    void uploadImage_unsupportedImageType_exceptionHandled() throws Exception {
        // Given
        when(referenceImageService.uploadImageFromUrl(WEB_IMAGE_URL)).thenThrow(new UnsupportedImageTypeException("text/plain"));

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                        .param("url", WEB_IMAGE_URL))
                .andExpect(status().isUnsupportedMediaType())
                .andReturn();
        assertEquals("{\"error\":\"Unsupported image type: text/plain\"}", result.getResponse().getContentAsString());
    }

    @Test
    void uploadImage_s3UploadError_exceptionHandled() throws Exception {
        // Given
        when(referenceImageService.uploadImageFromUrl(WEB_IMAGE_URL)).thenThrow(new S3UploadException("teapot error", 418));

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                        .param("url", WEB_IMAGE_URL))
                .andExpect(status().isIAmATeapot())
                .andReturn();
        assertEquals("{\"error\":\"Failed to upload a file to the S3 server: teapot error\"}", result.getResponse().getContentAsString());
    }

    @Test
    void uploadImage_invalidDownloadUrl_exceptionHandled() throws Exception {
        // Given
        String invalidUrl = "invalid-url";
        when(referenceImageService.uploadImageFromUrl(invalidUrl)).thenThrow(new InvalidDownloadURLException("Invalid URL"));

        // When & Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                        .param("url", invalidUrl))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals("{\"error\":\"Invalid URL\"}", result.getResponse().getContentAsString());
    }

}