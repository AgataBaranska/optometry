package com.baranskagata.optometry.repository;

import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.dao.Optometrist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OptometristRepository extends JpaRepository<Optometrist,Long> {

    @Query(value = "SELECT new com.baranskagata.optometry.dto.AppUserOptometrist" +
            "(a.id, a.firstName, a.lastName, a.pesel,a.telephone," +
            " a.email,a.street, a.city,a.country, a.postalCode," +
            " o.optometristNumber) FROM AppUser a Join a.optometrist o")
    List<AppUserOptometrist> findAllAppUserOptometrist();

    Optional<AppUserOptometrist> findByOptometristNumber(String optometristNumber);

}
