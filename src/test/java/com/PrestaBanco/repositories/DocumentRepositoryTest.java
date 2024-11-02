package com.PrestaBanco.repositories;

import com.PrestaBanco.entities.DocumentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DocumentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void whenFindByRequestId_thenReturnDocuments() {
        // given

        DocumentEntity document1 = new DocumentEntity(null, (long) 2, "Aprobado", null);
        entityManager.persistAndFlush(document1);

        // when
        List<DocumentEntity> found = documentRepository.findByRequestId(document1.getRequestId());

        // then
        assertThat(found).hasSize(1).extracting(DocumentEntity::getRequestId).containsOnly(document1.getRequestId());
    }
}
