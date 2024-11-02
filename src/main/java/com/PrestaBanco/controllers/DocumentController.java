package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    System sys;

    @GetMapping("/")
    public ResponseEntity<List<DocumentEntity>> getAllDocuments() {
        List<DocumentEntity> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/findByRequestId/{id}")
    public ResponseEntity<List<DocumentEntity>> getDocumentByRequestId(@PathVariable Long id) {
        List<DocumentEntity> documents = documentService.getDocumentsByRequestId(id);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentEntity> getDocumentById(@PathVariable Long id) {
        DocumentEntity documentEntity = documentService.getDocumentById(id);
        return ResponseEntity.ok(documentEntity);
    }

    @PostMapping("/")
    public ResponseEntity<DocumentEntity> uploadDocument(
            @RequestParam("file") MultipartFile file,  // Recibir archivo desde el frontend
            @RequestParam("requestId") Long requestId, // Otras propiedades del documento
            @RequestParam("type") String type) {      // Tipo de documento

        DocumentEntity savedDocument = documentService.saveDocument(type, requestId, file);
        return ResponseEntity.ok(savedDocument); // Devolvemos el documento guardado

    }

    @PutMapping("/")
    public ResponseEntity<DocumentEntity> updateDocument(@RequestBody DocumentEntity documentEntity) {
        DocumentEntity updatedDocument = documentService.updateDocument(documentEntity);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) throws Exception {
        var isDeleted = documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
