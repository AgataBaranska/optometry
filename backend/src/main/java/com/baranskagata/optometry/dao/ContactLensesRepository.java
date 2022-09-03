package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.ContactLenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactLensesRepository extends JpaRepository<ContactLenses,Long> {
}
