package com.carshowroom.mycar_showroom.repository;

import com.carshowroom.mycar_showroom.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    Optional<Branch> findByCity(String city);
    java.util.List<Branch> findAllByOrderByNameAsc();
}
