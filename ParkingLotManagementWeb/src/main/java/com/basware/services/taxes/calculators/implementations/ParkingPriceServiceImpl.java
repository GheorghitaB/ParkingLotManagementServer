package com.basware.services.taxes.calculators.implementations;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.models.vehicles.VehicleType;
import com.basware.exceptions.ResourceNotFoundException;
import com.basware.exceptions.ServiceNotAvailable;
import com.basware.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.basware.utils.Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES;

@Component
public class ParkingPriceServiceImpl implements ParkingPriceService {
    @Autowired
    private ParkingPriceCalculator parkingPriceCalculator;
    @Autowired
    private ParkingDiscountCalculator parkingDiscountCalculator;

    @Override
    public Price getParkingPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                                 ParkingSpotType parkingSpotType, Currency toCurrency)
                                throws ResourceNotFoundException, ServiceNotAvailable {

        Price totalPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, userType,
                vehicleType, parkingSpotType, toCurrency);

        if(parkingTimeInMinutes >= DISCOUNT_AVAILABLE_AFTER_MINUTES){
            Price discount = parkingDiscountCalculator.getDiscount(totalPrice, userType, toCurrency);
            totalPrice = new Price(totalPrice.getUnits() - discount.getUnits(), toCurrency);
        }

        return totalPrice;
    }
}