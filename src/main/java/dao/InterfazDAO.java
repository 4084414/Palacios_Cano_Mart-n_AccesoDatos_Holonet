/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import clases.EscuadronDTO;
import java.util.List;

/**
 *
 * @author DAM2P
 */
public interface InterfazDAO {
    // Registrar un escuadrón
    void registrarEscuadron(EscuadronDTO escuadron);

    // Obtener todos los escuadrones
    List<EscuadronDTO> obtenerTodos();

    // Buscar escuadrones con un número mínimo de cazas
    List<EscuadronDTO> obtenerPorNumeroCazas(int numeroCazas);
}
