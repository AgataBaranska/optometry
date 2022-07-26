package com.baranskagata.optometry.repository;

import com.baranskagata.optometry.dao.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(userRepository).isNotNull();
    }

    @Test void givenAppUser_whenFindByUsername_thenReturnAppUser(){
        AppUser appUser = AppUser.builder()
                .username("randomUser")
                .password("12345")
                .firstName("Ala")
                .lastName("Bak")
                .pesel("12345678909")
                .telephone("234234567")
                .email("alabak@gmail.com")
                .street("Wakacyjna")
                .city("Warsaw")
                .postalCode("123321")
                .country("Poland")
                .build();

        userRepository.save(appUser);

        AppUser foundAppUser = userRepository.findByUsername(appUser.getUsername()).get();

        assertThat(foundAppUser).isNotNull();
        assertThat(foundAppUser).isEqualTo(appUser);
    }
}
