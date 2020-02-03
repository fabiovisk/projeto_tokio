package com.example.api.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.api.domain.Endereco;

public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Long>{
	

		List<Endereco> findAllByOrderByLogradouroAsc();
		List<Endereco> findByLogradouro(String logradouro);

	}

