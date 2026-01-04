/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class conexion {
         Connection cn;
public Connection conectar() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        // Agregamos (Connection) al inicio para que Java acepte la asignación
        cn = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost/proyectospotify", "root", "");
        System.out.println("CONECTADO");
    } catch (Exception e) {
        System.out.println("Error de conexion: " + e);
    }
    return cn;
}
}
