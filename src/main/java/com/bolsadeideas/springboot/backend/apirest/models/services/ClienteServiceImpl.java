package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

@Service//componente de servicio, se va a guardar en el contenedor de spring, va a quedar en el contexto, despues se puede inyectar este objeto(bean) en el controlador
public class ClienteServiceImpl implements IClienteService{

	@Autowired//inyectar, inyeccion de dependencias
	private IClienteDao clienteDao;
	@Override
	@Transactional(readOnly = true)//manejar transaccion, y como es un select pues es solo lectura
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();//porque devuelve una lista de clientes
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);//.orElse retorna el objeto cliente de lo contrario un null
	}
	
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {		
		return clienteDao.save(cliente);//retorna la entidad guardada en la base de datos
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);//internamente obtiene la entidad, el cliente y lo elimina 
		//clienteDao.delete(findById(id));//se le envia una entidad atachada en el contexto de persistencia, osea en la sesion de jpa
	}

}
