/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Repository.UserDAO;
import Model.User;
import java.sql.*;

/**
 *
 * @author fabri
 */
public class JDBCUserDAO implements UserDAO {

    private Connection conn;
    private Statement set;
    private ResultSet rs;

    public void dbConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Formula1", "app", "app");
            System.out.println("Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Not Connected. ");
            System.out.println(e);
        }
    }

    @Override
    public User find(int id) {
        String query = "SELECT ...";
        User user = new User();
        return user;
    }

    @Override
    public boolean insert(User user) {
        boolean inserted = false;
        int insertedId = -1;
        String query = "INSERT INTO users (NOMBRE, CIUDAD, PAIS, LONGITUD, "
                + "N_C"
                + "URVAS, N_VUELTAS) VALUES ('" + user.getPname() + "',"
                + "'" + user.getSname1() + "','" + user.getSname2() + "'," + user.getEmail() + ","
                + "" + user.getPass() + "," + user.getBday() + "," + user.getAddress() + "," + user.getPcode() + ","
                + "" + user.getCountry() + "," + user.getBoughtFlights() + ")";
        try {
            set = conn.createStatement();
            insertedId = set.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs.close();
            set.close();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (insertedId > -1) {
            inserted = true;
        }
        return inserted;
    }

    @Override
    public boolean update(User user) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE users SET prename=" + user.getPname() + ","
                + "surname1=" + user.getSname1() + ", surname2=" + user.getSname2() + ", "
                + "email= " + user.getEmail() + ",password= " + user.getPass() + ","
                + "birthday= " + user.getBday() + ", address= " + user.getAddress() + ", "
                + "postalcode= " + user.getPcode() + ", country= " + user.getCountry() + ","
                + "boughtFlights=" + user.getBoughtFlights()
                + "WHERE id = " + user.getId() + ";";
        try {
            set = conn.createStatement();
            updatedId = set.executeUpdate(query, Statement.SUCCESS_NO_INFO);
            rs.close();
            set.close();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (updatedId > 0) {
            updated = true;
        }
        return updated;
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        int deletedId = -1;
        String query = "DELETE FROM users WHERE id=" + id + ";";
        try {
            set = conn.createStatement();
            deletedId = set.executeUpdate(query, Statement.SUCCESS_NO_INFO);
            rs.close();
            set.close();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (deletedId > -1) {
            deleted = true;
        }
        return deleted;
    }

}
