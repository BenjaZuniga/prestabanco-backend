package com.PrestaBanco.services;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.repositories.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDocuments() {
        List<DocumentEntity> documents = new ArrayList<>();
        documents.add(new DocumentEntity());
        when(documentRepository.findAll()).thenReturn(documents);

        ArrayList<DocumentEntity> result = documentService.getAllDocuments();
        assertEquals(1, result.size());
        verify(documentRepository, times(1)).findAll();
    }

    @Test
    void testGetDocumentsByRequestId() {
        List<DocumentEntity> documents = new ArrayList<>();
        DocumentEntity document = new DocumentEntity();
        document.setRequestId(1L);
        documents.add(document);
        when(documentRepository.findByRequestId(1L)).thenReturn(documents);

        ArrayList<DocumentEntity> result = documentService.getDocumentsByRequestId(1L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getRequestId());
        verify(documentRepository, times(1)).findByRequestId(1L);
    }

    @Test
    void testGetDocumentById() {
        DocumentEntity document = new DocumentEntity();
        document.setId(1L);
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        DocumentEntity result = documentService.getDocumentById(1L);
        assertEquals(1L, result.getId());
        verify(documentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveDocument() throws IOException {
        String type = "texto";
        Long requestId = 1L;
        byte[] data = "test data".getBytes();
        DocumentEntity document = new DocumentEntity();
        document.setType(type);
        document.setRequestId(requestId);
        document.setData(data);

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenReturn(data);
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(document);

        DocumentEntity result = documentService.saveDocument(type, requestId, file);
        assertNotNull(result);
        assertEquals(type, result.getType());
        assertEquals(requestId, result.getRequestId());
        verify(documentRepository, times(1)).save(any(DocumentEntity.class));
    }

    @Test
    void testSaveDocumentEmptyFileThrowsException() {
        String type = "texto";
        Long requestId = 1L;

        when(file.isEmpty()).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            documentService.saveDocument(type, requestId, file);
        });

        assertEquals("El archivo no puede estar vacío.", exception.getMessage());
    }

    @Test
    void testSaveDocumentTextFileContentLogging() throws IOException {
        String type = "texto";  // O usa "json" para probar el otro tipo
        Long requestId = 1L;
        byte[] data = "contenido de prueba".getBytes();

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenReturn(data);
        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(new DocumentEntity());

        DocumentEntity result = documentService.saveDocument(type, requestId, file);

        assertNotNull(result);
        verify(file, times(1)).getBytes();
    }

    @Test
    void testSaveDocumentIOException() throws IOException {
        String type = "pdf";
        Long requestId = 1L;

        when(file.isEmpty()).thenReturn(false);
        when(file.getBytes()).thenThrow(new IOException("Error al leer el archivo"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            documentService.saveDocument(type, requestId, file);
        });

        assertTrue(exception.getMessage().contains("Error al guardar el documento"));
    }

    @Test
    void testUpdateDocument() {
        DocumentEntity document = new DocumentEntity();
        document.setId(1L);
        when(documentRepository.save(document)).thenReturn(document);

        DocumentEntity result = documentService.updateDocument(document);
        assertEquals(1L, result.getId());
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void testDeleteDocument() throws Exception {
        doNothing().when(documentRepository).deleteById(1L);

        boolean result = documentService.deleteDocument(1L);
        assertTrue(result);
        verify(documentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDocumentException() {
        doThrow(new RuntimeException("Error al eliminar documento")).when(documentRepository).deleteById(1L);

        Exception exception = assertThrows(Exception.class, () -> {
            documentService.deleteDocument(1L);
        });

        assertEquals("Error al eliminar documento", exception.getMessage());
    }
}

