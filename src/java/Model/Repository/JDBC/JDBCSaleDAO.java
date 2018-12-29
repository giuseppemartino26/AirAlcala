yy/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.CreditCard;
import Model.Flight;
import Model.Repository.CreditCardDAO;
import Model.Repository.FlightDAO;
import Model.Sale;
import java.sql.*;
import Model.Repository.SaleDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.util.ArrayList;


/**
 *
 * @author fabri
 */
public class JDBCSaleDAO implements SaleDAO {

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
    public Sale find(int id) {
        Sale sale = null;
        String query = "SELECT * FROM sales WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            
            FlightDAO flightDAO = new JDBCFlightDAO();
            Flight flight = flightDAO.find(rsObj.getInt("flight_id"));
            
            UserDAO userDAO = new JDBCUserDAO();
            User user = userDAO.find(rsObj.getInt("user_id"));
            
            CreditCardDAO ccDAO = new JDBCCreditCardDAO();
            CreditCard cc = ccDAO.find(rsObj.getInt("credit_card"));
            
            sale = new Sale(rsObj.getInt("id"), flight ,
            user,rsObj.getString("place"), rsObj.getInt("number_luggages"),
            cc);
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return sale;
    }
    
        public ArrayList<Sale> findAll(){
        ArrayList<Sale> saleList = new ArrayList<Sale>();
        String query = "SELECT * FROM sales";
        
        try{
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();
            
            while(rsObj.next()){
                FlightDAO flightDAO = new JDBCFlightDAO();
                Flight flight = flightDAO.find(rsObj.getInt("flight_id"));
                
                UserDAO userDAO = new JDBCUserDAO();
                User user = userDAO.find(rsObj.getInt("user_id"));
                
                CreditCardDAO ccDAO = new JDBCCreditCardDAO();
                CreditCard cc = ccDAO.find(rsObj.getInt("credit_card"));
                
                Sale sale = new Sale(rsObj.getInt("id"),flight,user,rsObj.getString("place"),
                rsObj.getInt("number_luggages"),cc);
                
                saleList.add(sale);
            }
            dbDisconnect();

        } catch(SQLException e){
            System.out.println("Error Retrieving Data. " + e);
        }
        return saleList;
    }

    @Override
    public boolean insert(Sale sale) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO sales (flight_id, user_id, place, number_luggages, credit_card) "
                + "VALUES (?,?,?,?,?)";
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, sale.getFlight().getId() );
            stmtObj.setInt(2,sale.getUser().getId());
            stmtObj.setString(3,sale.getPlace());
            stmtObj.setInt(4,sale.getNoLuggage());
            stmtObj.setInt(5, sale.getCreditCard().getId() );
            
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
    public boolean update(Sale sale) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE sales SET flight_id = ?, user_id = ?,"
                + "place = ?, number_luggages = ?,credit_card = ?"
                + "WHERE id = ?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
           
            stmtObj.setInt(1,sale.getFlight().getId());
            stmtObj.setInt(2,sale.getUser().getId());            
            stmtObj.setString(3,sale.getPlace());
            stmtObj.setInt(4, sale.getNoLuggage());
            stmtObj.setInt(5, sale.getCreditCard().getId());
            stmtObj.setInt(6, sale.getId());
            
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
        String query = "DELETE FROM sales WHERE id= ?;";
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
