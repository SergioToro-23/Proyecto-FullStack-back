package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name="clientes")//no necesaria si la clase se llama igual a la tabla
public class Cliente implements Serializable {// clase entity, de persistencia, POJO, por eso se importa serializable

	//cada atributo corresponde o esta mapeado a una tabla de la BD
	//Parete del contexto de persistencia de JPA, esta sincornizado con la BD
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@Column(nullable=false)
	@NotNull(message = "El nombre no puede ser nulo")
	private String nombre;
	@NotNull(message = "El apellido no puede ser nulo")//con la dependencia en el pom.xml de javax.validation.constraints.NotNull
	private String apellido;
	@Column(nullable=false, unique=true)
	@NotNull(message = "El email no puede ser nulo")
	private String email;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

	public Long getId() {
		return id;
	}

	//evento pre persist, antes de un save que incluya la fecha
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	private static final long serialVersionUID = 1L;

}
