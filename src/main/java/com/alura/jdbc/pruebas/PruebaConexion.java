package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        /**
         * Llama al metodo para crear la conexion con la DB
         */
        Connection con = new ConnectionFactory().RecuperaConexion();

        System.out.println("Cerrando la conexión");

        con.close();
    }

}
