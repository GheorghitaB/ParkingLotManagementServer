package com.basware.ParkingLotManagementCommon.models.users;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity("users")
@Indexes({
		@Index(options = @IndexOptions(name = "unique_user_type", unique = true),
				fields = @Field(value = "userType"))
})
public abstract class User {

	@Id
	private ObjectId objectId;

	protected String name;
	private UserType userType;

	protected User() {}

	protected User(String name, UserType userType) {
		this.name = name;
		this.userType = userType;
	}
	
	public String getName() {
		return name;
	}
	
	public UserType getUserType() {
		return userType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return name.equals(user.name) && userType == user.userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, userType);
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", userType=" + userType +
				'}';
	}
}
