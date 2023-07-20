package com.sistema.carrito.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity	
@Table(name = "orden")
public class Orden {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY	)
	private Integer id;
	private String numero;
	private Date fechaCreacion;
	private Date fechaRecibida;
	private double total;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToOne
	private DetalleOrden detalleOrden;
	
	public Orden() {
		// TODO Auto-generated constructor stub
	}
		
	public Orden(Integer id, String numero, Date fechaCreacion, Date fechaRecibida, double total, Usuario usuario,
			DetalleOrden detalleOrden) {
		super();
		this.id = id;
		this.numero = numero;
		this.fechaCreacion = fechaCreacion;
		this.fechaRecibida = fechaRecibida;
		this.total = total;
		this.usuario = usuario;
		this.detalleOrden = detalleOrden;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(Date fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	

	public DetalleOrden getDetalleOrden() {
		return detalleOrden;
	}

	public void setDetalleOrden(DetalleOrden detalleOrden) {
		this.detalleOrden = detalleOrden;
	}

	@Override
	public String toString() {
		return "Orden [id=" + id + ", numero=" + numero + ", fechaCreacion=" + fechaCreacion + ", fechaRecibida="
				+ fechaRecibida + ", total=" + total + ", usuario=" + usuario + ", detalleOrden=" + detalleOrden + "]";
	}
	
}
