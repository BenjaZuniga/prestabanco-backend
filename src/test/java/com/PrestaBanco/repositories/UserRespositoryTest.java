package com.PrestaBanco.repositories;

import com.PrestaBanco.entities.DocumentEntity;
import com.PrestaBanco.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRespositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void whenExistUser_thenReturnTrue() {
        UserEntity user = new UserEntity();
        user.setMail("cliente@mail.com");
        entityManager.persistAndFlush(user);

        boolean exist = userRepository.existsByMail(user.getMail());
        assertThat(exist).isTrue();
    }

    @Test
    public void whenExistUser_thenReturnFalse() {
        UserEntity user = new UserEntity();
        user.setMail("cliente@mail.com");
        entityManager.persistAndFlush(user);

        boolean exist = userRepository.existsByMail("ejecutivo@mail.com");
        assertThat(exist).isFalse();
    }

    @Test void whenFindBymail_thenReturnUser() {
        UserEntity user = new UserEntity();
        user.setMail("cliente@mail.com");
        entityManager.persistAndFlush(user);

        UserEntity found = userRepository.findByMail(user.getMail());
        assertThat(found.getMail()).isEqualTo(user.getMail());
    }
}
