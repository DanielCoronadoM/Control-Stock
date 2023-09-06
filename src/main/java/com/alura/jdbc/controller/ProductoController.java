package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;


import java.sql.*;
import java.util.List;


public class ProductoController {

	private ProductoDAO productoDAO;

	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().RecuperaConexion());
	}



	public void guardar(Producto producto)  {
		productoDAO.guardar(producto);
	}

	public int modificar(Producto producto){
		return productoDAO.modificar(producto);
	}

	/*
	 *Refactorizar a Model View Controller
	 */
	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar() {
		return productoDAO.listar();
	}

}
