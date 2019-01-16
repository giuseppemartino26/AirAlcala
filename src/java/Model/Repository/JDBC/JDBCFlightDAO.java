/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Flight;
import Model.Repository.FlightDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fabri
 */
public class JDBCFlightDAO implements FlightDAO {

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
    public Flight find(int id) {
        Flight flight = new Flight();
        Route route = new Route();
        String query = "SELECT * FROM flights WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            if (rsObj.next()) {
                RouteDAO routeDAO = new JDBCRouteDAO();
                System.out.println(rsObj.getInt("route_id"));
                route = routeDAO.find(rsObj.getInt("route_id"));

                System.out.println(route.getId());
                flight.setId(rsObj.getInt("id"));
                flight.setLocator(rsObj.getString("locator"));
                flight.setRoute(route);
                flight.setDeparture(rsObj.getDate("departure_date"));
                flight.setDeparturetime(rsObj.getString("departure_time"));
                flight.setArrivaltime(rsObj.getString("arrival_time"));
                flight.setAvailableSeats(rsObj.getInt("available_seats"));
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return flight;
    }

    @Override
    public boolean insert(Flight flight) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO flights (locator, route_id, departure_date, departure_time, arrival_time, available_seats) "
                + "VALUES (?,?,?,?,?,?)";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, flight.getLocator());
            stmtObj.setInt(2, flight.getRoute().getId());
            stmtObj.setDate(3, flight.getDeparture());
            stmtObj.setString(4, flight.getDeparturetime());
            stmtObj.setString(5, flight.getArrivaltime());
            stmtObj.setInt(6, flight.getAvailableSeats());
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
    public boolean update(Flight flight) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE flights SET locator = ?, route_id = ?,"
                + "departure_date = ?, departure_time = ?, arrival_time = ?, available_seats = ?"
                + "WHERE id = ?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);

            stmtObj = connObj.prepareStatement(query);
            stmtObj.setString(1, flight.getLocator());
            stmtObj.setInt(2, flight.getRoute().getId());
            stmtObj.setDate(3, flight.getDeparture());
            stmtObj.setString(4, flight.getDeparturetime());
            stmtObj.setString(5, flight.getArrivaltime());
            stmtObj.setInt(6, flight.getAvailableSeats());
            stmtObj.setInt(7, flight.getId());

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
        String query = "DELETE FROM flights WHERE id= ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);

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
    public ArrayList<Flight> findAll() {
        ArrayList<Flight> flightList = new ArrayList<Flight>();
        String query = "SELECT * FROM flights";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                Flight flight = new Flight();
                RouteDAO routeDAO = new JDBCRouteDAO();
                Route route = routeDAO.find(rsObj.getInt("route_id"));

                flight.setId(rsObj.getInt("id"));
                flight.setLocator(rsObj.getString("locator"));
                flight.setRoute(route);
                flight.setDeparture(rsObj.getDate("departure_date"));
                flight.setDeparturetime(rsObj.getString("departure_time"));
                flight.setArrivaltime(rsObj.getString("arrival_time"));
                flight.setAvailableSeats(rsObj.getInt("available_seats"));
                
                flightList.add(flight);
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return flightList;
    }

    @Override
    public ArrayList<Flight> findFlights(Route route, String date) {
        ArrayList<Flight> flightList = new ArrayList<>();
        String query = "SELECT * FROM flights WHERE route_id = ? AND departure > '"+date+"'";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, route.getId());
            //stmtObj.setDate(2, date);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                Flight flight = new Flight();

                flight.setId(rsObj.getInt("id"));
                flight.setLocator(rsObj.getString("locator"));
                flight.setRoute(route);
                flight.setDeparture(rsObj.getDate("departure"));
                flight.setArrivaltime(rsObj.getString("departure_time"));
                flight.setArrivaltime(rsObj.getString("arrival_time"));
                flight.setAvailableSeats(rsObj.getInt("available_seats"));

                flightList.add(flight);
            }
            rsObj.close();
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return flightList;
    }

}
