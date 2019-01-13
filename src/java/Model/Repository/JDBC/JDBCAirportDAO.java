/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Helpers.SecurePasswordHelper;
import Model.Repository.AirportDAO;
import Model.Airport;
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
public class JDBCAirportDAO implements AirportDAO{
    
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
    public Airport find(int id) {
        Airport airport = null;
        String query = "SELECT * FROM airports WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            
            if (rsObj.next()) {
                airport = new Airport();

                airport.setId(rsObj.getInt("id"));
                airport.setName(rsObj.getString("name"));
                airport.setCountry(rsObj.getString("country"));
                airport.setTax(rsObj.getInt("tax"));
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return airport;
    }
    
        public ArrayList<Airport> findAll(){
        ArrayList<Airport> airportList = new ArrayList<Airport>();
        String query = "SELECT * FROM airports";
        
        try{
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();
            
            while(rsObj.next()){
                Airport airport = new Airport();
                airport.setId(rsObj.getInt("id"));
                airport.setName(rsObj.getString("name"));
                airport.setCountry(rsObj.getString("country"));
                airport.setTax(rsObj.getInt("tax"));
            }
            dbDisconnect();
        } catch(SQLException e){
            System.out.println("Error Retrieving Data. " + e);
        }
        return airportList;
    }
        
    @Override
    public boolean insert(Airport airport) {
        boolean inserted = false;
        int insertedId = 0;
        
        if (airport.getName().equals("")){
             String query = "INSERT INTO airports (name, country, tax)"
                + "VALUES (?,?,?)";
             
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, airport.getName());
                stmtObj.setString(2,airport.getCountry());
                stmtObj.setInt(3,airport.getTax());
                
                insertedId = stmtObj.executeUpdate();

                dbDisconnect();
            } catch (SQLException e) {
                System.out.println("Not inserted. " + e);
            }
        } else{
            String query = "INSERT INTO airports (name, country, tax)"
                + "VALUES (?,?,?)";
             
            try {
                connObj = dbConnect();
                stmtObj = connObj.prepareStatement(query);
                stmtObj.setString(1, airport.getName());
                stmtObj.setString(2,airport.getCountry());
                stmtObj.setInt(3,airport.getTax());

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
    public boolean update(Airport airport) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE airports SET name= ?, country= ?, tax= ?"
                + "WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            
            stmtObj.setString(1, airport.getName());
            stmtObj.setString(2,airport.getCountry());
            stmtObj.setInt(3,airport.getTax());

            
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
        String query = "DELETE FROM airports WHERE id=?";
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
