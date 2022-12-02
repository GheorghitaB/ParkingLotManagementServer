package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementServer.models.users.UserType;

import java.util.Optional;

public interface UserTypeDiscountDao {
    Optional<UserDiscount> findByUserType(UserType userType);
    void save(UserDiscount userPrice);
    void deleteAll();
}