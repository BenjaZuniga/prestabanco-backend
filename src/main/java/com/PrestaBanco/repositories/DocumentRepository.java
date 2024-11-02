package com.PrestaBanco.repositories;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    public List<DocumentEntity> findByRequestId(Long requestId);

}
