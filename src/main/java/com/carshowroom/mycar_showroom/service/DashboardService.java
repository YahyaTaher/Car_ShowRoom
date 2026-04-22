package com.carshowroom.mycar_showroom.service;

import com.carshowroom.mycar_showroom.dto.DashboardAnalyticsDTO;
import com.carshowroom.mycar_showroom.dto.DashboardStatsDTO;
import com.carshowroom.mycar_showroom.repository.CarRepository;
import com.carshowroom.mycar_showroom.repository.ContractRepository;
import com.carshowroom.mycar_showroom.entity.ContractStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public DashboardAnalyticsDTO getDashboardAnalytics() {
        long pending = contractRepository.countByStatusParam(ContractStatus.PENDING);
        long approved = contractRepository.countByStatusParam(ContractStatus.COMPLETED);
        long cancelled = contractRepository.countByStatusParam(ContractStatus.CANCELLED);
        long totalSales = pending + approved + cancelled;

        DashboardAnalyticsDTO dto = new DashboardAnalyticsDTO();
        dto.setPendingSales((int) pending);
        dto.setApprovedSales((int) approved);
        dto.setCancelledSales((int) cancelled);
        dto.setApprovedSalesPercent(totalSales == 0 ? 0.0 : (approved * 100.0) / totalSales);

        dto.setCustomersWhoPurchased((int) contractRepository.countDistinctCustomersWhoPurchased());

        dto.setAvailableUnits((int) carRepository.sumAvailableUnits());

        List<Object[]> branchData = carRepository.sumAvailableUnitsByBranch();
        List<String> names = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Object[] row : branchData) {
            names.add(row[0] != null ? row[0].toString() : "Unknown");
            values.add(row[1] != null ? ((Number) row[1]).intValue() : 0);
        }
        dto.setBranchNames(names);
        dto.setAvailableUnitsByBranch(values);

        return dto;
    }
}

