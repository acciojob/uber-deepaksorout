package com.driver.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin curradmin=adminRepository1.findById(adminId).get();
		curradmin.setPassword(password);
		adminRepository1.save(curradmin);

		return curradmin;

	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		Admin curradmin=adminRepository1.findById(adminId).get();
		adminRepository1.delete(curradmin);
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> driverList=new ArrayList<>();
		int maxId=driverRepository1.getMaxId();
		int minId=driverRepository1.getMinId();
		for(int i=minId;i<=maxId;i++){
			driverList.add(driverRepository1.findById(i).get());
		}
		return driverList;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList=new ArrayList<>();
		int maxId=customerRepository1.getMaxId();
		int minId=customerRepository1.getMinId();

		for(int i=minId;i<=maxId;i++){
			customerList.add(customerRepository1.findById(i).get());
		}
		return customerList;
	}

}
