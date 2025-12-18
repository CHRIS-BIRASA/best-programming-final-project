package model;

import java.time.LocalDate;
import java.util.List;

public class Report {
    private String reportType;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer truckId;
    private Integer userId;
    private List<String> data;
    private double totalFuelCost;
    private double totalFuelAmount;
    
    public Report(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportType() { return reportType; }
    public LocalDate getDateFrom() { return dateFrom; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }
    public LocalDate getDateTo() { return dateTo; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }
    public Integer getTruckId() { return truckId; }
    public void setTruckId(Integer truckId) { this.truckId = truckId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public List<String> getData() { return data; }
    public void setData(List<String> data) { this.data = data; }
    public double getTotalFuelCost() { return totalFuelCost; }
    public void setTotalFuelCost(double totalFuelCost) { this.totalFuelCost = totalFuelCost; }
    public double getTotalFuelAmount() { return totalFuelAmount; }
    public void setTotalFuelAmount(double totalFuelAmount) { this.totalFuelAmount = totalFuelAmount; }
}