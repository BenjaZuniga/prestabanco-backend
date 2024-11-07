package com.PrestaBanco.repositories;

import com.PrestaBanco.entities.RequestEntity;
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
public class RequestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void testFindByOwnerId() {
        RequestEntity request1 = new RequestEntity(null,(long)1,null,null, null, 0, null, null, null);

        RequestEntity request2 = new RequestEntity();
        request2.setOwnerId((long)2);

        RequestEntity request3 = new RequestEntity();
        request3.setOwnerId((long)1);

        entityManager.persist(request1);
        entityManager.persist(request2);
        entityManager.persist(request3);

        List<RequestEntity> requests = requestRepository.findByOwnerId((long)1);

        assertThat(requests).hasSize(2).extracting(RequestEntity::getOwnerId).containsExactly(request1.getOwnerId(), request3.getOwnerId());

    }

}
