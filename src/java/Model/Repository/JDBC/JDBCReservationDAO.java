/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Flight;
import Model.Repository.FlightDAO;
import Model.Repository.ReservationDAO;
import Model.Repository.UserDAO;
import Model.Reservation;
import Model.User;
import java.sql.*;

/**
 *
 * @author fabri
 */
public class JDBCReservationDAO implements ReservationDAO {

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
    public Reservation find(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            
            UserDAO userDAO = new JDBCUserDAO();
            User user = userDAO.find(rsObj.getInt("user_id"));
            
            FlightDAO flightDAO = new JDBCFlightDAO();
            Flight flight = flightDAO.find(rsObj.getInt("flight_id"));
            
            reservation = new Reservation(rsObj.getInt("id"),
            user, flight);
            
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return reservation;
    }

    @Override
    public boolean insert(Reservation reservation) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO reservation (user_id, flight_id) "
                + "VALUES (?,?)";
        
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, reservation.getUser().getId() );
            stmtObj.setInt(2, reservation.getFlight().getId() );
            
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
    public boolean update(Reservation reservation) {
                boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE TABLE reservation SET user_id = ?, flight_id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
           
            stmtObj.setInt(1,reservation.getUser().getId());            
            stmtObj.setInt(2,reservation.getFlight().getId());
            
            updatedId = stmtObj.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
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
        String query = "DELETE FROM reservation WHERE id= ?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1,id);

            deletedId = stmtObj.executeUpdate(query);
            
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
