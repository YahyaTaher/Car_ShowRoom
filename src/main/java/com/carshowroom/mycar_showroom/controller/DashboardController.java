package com.carshowroom.mycar_showroom.controller;

import com.carshowroom.mycar_showroom.dto.DashboardStatsDTO;
import com.carshowroom.mycar_showroom.dto.DashboardAnalyticsDTO;
import com.carshowroom.mycar_showroom.dto.ResponseWrapper;
import com.carshowroom.mycar_showroom.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<ResponseWrapper<DashboardStatsDTO>> getStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(ResponseWrapper.success("Dashboard stats retrieved", stats));
    }

    @GetMapping("/analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<DashboardAnalyticsDTO>> analytics() {
        DashboardAnalyticsDTO dto = dashboardService.getDashboardAnalytics();
        return ResponseEntity.ok(ResponseWrapper.success("Dashboard analytics retrieved", dto));
    }
}


