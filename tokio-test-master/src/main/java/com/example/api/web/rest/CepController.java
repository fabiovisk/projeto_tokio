package com.example.api.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.domain.Endereco;
import com.example.api.service.EnderecoService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;



@RestController
@RequestMapping("/enderecos")
public class CepController {

	@Autowired
	private EnderecoService servico;

	@Autowired
	public CepController(EnderecoService servico) {
		this.servico = servico;
	}

	@GetMapping(path = "/endereco-id/{id}")
	public Optional<Endereco> getEnderecoById(@PathVariable("id") Long id ) {
//		verifyIsEnderecoExist(id);
		Optional<Endereco> endereco = servico.findById(id);
		return endereco;
	}
	
	@GetMapping(path = "/findByLogradouro/{logradouro}")
	public ResponseEntity<?> findEnderecoByLogradouro(@PathVariable String logradouro) {
		return new ResponseEntity<>(servico.findByLogradouro(logradouro), HttpStatus.OK);
	}
	
	@GetMapping(path = "/enderecos")
	public ResponseEntity<?> findAll(Pageable pageable) {
		return new ResponseEntity<>(servico.findAll(pageable), HttpStatus.OK);
	}

	@PostMapping(path = "/endereco/save")
	public ResponseEntity<?> save(@Valid @RequestBody Endereco endereco) {
		return new ResponseEntity<>(servico.save(endereco), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/endereco/delete/{id}")
	@PreAuthorize("ADMIN")
	public ResponseEntity<?> deleteEndereco(@PathVariable Long id) {
//		verifyIsEnderecoExist(id);
		servico.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/endereco/update/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
//		verifyIsEnderecoExist(customer.getId());
		servico.save(endereco);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	private void verifyIsEnderecoExist(Long id) {
//		if (servico.findById(id) == null)
//			throw new EnderecoResourceException("Endereco NOT FOUND Id:"+id);
//	}
//	
	
	
	
	
	
	
	private List<Endereco> listagem = new ArrayList<Endereco>();
	
	private Endereco endereco;
	
	private String cep;
	

	public Endereco carregarEndereco() {
		endereco = new Endereco();
		Client c = Client.create();
		WebResource wr = c.resource("http://viacep.com.br/ws/" + this.getCep() + "/json/");
		System.out.println("CHAMOU O URI....");
		this.endereco = servico.buscarEnderecoPor(wr.get(String.class));
		String JSON = wr.get(String.class);
		System.out.println(JSON);
		return this.getEndereco();

	}

	public List<Endereco> getListagem() {
		return listagem;
	}

	public void setListagem(List<Endereco> listagem) {
		this.listagem = listagem;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void limpar() {
		this.endereco = new Endereco();
	}

}
