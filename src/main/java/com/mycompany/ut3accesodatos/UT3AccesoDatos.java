/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ut3accesodatos;

import clases.EscuadronDTO;
import dao.ClaseDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martín Palacios Cano
 */
public class UT3AccesoDatos {

    private static final String URL = "jdbc:postgresql://localhost:5432/starwars_ad";
    private static final String USER = "openpg";
    private static final String PASS = "openpgpwd";

    public static void main(String[] args) {

        ClaseDAO dao = new ClaseDAO();

        dao.crearTabla();
        dao.registrar();
        dao.consultarTodo();
        dao.consultarAlgo();
    }

}