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

    private static Connection connObj;
    private static PreparedStatement stmtObj;
    private static ResultSet rsObj;
    
    private Connection dbConnect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connObj = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
            System.out.println("Connected.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Not Connected. ");
            System.out.println(e);
        }
        return connObj;
    }
    
    public static void dbDisconnect() {
	try {
            rsObj.close();
            stmtObj.close();
            connObj.close();
	} catch (Exception exObj) {
            exObj.printStackTrace();
        }		
	}

    @Override
    public User find(int id) {
        boolean found = false;
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj.close();
            stmtObj.close();
            
            user = new User(rsObj.getInt("id"), rsObj.getString("prename"),
            rsObj.getString("surname1"),rsObj.getString("surname2"),
            rsObj.getString("email"),rsObj.getString("pass"),rsObj.getDate("birthday"),
            rsObj.getString("address"),rsObj.getString("postalcode"),rsObj.getString("country"),
            rsObj.getInt("flihgts_bought"));
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return user;
    } 

    @Override
    public boolean insert(User user) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO users (prename, surname1, surname2, email,"
                + "pass, birthday, address, postalcode, country, boughtflights ) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, user.getPname());
            stmtObj.setString(2,user.getSname1());
            stmtObj.setString(3,user.getSname2());
            stmtObj.setString(4,user.getEmail());
            stmtObj.setString(5,user.getPass());
            stmtObj.setDate(6, (Date) user.getBday());
            stmtObj.setString(7,user.getAddress());
            stmtObj.setString(8,user.getPcode());
            stmtObj.setString(9, user.getCountry());
            stmtObj.setInt(10, user.getBoughtFlights());
            
            insertedId = stmtObj.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (insertedId > 0) {
            inserted = true;
        }
        return inserted;
    }

    @Override
    public boolean update(User user) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE users SET prename= ?, surname1= ?,"
                + "surname2= ?, email= ?,pass= ?, birthday=  ?, "
                + "address= ?, postalcode= ?, country= ?, boughtFlights= ?"
                + "WHERE id = ?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            
            stmtObj.setString(1, user.getPname());
            stmtObj.setString(2,user.getSname1());
            stmtObj.setString(3,user.getSname2());
            stmtObj.setString(4,user.getEmail());
            stmtObj.setString(5,user.getPass());
            stmtObj.setDate(6, (Date) user.getBday());
            stmtObj.setString(7,user.getAddress());
            stmtObj.setString(8,user.getPcode());
            stmtObj.setString(9, user.getCountry());
            stmtObj.setInt(10, user.getBoughtFlights());
            stmtObj.setInt(11,user.getId());
            
            updatedId = stmtObj.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rsObj.close();
            stmtObj.close();
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
        int deletedId = 0;
        String query = "DELETE FROM users WHERE id=?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1,id);

            deletedId = stmtObj.executeUpdate(query);
            rsObj.close();
            stmtObj.close();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (deletedId > 0) {
            deleted = true;
        }
        return deleted;
    }

}