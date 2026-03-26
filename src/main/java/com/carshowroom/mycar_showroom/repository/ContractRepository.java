package com.carshowroom.mycar_showroom.repository;

import com.carshowroom.mycar_showroom.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}
