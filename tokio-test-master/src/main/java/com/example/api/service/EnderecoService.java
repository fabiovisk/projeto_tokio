package com.example.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.example.api.domain.Endereco;
import com.example.api.repository.EnderecoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EnderecoService {
	
	@Autowired
	private EnderecoRepository repository;

	@Autowired
	public EnderecoService(EnderecoRepository repository) {
		this.repository = repository;
	}
	
	public List<Endereco> findAll(Pageable pageable) {
		return repository.findAllByOrderByLogradouroAsc();
	}
	
	public List<Endereco> findByLogradouro(String logradouro) {
		return repository.findByLogradouro(logradouro);
	}

	public Optional<Endereco> findById(Long id) {
		return repository.findById(id);
	}

	public Endereco save(@Valid Endereco endereco) {
		return repository.save(endereco);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public Endereco buscarEnderecoPor(String urlJson) {
		System.out.println("CHAMOU O SERVIÇO....");
		
		final GsonBuilder gsonBuilder = new GsonBuilder();
		final Gson gson = gsonBuilder.create();
		Gson g = new Gson();		
		Endereco retornoEndereco = gson.fromJson(urlJson, Endereco.class);		
		return retornoEndereco;
	}

}
