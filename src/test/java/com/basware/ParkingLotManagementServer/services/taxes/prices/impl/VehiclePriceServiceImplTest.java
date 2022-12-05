package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.repositories.taxes.VehicleTypePriceDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.VehiclePriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehiclePriceServiceImplTest {

    @Mock
    private VehicleTypePriceDao vehicleTypePriceDao;

    @InjectMocks
    private VehiclePriceService vehiclePriceService;

    @BeforeEach
    void setUp() {
        vehiclePriceService = new VehiclePriceServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPrice_ShouldThrowResourceNotFoundExceptionWhenThereIsNotAPriceForSearchedVehicleType(){
        when(vehicleTypePriceDao.findByVehicleType(VehicleType.CAR)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> vehiclePriceService.getPrice(VehicleType.CAR));
        verify(vehicleTypePriceDao, times(1)).findByVehicleType(VehicleType.CAR);
    }

    @Test
    void getPrice_ShouldReturnPriceWhenThereIsAPriceForSearchedVehicleType() throws ResourceNotFoundException {
        Price price = new Price(0.5, Currency.EUR);
        when(vehicleTypePriceDao.findByVehicleType(VehicleType.CAR)).thenReturn(Optional.of(price));
        Price resultPrice = vehiclePriceService.getPrice(VehicleType.CAR);
        assertEquals(price, resultPrice);
        verify(vehicleTypePriceDao, times(1)).findByVehicleType(VehicleType.CAR);
    }
}