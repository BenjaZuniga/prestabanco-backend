package com.PrestaBanco.services;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;


    public ArrayList<DocumentEntity> getAllDocuments() {return (ArrayList<DocumentEntity>) documentRepository.findAll();}

    @Transactional
    public ArrayList<DocumentEntity> getDocumentsByRequestId(Long id) {
        return (ArrayList<DocumentEntity>) documentRepository.findByRequestId(id);}

    public DocumentEntity getDocumentById(Long id) {return  documentRepository.findById(id).get();}

    public DocumentEntity saveDocument(String type, Long requestId, MultipartFile file) {
        // Verificar si el archivo no es nulo ni está vacío
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío.");
        }

        DocumentEntity document = new DocumentEntity();
        document.setRequestId(requestId);
        document.setType(type);

        try {
            // Obtener los bytes del archivo
            byte[] data = file.getBytes();
            document.setData(data);  // Guardamos el archivo en el campo 'data'

            // Guardar el documento usando el servicio
            return documentRepository.save(document);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            throw new RuntimeException("Error al guardar el documento: " + e.getMessage());
        }
    }

    public DocumentEntity updateDocument(DocumentEntity document) {return documentRepository.save(document);}

    public boolean deleteDocument(Long id) throws Exception{
        try{
            documentRepository.deleteById(id);
            return true;

        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
