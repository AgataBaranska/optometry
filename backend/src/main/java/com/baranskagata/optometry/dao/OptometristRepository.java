package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.entity.Optometrist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OptometristRepository extends JpaRepository<Optometrist,Long> {

    @Query(value="SELECT a.id,a.firstName, a.lastName, a.pesel,a.telephone, a.email,a.street, a.city, a.postalCode, o.optometristNumber FROM AppUser a Join a.optometrist o")
    Page<AppUserOptometrist>  findAllAppUserOptometrist(Pageable pageable);

    Optional<AppUserOptometrist> findByOptometristNumber(String optometristNumber);


}
