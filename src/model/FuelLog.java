package model;

import java.time.LocalDate;

public class FuelLog {
    private int logId, truckId, userId;
    private LocalDate date;
    private double fuelAmount, fuelPrice, mileage, fuelCost;
    
    public FuelLog() {}
    
    public FuelLog(int truckId, int userId, LocalDate date, double fuelAmount, double fuelPrice, double mileage) {
        this.truckId = truckId;
        this.userId = userId;
        this.date = date;
        this.fuelAmount = fuelAmount;
        this.fuelPrice = fuelPrice;
        this.mileage = mileage;
        this.fuelCost = fuelAmount * fuelPrice;
    }
    
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public int getTruckId() { return truckId; }
    public void setTruckId(int truckId) { this.truckId = truckId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getFuelAmount() { return fuelAmount; }
    public void setFuelAmount(double fuelAmount) { this.fuelAmount = fuelAmount; }
    public double getFuelPrice() { return fuelPrice; }
    public void setFuelPrice(double fuelPrice) { this.fuelPrice = fuelPrice; }
    public double getMileage() { return mileage; }
    public void setMileage(double mileage) { this.mileage = mileage; }
    public double getFuelCost() { return fuelCost; }
    public void setFuelCost(double fuelCost) { this.fuelCost = fuelCost; }
}