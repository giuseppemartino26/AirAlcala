/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Controller.userController;
import Helpers.SecurePasswordHelper;
import Model.Administrator;
import Model.Repository.AdministratorDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class JDBCAdministratorDAO implements AdministratorDAO {

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
            //rsObj.close();
            stmtObj.close();
            connObj.close();
	} catch (Exception exObj) {
            exObj.printStackTrace();
        }		
    }

    @Override
    public Administrator find(int id) {
        Administrator admin = new Administrator();
        String query = "SELECT * FROM administrators WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            
            if(rsObj.next()){
                admin.setId(rsObj.getInt("id"));
                admin.setPname(rsObj.getString("prename"));
                admin.setSname1(rsObj.getString("surname1"));
                admin.setSname2(rsObj.getString("surname2"));
                admin.setEmail(rsObj.getString("email"));
                admin.setPass(rsObj.getString("pass"));
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return admin;
    }
    
    public ArrayList<Administrator> findAll(){
        ArrayList<Administrator> adminList = new ArrayList<Administrator>();
        String query = "SELECT * FROM administrators";

        try{
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();
            
            while(rsObj.next()){
                Administrator admin = new Administrator();
                admin.setId(rsObj.getInt("id"));
                admin.setPname(rsObj.getString("prename"));
                admin.setSname1(rsObj.getString("surname1"));
                admin.setSname2(rsObj.getString("surname2"));
                admin.setEmail(rsObj.getString("email"));
                admin.setPass(rsObj.getString("pass"));
            
                adminList.add(admin);
            }
            rsObj.close();
            dbDisconnect();
        } catch(SQLException e){
            System.out.println("Error Retrieving Data. " + e);
        }
        return adminList;
    }
    
    public Administrator findbyEmail(String email) {
        Administrator admin = null;
        String query = "SELECT * FROM administrators WHERE email = '"+email+"'";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            //stmtObj.setString(1, "'"+email+"'"); //whit string only works qith ''
            rsObj = stmtObj.executeQuery();
            
            if (rsObj.next()) {
                admin = new Administrator();

                admin.setId(rsObj.getInt("id"));
                admin.setPname(rsObj.getString("prename"));
                admin.setSname1(rsObj.getString("surname1"));
                admin.setSname2(rsObj.getString("surname2"));
                admin.setEmail(rsObj.getString("email"));
                admin.setPass(rsObj.getString("pass"));
              }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return admin;
    }

    @Override
    public boolean insert(Administrator admin) {
        boolean inserted = false;
        int insertedId = 0;
        
        String query = "INSERT INTO administrators (email, pass, prename, surname1, surname2) "
                + "VALUES (?,?,?,?,?)";
        
        // Insert Password as a salted hash with 1000 iterations
        String securePass = "";        
        SecurePasswordHelper sec = new SecurePasswordHelper();
        
        // Convert Password into secure hash using helper class
        try {
            securePass = sec.generateSecurePasswordHash(admin.getPass());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, admin.getEmail());
            stmtObj.setString(2,securePass);
            stmtObj.setString(3,admin.getPname());
            stmtObj.setString(4,admin.getSname1());
            stmtObj.setString(5,admin.getSname2());
            
            insertedId = stmtObj.executeUpdate();
            
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
    public boolean update(Administrator admin) {
        boolean updated = false;
        int updatedId = 0; 
        
        // IMPORTANT Diferentiate two cases: 1. Psasword has not been changed -> Must remain unchanged in DB
        if(admin.getPass().equals("")){
            String query = "UPDATE administrators SET email= ?, "
                    + "prename= ?, surname1= ?,surname2= ?"
                    + "WHERE id = ?";
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);

                stmtObj.setString(1,admin.getEmail());
                stmtObj.setString(2,admin.getPname());
                stmtObj.setString(3,admin.getSname1());
                stmtObj.setString(4,admin.getSname2());
                stmtObj.setInt(5, admin.getId());

                updatedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }
        // IMPORTANT If Password was changed change it by replacing secure hash value    
        } else{
            // Convert Password into secure hash using helper class
            String securePass = "";
            SecurePasswordHelper sec = new SecurePasswordHelper();
            
            try {
                securePass = sec.generateSecurePasswordHash(admin.getPass());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String query = "UPDATE administrators SET email= ?, pass = ?, "
                    + "prename= ?, surname1= ?,surname2= ?"
                    + "WHERE id = ?";
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, admin.getEmail());
                stmtObj.setString(2, securePass);
                stmtObj.setString(3,admin.getPname());
                stmtObj.setString(4,admin.getSname1());
                stmtObj.setString(5,admin.getSname2());
                stmtObj.setInt(6, admin.getId());

                updatedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }            
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
        String query = "DELETE FROM administrators WHERE id=?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1,id);

            deletedId = stmtObj.executeUpdate();
           
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        if (deletedId > 0) {
            deleted = true;
        }
        return deleted;
    }    
}
