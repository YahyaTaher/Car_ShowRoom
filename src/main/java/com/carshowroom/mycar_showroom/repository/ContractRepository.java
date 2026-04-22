package com.carshowroom.mycar_showroom.repository;

import com.carshowroom.mycar_showroom.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.carshowroom.mycar_showroom.entity.ContractStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByCustomerId(Long customerId);
    List<Contract> findByUserId(Long userId);
    List<Contract> findByStatus(ContractStatus status);
    
    @Query("SELECT COUNT(c) FROM Contract c WHERE c.status = 'PENDING'")
    long countPendingContracts();
    
    @Query("SELECT SUM(c.totalPrice) FROM Contract c WHERE c.status = 'COMPLETED'")
    java.math.BigDecimal sumTotalRevenue();
    
@Query("SELECT COUNT(c) FROM Contract c WHERE c.status = :status")
    long countByStatusParam(ContractStatus status);

    @Query("SELECT COUNT(DISTINCT c.user.id) FROM Contract c WHERE c.status = 'COMPLETED'")
    long countDistinctCustomersWhoPurchased();
}
