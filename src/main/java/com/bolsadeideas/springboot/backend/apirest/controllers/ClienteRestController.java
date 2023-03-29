package com.bolsadeideas.springboot.backend.apirest.controllers;

//import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"})//da acceso al dominio para que envie y reciba datos
@RestController
@RequestMapping("/api")//nuestro controlador api rest, con la ruta api
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;//se declara un beans con su tipo generico(interfaz), va a buscar el primer candidato que implemente esta interfaz
	
	//metodo index para listar los clientes
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll(); 
	}
	
	//metodo index para retornar solo un cliente
	@GetMapping("/clientes/{id}")//@ResponseStatus aca no es necesario porque por defecto devuelve 200 ok	
	public Cliente show(@PathVariable Long id){
		return clienteService.findById(id); 
	}
	
	//recibe objeto cliente, contiene los datos para que se persistan
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)//responde con 201 de creado correctamente
	public Cliente create(@RequestBody Cliente cliente) {//nos envian el cliente en json, dentro del cuerpo del request, por eso la anotacion para que tome los datos y los mapee al objeto cliente
		return clienteService.save(cliente);//sirve como insert, si no tiene un id
	}
	
	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {//cliente esta dentro del cuerpo del request(Requesbody), ademas el id(pathvariable)
		Cliente clienteActual = clienteService.findById(id);//obtener el cliente de la base de datos por su id
		
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		
		return clienteService.save(clienteActual);//sirve de update
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//retorna un 204
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
