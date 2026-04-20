package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.dto.DashboardStatsDTO;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import com.carshowroom.mycar_showroom.repository.ContractRepository;
import com.carshowroom.mycar_showroom.entity.ContractStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ContractRepository contractRepository;

    public DashboardStatsDTO getDashboardStats() {
        long totalCars = carRepository.count();
        long availableCars = carRepository.findAvailableCars().size();
        long soldCars = contractRepository.countByStatusParam(ContractStatus.COMPLETED);
        
        java.math.BigDecimal totalRevenue = contractRepository.sumTotalRevenue();
        if (totalRevenue == null) totalRevenue = java.math.BigDecimal.ZERO;
        
        long pendingContracts = contractRepository.countPendingContracts();
        
        return new DashboardStatsDTO((int) totalCars, (int) availableCars, (int) soldCars, 
                                   totalRevenue.doubleValue(), (int) pendingContracts, 0);
    }
}

