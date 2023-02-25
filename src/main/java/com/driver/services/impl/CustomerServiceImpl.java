package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		boolean flag=false;
		TripBooking tripBooking=new TripBooking();
		for(int i=driverRepository2.getMinId();i<=driverRepository2.getMaxId();i++){
			if(driverRepository2.findById(i).isPresent() && driverRepository2.findById(i).get().getCab().isAvailable()==true){
				tripBooking.setDriver(driverRepository2.findById(i).get());
				tripBooking.setCustomer(customerRepository2.findById(customerId).get());
				tripBooking.setDistanceInKm(distanceInKm);
				tripBooking.setFromLocation(fromLocation);
				tripBooking.setToLocation(toLocation);
				tripBooking.setStatus(TripStatus.CONFIRMED);
				tripBookingRepository2.save(tripBooking);
			}
			flag=true;
			break;
		}
		if(flag==false){
			throw new Exception("No cab available!");
		}
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.setBill(tripBooking.getDriver().getCab().getPerKmRate()*tripBooking.getDistanceInKm());
	}
}
