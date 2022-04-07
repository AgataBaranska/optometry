package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
