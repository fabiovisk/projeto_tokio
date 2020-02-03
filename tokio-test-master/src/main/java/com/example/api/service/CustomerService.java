package com.example.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll(Pageable pageable) {
		return repository.findAllByOrderByNameAsc();
	}
	
	public List<Customer> findByName(String name) {
		return repository.findByName(name);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Customer save(@Valid Customer customer) {
		return repository.save(customer);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
