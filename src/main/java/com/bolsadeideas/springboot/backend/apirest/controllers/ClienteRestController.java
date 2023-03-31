package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.HashMap;
//import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> show(@PathVariable Long id){//response entity, cualquier tipo de objeto
		
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();//mensaje de error, pares de nombres con sus valores tipo object
		
		try {//otros errores
			
			cliente = clienteService.findById(id);//encuentra el cliente requerido por el id
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);//es este error por ser de base de datos
		}
		
		if (cliente == null) {
			response.put("mensaje", "El cliente ID:" .concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//404 cuando falla
		}
		
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);  
	}
	
	//recibe objeto cliente, contiene los datos para que se persistan
	@PostMapping("/clientes")	
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {//nos envian el cliente en json, dentro del cuerpo del request, por eso la anotacion para que tome los datos y los mapee al objeto cliente
		
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		try {
			clienteNew = clienteService.save(cliente);//insert sin id
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);//es este error por ser de base de datos
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);//responde con 201 de creado correctamente
	
	}
	
	
	@PutMapping("/clientes/{id}")	
	public ResponseEntity<?> update(@Valid @RequestBody  Cliente cliente, @PathVariable Long id) {//cliente esta dentro del cuerpo del request(Requesbody), ademas el id(pathvariable)
		
		Cliente clienteUpdated = null;
		Cliente clienteActual = clienteService.findById(id);//obtener el cliente de la base de datos por su id
		
		Map<String, Object> response = new HashMap<>();
		
		if (clienteActual == null) {
			response.put("mensaje", "Error, no se pudo editar, el cliente ID: " .concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);//404 cuando falla
		}
		
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			
			clienteUpdated = clienteService.save(clienteActual);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);//es este error por ser de base de datos
		}
		
		
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);//sirve de update
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			clienteService.delete(id);//sprin con CRUD repository valida que el cliente exista
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);//es este error por ser de base de datos
		}
		
		response.put("mensaje", "El cliente con ID: " .concat(id.toString().concat(" ha sido eliminado con éxito!")));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}
