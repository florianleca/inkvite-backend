package com.flolecinc.inkvitebackend.tattoos.references.images;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReferenceImageController.class)
class ReferenceImageControllerTest {

    public static final String S3_IMAGE_PATH = "image.jpg";
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
        String imageUrl = "https://example.com/image.jpg";
        when(referenceImageService.uploadImageFromUrl(imageUrl)).thenReturn(s3TempImage);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/references-images/upload-from-url")
                .param("url", imageUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imagePath").value(S3_IMAGE_PATH))
                .andExpect(jsonPath("$.imageUrl").value(S3_IMAGE_URL));
        verify(referenceImageService).uploadImageFromUrl(imageUrl);
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

}