package com.basware.ParkingLotManagementCommon.models.vehicles;

public class Truck extends Vehicle{
	public Truck(String plateNumber, boolean isElectric) {
		super(VehicleType.TRUCK, plateNumber, isElectric);
	}

}
