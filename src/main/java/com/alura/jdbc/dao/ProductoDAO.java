/**
 * Esta clase contiene tod0 el codigo necesario para ejecutar peristencias a la base de datos, es decir,
 * modificaciones a la DB
 *
 * Contiene toda la logica de "Querys"
 */

package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {
    final private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardar(Producto producto) {
        final Connection con = new ConnectionFactory().RecuperaConexion();
        try (con) {
            final PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad)"
                            + "VALUES (?, ? ,?) ",
                    Statement.RETURN_GENERATED_KEYS);

            try (statement) {
                guardarRegistro(producto, statement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(Producto producto) {
        final Connection con = new ConnectionFactory().RecuperaConexion();
        try(con) {

            final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET"
                    + " NOMBRE =  ?, "
                    + " DESCRIPCION = ?, "
                    + " CANTIDAD = ? "
                    + " WHERE ID = ?");
            try(statement) {

                modificarRegistro(producto, statement);

                return statement.getUpdateCount(); //No. de filas modificadas
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public int eliminar(Integer id) {
        final Connection con = new ConnectionFactory().RecuperaConexion();
        try(con) {

            final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");
            try(statement) {

                statement.setInt(1, id);
                statement.execute();

                return statement.getUpdateCount();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.RecuperaConexion();

        try (con) {
            final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

            try (statement) {
                statement.execute();

                final ResultSet resultSet = statement.getResultSet();

                try (resultSet) {
                    while (resultSet.next()) {
                        Producto fila = new Producto(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),resultSet.getString("DESCRIPCION"), resultSet.getInt("CANTIDAD"));


                        resultado.add(fila);
                    }
                }
                return resultado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*
     * recupera los valores para el prepare statement y ejecuta el registro para guardar un producto
     */
    private static void guardarRegistro(Producto producto, PreparedStatement statement) throws SQLException {

        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());

        statement.execute();

        /*
         *Try with resources
         * Cierra automaticamente los recursos que se le indican
         */
        final ResultSet resultSet = statement.getGeneratedKeys();
        try (resultSet) {
            while (resultSet.next()) {
                producto.setId(resultSet.getInt(1));
                System.out.println(String.format("Fue insertado el producto %s", producto));
            }
        }
    }

    private static void modificarRegistro(Producto producto, PreparedStatement statement) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getId());

        statement.execute();
    }


}
