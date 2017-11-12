package com.carlos.sistemat3.modelo;

public class Producto {
	private int id;
	private String nombre;
	private int precio;
	private int stock;
	
	
	public Producto(int id, String nombre, int stock, int precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.stock = stock;
		this.precio=precio;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	
	
	
}
