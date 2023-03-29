package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {//crud repository es una interfaz que tiene metodos para un CRUD, ahorra mucho
//clase de acceso a datos, accede a realiazr consultas y operaciones a la BD
	//clase Dao, Repository
}
