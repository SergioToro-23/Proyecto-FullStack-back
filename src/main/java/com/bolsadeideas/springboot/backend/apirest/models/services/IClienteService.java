package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteService {//a esta interfaz se le da el contrato de implementacion osea el CRUD
	
	public List<Cliente> findAll();//traer todos
	
	public Cliente findById(Long id);//consultar
	
	public Cliente save(Cliente cliente);//recibir y almacenar 
	
	public void delete(Long id);//delete
}
