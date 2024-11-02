package com.PrestaBanco.repositories;

import com.PrestaBanco.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    public List<RequestEntity> findByOwnerId(Long id);

}
