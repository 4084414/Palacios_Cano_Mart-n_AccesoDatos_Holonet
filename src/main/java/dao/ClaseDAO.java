/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import clases.EscuadronDTO;
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
 * @author DAM2P
 */
public class ClaseDAO implements InterfazDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/starwars_ad";
    private static final String USER = "openpg";
    private static final String PASS = "openpgpwd";

    public void crearTabla() {
        // Sentencia de descripción de datos (DDL) con verificación segura
        String sql = "CREATE TABLE IF NOT EXISTS naves_imperiales ("
                + "idEscuadeon VARCHAR(10) PRIMARY KEY, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "sectorBase VARCHAR(50) NOT NULL, "
                + "numeroCazas INT NOT NULL"
                + ");";

        System.out.println("⏳ Estableciendo canal seguro con el terminal del SGBD...");

        // El bloque try-with-resources cierra la conexión y el statement de manera automática
        try (Connection con = DriverManager.getConnection(URL, USER, PASS); Statement stmt = con.createStatement()) {

            stmt.execute(sql);
            System.out.println("✔ Infraestructura DDL completada: Tabla 'naves_imperiales' verificada en el sector.");

        } catch (SQLException e) {
            System.err.println("❌ Error en la red de holocomunicaciones DDL: " + e.getMessage());
        }
    }

    
    public void registrarEscuadron(EscuadronDTO escuadron) {
        // Sentencia parametrizada para neutralizar la Inyección SQL maliciosa
        String sql = "INSERT INTO naves_imperiales (idEscuadeon, nombre, sectorBase, numeroCazas) VALUES (?, ?, ?, ?) ON CONFLICT (idEscuadeon) DO NOTHING;";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS); PreparedStatement pstmt = con.prepareStatement(sql)) {

            // Parametrización fuertemente tipada de los datos de la nave
            pstmt.setString(1, escuadron.getIdEscuadron());
            pstmt.setString(2, escuadron.getNombre());
            pstmt.setString(3, escuadron.getSectorBase());
            pstmt.setInt(4, escuadron.getNumeroCazas());

            int registrosAfectados = pstmt.executeUpdate();
            System.out.println("✔ Operación DML completada. Registros añadidos al hangar: " + registrosAfectados);

        } catch (SQLException e) {
            System.err.println("❌ Registro cancelado. Error en la matriz de persistencia [" + escuadron.getNombre() + "]: " + e.getMessage());
        }
    }

    public void registrar() {
        registrarEscuadron(new EscuadronDTO("N-001", "Los panas", "El sótano de su madre", 20));
        registrarEscuadron(new EscuadronDTO("N-002", "Escuadrón Rojo", "Sistema Yavin", 12));
        registrarEscuadron(new EscuadronDTO("N-003", "Escuadrón Oro", "Sistema Sullust", 12));
        registrarEscuadron(new EscuadronDTO("N-004", "Escuadrón Infernal", "Coruscant", 6));
    }
    
    //Muestra todos los escuadrones
    public void consultarTodo() {
        System.out.println("Escuadrones registrados:");

        for (EscuadronDTO escuadron : obtenerTodos()) {
            System.out.println(escuadron);
        }
    }

    // Mostrar escuadrones con al menos 10 cazas
    public void consultarAlgo() {
        System.out.println("Escuadrones con 10 o más cazas:");

        for (EscuadronDTO escuadron : obtenerPorNumeroCazas(10)) {
            System.out.println(escuadron);
        }
    }


    public List<EscuadronDTO> obtenerTodos() {
        String sql = "SELECT idEscuadeon, nombre, sectorBase, numeroCazas " + "FROM naves_imperiales";

        List<EscuadronDTO> flotaDetectada = new ArrayList<>();
        
        // Gestión robusta: la Connection y el PreparedStatement se cierran solos al salir del bloque
        try (Connection con = DriverManager.getConnection(URL, USER, PASS); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("idEscuadeon");
                String nombre = rs.getString("nombre");
                String base = rs.getString("sectorBase");
                int naves = rs.getInt("numeroCazas");
                
                // Superamos el desfase mapeando los datos de la tupla a una instancia de Objeto Java
                flotaDetectada.add(new EscuadronDTO(id, nombre, base, naves)
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar los escuadrones: "+ e.getMessage());
        }

        return flotaDetectada;
    }

    public List<EscuadronDTO> obtenerPorNumeroCazas(int numeNaves) {
        String sql = "SELECT idEscuadeon, nombre, sectorBase, numeroCazas FROM naves_imperiales WHERE numeroCazas >= ?;";
        List<EscuadronDTO> flotaDetectada = new ArrayList<>();

        // Gestión robusta: la Connection y el PreparedStatement se cierran solos al salir del bloque
        try (Connection con = DriverManager.getConnection(URL, USER, PASS); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, numeNaves);

            // El ResultSet se declara dentro de su propio try de recursos secundario
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("idEscuadeon");
                    String nombre = rs.getString("nombre");
                    String base = rs.getString("sectorBase");
                    int naves = rs.getInt("numeroCazas");

                    // Superamos el desfase mapeando los datos de la tupla a una instancia de Objeto Java
                    flotaDetectada.add(new EscuadronDTO(id, nombre, base, naves));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Fallo en el barrido de holocron-consultas: " + e.getMessage());
        }
        return flotaDetectada;

    }
}
