package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.databases.MongoDbHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles(profiles = "test")
class UserTypeDiscountPercentDaoImplIT {

    @Autowired
    private MongoDbHelper mongoDbHelper;

    private UserTypeDiscountPercentDaoImplExtended userTypeDiscountPercentDao;

    @BeforeEach
    void setUp() {
        userTypeDiscountPercentDao = new UserTypeDiscountPercentDaoImplExtended(mongoDbHelper);
        userTypeDiscountPercentDao.deleteAll();
    }

    @Test
    void save_ShouldSaveUserDiscountIntoDatabase(){
        UserDiscount userDiscount = new UserDiscount(UserType.REGULAR, 0.25);

        boolean saved = userTypeDiscountPercentDao.save(userDiscount);

        assertTrue(saved);
    }

    @Test
    void deleteAll_ShouldDeleteAllDataFromDatabaseCollection(){
        UserDiscount userDiscount1 = new UserDiscount(UserType.REGULAR, 0.25);
        UserDiscount userDiscount2 = new UserDiscount(UserType.VIP, 0.50);

        userTypeDiscountPercentDao.save(userDiscount1);
        userTypeDiscountPercentDao.save(userDiscount2);

        userTypeDiscountPercentDao.deleteAll();

        assertEquals(0, userTypeDiscountPercentDao.getSize());
    }

    @Test
    void findByUserType_ShouldReturnDiscountForUserTypeWhenItExistsIntoDatabase(){
        UserType userType = UserType.REGULAR;
        double discount = 0.25;
        UserDiscount userDiscount = new UserDiscount(userType, discount);

        userTypeDiscountPercentDao.save(userDiscount);

        Optional<Double> discountOptional = userTypeDiscountPercentDao.findByUserType(userType);
        assertTrue(discountOptional.isPresent());
        assertEquals(discount, discountOptional.get());
    }

    @Test
    void findByUserType_ShouldReturnEmptyOptionalWhenDiscountForUserTypeDoesNotExistIntoDatabase(){
        UserType userType = UserType.REGULAR;
        Optional<Double> discountOptional = userTypeDiscountPercentDao.findByUserType(userType);
        assertTrue(discountOptional.isEmpty());
    }

}

class UserTypeDiscountPercentDaoImplExtended extends UserTypeDiscountPercentDaoImpl{

    private final MongoDbHelper mongoDbHelper;

    public UserTypeDiscountPercentDaoImplExtended(MongoDbHelper mongoDbHelper) {
        super(mongoDbHelper);
        this.mongoDbHelper = mongoDbHelper;
    }

    public long getSize(){
        return mongoDbHelper.getMongoCollection(MongoDbHelper.USER_TYPE_DISCOUNT_COLLECTION)
                .countDocuments();
    }
}