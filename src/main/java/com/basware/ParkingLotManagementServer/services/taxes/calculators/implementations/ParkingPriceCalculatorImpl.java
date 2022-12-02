package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.prices.ParkingSpotPriceService;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserPriceService;
import com.basware.ParkingLotManagementServer.services.taxes.prices.VehiclePriceService;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingPriceCalculatorImpl implements ParkingPriceCalculator {
    @Autowired
    private UserPriceService userPriceService;
    @Autowired
    private VehiclePriceService vehiclePriceService;
    @Autowired
    private ParkingSpotPriceService parkingSpotPriceService;

    @Override
    public Price getTotalPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType, ParkingSpotType parkingSpotType) throws ResourceNotFoundException {
        int hours = parkingTimeInMinutes / 60;
        int minutes = parkingTimeInMinutes % 60;
        if(minutes >= 30 || (hours == 0 && minutes > 0)){
            hours++;
        } else {
            //TODO: What happens if parkingTimeInMinutes <= 0?
        }

        double userPrice = userPriceService.getPrice(userType).getUnits();
        double vehiclePrice = vehiclePriceService.getPrice(vehicleType).getUnits();
        double parkingSpotPrice = parkingSpotPriceService.getPrice(parkingSpotType).getUnits();

        return new Price (userPrice + vehiclePrice + parkingSpotPrice * hours, Currency.EUR);
    }
}
