package model;

public class Truck {
    private int truckId;
    private String truckCode, model, licensePlate;
    private int assignedDriverId;
    
    public Truck() {}
    
    public Truck(String truckCode, String model, String licensePlate) {
        this.truckCode = truckCode;
        this.model = model;
        this.licensePlate = licensePlate;
    }
    
    public int getTruckId() { return truckId; }
    public void setTruckId(int truckId) { this.truckId = truckId; }
    public String getTruckCode() { return truckCode; }
    public void setTruckCode(String truckCode) { this.truckCode = truckCode; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public int getAssignedDriverId() { return assignedDriverId; }
    public void setAssignedDriverId(int assignedDriverId) { this.assignedDriverId = assignedDriverId; }
    
    public String toString() { return truckCode + " - " + model; }
}