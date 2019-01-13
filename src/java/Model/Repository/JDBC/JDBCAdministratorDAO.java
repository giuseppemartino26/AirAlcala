/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Administrator;
import Model.Repository.AdministratorDAO;
import java.sql.*;
import java.util.ArrayList;

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
            rsObj.close();
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
            dbDisconnect();
        } catch(SQLException e){
            System.out.println("Error Retrieving Data. " + e);
        }
        return adminList;
    }
    
    public Administrator findbyEmail(String email) {
        Administrator admin = null;
        String query = "SELECT * FROM administrators WHERE email = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, email);
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
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, admin.getEmail());
            stmtObj.setString(2,admin.getPass());
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
        String query = "UPDATE administrators SET email= ?, pass= ?,"
                + "prename= ?, surname1= ?,surname2= ?"
                + "WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
           
            stmtObj.setString(1,admin.getEmail());
            stmtObj.setString(2,admin.getPass());            
            stmtObj.setString(3,admin.getPname());
            stmtObj.setString(4,admin.getSname1());
            stmtObj.setString(5,admin.getSname2());
            stmtObj.setInt(6, admin.getId());
            
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
