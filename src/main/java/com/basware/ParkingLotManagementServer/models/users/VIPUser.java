package com.basware.ParkingLotManagementServer.models.users;

public class VIPUser extends User{

	public VIPUser(String name) {
		super(name, UserType.VIP);
	}

}
