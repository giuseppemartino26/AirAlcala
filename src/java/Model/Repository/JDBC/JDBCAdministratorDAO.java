/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Administrator;
import Model.Repository.AdministratorDAO;
import java.sql.*;

/**
 *
 * @author fabri
 */
public class JDBCAdministratorDAO implements AdministratorDAO {

    private Connection conn;

    public void dbConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
            System.out.println("Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Not Connected. ");
            System.out.println(e);
        }
    }

    @Override
    public Administrator find(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Administrator admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Administrator admin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
