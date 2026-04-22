package com.carshowroom.mycar_showroom.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardAnalyticsDTO {
    private int pendingSales;
    private int approvedSales;
    private int cancelledSales;
    private double approvedSalesPercent;
    private int customersWhoPurchased;

    private int availableUnits;
    private List<String> branchNames = new ArrayList<>();
    private List<Integer> availableUnitsByBranch = new ArrayList<>();

    public DashboardAnalyticsDTO() {}

    public int getPendingSales() { return pendingSales; }
    public void setPendingSales(int pendingSales) { this.pendingSales = pendingSales; }

    public int getApprovedSales() { return approvedSales; }
    public void setApprovedSales(int approvedSales) { this.approvedSales = approvedSales; }

    public int getCancelledSales() { return cancelledSales; }
    public void setCancelledSales(int cancelledSales) { this.cancelledSales = cancelledSales; }

    public double getApprovedSalesPercent() { return approvedSalesPercent; }
    public void setApprovedSalesPercent(double approvedSalesPercent) { this.approvedSalesPercent = approvedSalesPercent; }

    public int getCustomersWhoPurchased() { return customersWhoPurchased; }
    public void setCustomersWhoPurchased(int customersWhoPurchased) { this.customersWhoPurchased = customersWhoPurchased; }

    public int getAvailableUnits() { return availableUnits; }
    public void setAvailableUnits(int availableUnits) { this.availableUnits = availableUnits; }

    public List<String> getBranchNames() { return branchNames; }
    public void setBranchNames(List<String> branchNames) { this.branchNames = branchNames; }

    public List<Integer> getAvailableUnitsByBranch() { return availableUnitsByBranch; }
    public void setAvailableUnitsByBranch(List<Integer> availableUnitsByBranch) { this.availableUnitsByBranch = availableUnitsByBranch; }
}

