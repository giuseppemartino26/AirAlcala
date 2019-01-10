/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Helpers.SecurePasswordHelper;
import Model.Repository.UserDAO;
import Model.User;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


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
            System.out.println("Not Connected. "+e);
        }
        return connObj;
    }
    
    public void dbDisconnect() {
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
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            
            if (rsObj.next()) {
                user = new User();

                user.setId(rsObj.getInt("id"));
                user.setPname(rsObj.getString("prename"));
                user.setSname1(rsObj.getString("surname1"));
                user.setSname2(rsObj.getString("surname2"));
                user.setEmail(rsObj.getString("email"));
                user.setPass(rsObj.getString("pass"));
                user.setBday(rsObj.getDate("birthday"));
                user.setAddress(rsObj.getString("address"));
                user.setPcode(rsObj.getString("postalcode"));
                user.setCity(rsObj.getString("city"));
                user.setCountry(rsObj.getString("country"));
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return user;
    }
    
    public ArrayList<User> findAll(){
        ArrayList<User> userList = new ArrayList<User>();
        String query = "SELECT * FROM users";
        
        try{
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();
            
            while(rsObj.next()){
                User user = new User();
                user.setId(rsObj.getInt("id"));
                user.setPname(rsObj.getString("prename"));
                user.setSname1(rsObj.getString("surname1"));
                user.setSname2(rsObj.getString("surname2"));
                user.setEmail(rsObj.getString("email"));
                user.setPass(rsObj.getString("pass"));
                user.setBday(rsObj.getDate("birthday"));
                user.setAddress(rsObj.getString("address"));
                user.setPcode(rsObj.getString("postalcode"));
                user.setCity(rsObj.getString("city"));
                user.setCountry(rsObj.getString("country"));
                userList.add(user);
            }
            dbDisconnect();
        } catch(SQLException e){
            System.out.println("Error Retrieving Data. " + e);
        }
        return userList;
    }

    @Override
    public boolean insert(User user) {
        boolean inserted = false;
        int insertedId = 0;
        
        if(user.getPass().equals("")){
             String query = "INSERT INTO users (prename, surname1, surname2, email,"
                + "birthday, address, postalcode, city, country ) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
             
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, user.getPname());
                stmtObj.setString(2,user.getSname1());
                stmtObj.setString(3,user.getSname2());
                stmtObj.setString(4,user.getEmail());
                stmtObj.setDate(5, (Date)user.getBday());
                stmtObj.setString(6,user.getAddress());
                stmtObj.setString(7,user.getPcode());
                stmtObj.setString(8,user.getCity());
                stmtObj.setString(9, user.getCountry());

                insertedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }
        } else{
            String query = "INSERT INTO users (prename, surname1, surname2, email,"
                + "pass, birthday, address, postalcode, city, country ) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
             
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, user.getPname());
                stmtObj.setString(2,user.getSname1());
                stmtObj.setString(3,user.getSname2());
                stmtObj.setString(4,user.getEmail());
                stmtObj.setString(5,user.getPass());
                stmtObj.setDate(6, (Date)user.getBday());
                stmtObj.setString(7,user.getAddress());
                stmtObj.setString(8,user.getPcode());
                stmtObj.setString(9,user.getCity());
                stmtObj.setString(10, user.getCountry());

                insertedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }            
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
        String query = "UPDATE users SET prename= ?, surname1= ?,"
                + "surname2= ?, email= ?,pass= ?, birthday=  ?, "
                + "address= ?, postalcode= ?, city=?, country= ?"
                + "WHERE id = ?";
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
            stmtObj.setString(9,user.getCity());
            stmtObj.setString(10, user.getCountry());
            stmtObj.setInt(11,user.getId());
            
            updatedId = stmtObj.executeUpdate();
            
            dbDisconnect();
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
        String query = "DELETE FROM users WHERE id=?";
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
    
    @Override
    public int login(String email, String pass){
         User user = null;
         int user_id = 0;
         String query = "SELECT * FROM users WHERE email = ?;";
         
         try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, email);
            rsObj = stmtObj.executeQuery();
            
            if(rsObj.next()){
                user = new User();          
                user.setId(rsObj.getInt("id"));
                user.setPname(rsObj.getString("prename"));
                user.setSname1(rsObj.getString("surname1"));
                user.setSname2(rsObj.getString("surname2"));
                user.setEmail(rsObj.getString("email"));
                user.setPass(rsObj.getString("pass"));
                user.setBday(rsObj.getDate("birthday"));
                user.setAddress(rsObj.getString("address"));
                user.setPcode(rsObj.getString("postalcode"));
                user.setCity(rsObj.getString("city"));
                user.setCountry(rsObj.getString("country"));
                
                user_id = user.getId();
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
         
        SecurePasswordHelper sec = new SecurePasswordHelper();
        try {
            if(sec.validatePassword(pass, user.getPass())){
                return user_id;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(JDBCUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0; 
    }
}