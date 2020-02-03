package com.example.api.web.rest;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import exception.CustomerResourceException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}
	
	@GetMapping(path = "/customer-id/{id}")
	public Optional<Customer> getCustomerById(@PathVariable("id") Long id ) {
		verifyIsCustomerExist(id);
		Optional<Customer> customer = service.findById(id);
		return customer;
	}
	
	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<?> findCustomersByName(@PathVariable String name) {
		return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
	}
	
	@GetMapping(path = "/customers")
	public ResponseEntity<?> findAll(Pageable pageable) {
		return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
	}

	@PostMapping(path = "/customer/save")
	public ResponseEntity<?> save(@Valid @RequestBody Customer customer) {
		return new ResponseEntity<>(service.save(customer), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/customer/delete/{id}")
	@PreAuthorize("ADMIN")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		verifyIsCustomerExist(id);
		service.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		verifyIsCustomerExist(customer.getId());
		service.save(customer);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verifyIsCustomerExist(Long id) {
		if (service.findById(id) == null)
			throw new CustomerResourceException("Customer NOT FOUND Id:"+id);
	}

}
