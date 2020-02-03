package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResource {
	
	@JsonProperty("name_customer")
	private String name;
	
	@JsonProperty("id_customer")
	private Long id;
	
	@JsonProperty("email")
	private String email;
	
	public CustomerResource() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CustomerResource [name=" + name + ", id=" + id + ", email=" + email + "]";
	}


	

}
