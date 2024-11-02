package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.services.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DocumentControllerTest {

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private DocumentService documentService;

    private DocumentEntity document;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        document = new DocumentEntity();
        document.setId(1L);
        document.setRequestId(1L);
        document.setType("pdf");
        document.setData(new byte[10]);
    }

    @Test
    public void testGetAllDocuments() {
        List<DocumentEntity> documents = new ArrayList<>();
        documents.add(document);

        when(documentService.getAllDocuments()).thenReturn((ArrayList<DocumentEntity>) documents);

        ResponseEntity<List<DocumentEntity>> response = documentController.getAllDocuments();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(document, response.getBody().get(0));
        verify(documentService, times(1)).getAllDocuments();
    }

    @Test
    public void testGetDocumentByRequestId() {
        List<DocumentEntity> documents = new ArrayList<>();
        documents.add(document);

        when(documentService.getDocumentsByRequestId(1L)).thenReturn((ArrayList<DocumentEntity>) documents);

        ResponseEntity<List<DocumentEntity>> response = documentController.getDocumentByRequestId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(document, response.getBody().get(0));
        verify(documentService, times(1)).getDocumentsByRequestId(1L);
    }

    @Test
    public void testGetDocumentById() {
        when(documentService.getDocumentById(1L)).thenReturn(document);

        ResponseEntity<DocumentEntity> response = documentController.getDocumentById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(document, response.getBody());
        verify(documentService, times(1)).getDocumentById(1L);
    }

    @Test
    public void testUploadDocument() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(new byte[10]);
        when(documentService.saveDocument(any(String.class), any(Long.class), any(MultipartFile.class))).thenReturn(document);

        ResponseEntity<DocumentEntity> response = documentController.uploadDocument(file, 1L, "pdf");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(document, response.getBody());
        verify(documentService, times(1)).saveDocument("pdf", 1L, file);
    }

    @Test
    public void testUpdateDocument() {
        when(documentService.updateDocument(any(DocumentEntity.class))).thenReturn(document);

        ResponseEntity<DocumentEntity> response = documentController.updateDocument(document);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(document, response.getBody());
        verify(documentService, times(1)).updateDocument(any(DocumentEntity.class));
    }

    @Test
    public void testDeleteDocument() throws Exception {
        when(documentService.deleteDocument(1L)).thenReturn(true);

        ResponseEntity<Void> response = documentController.deleteDocument(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(documentService, times(1)).deleteDocument(1L);
    }

    @Test
    public void testDeleteDocumentThrowsException() throws Exception {
        when(documentService.deleteDocument(1L)).thenThrow(new Exception("Error deleting document"));

        Exception exception = assertThrows(Exception.class, () -> {
            documentController.deleteDocument(1L);
        });

        assertEquals("Error deleting document", exception.getMessage());
    }
}

