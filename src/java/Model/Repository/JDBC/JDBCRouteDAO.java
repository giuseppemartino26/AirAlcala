/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Repository.JDBC;

import Model.Airplane;
import Model.Airport;
import Model.Repository.AirplaneDAO;
import Model.Repository.AirportDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author fabri
 */
public class JDBCRouteDAO implements RouteDAO {

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
    public Route find(int id) {
        Route route = new Route();
        String query = "SELECT * FROM routes WHERE id = ?";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, id);
            rsObj = stmtObj.executeQuery();
            if (rsObj.next()) {
                /*RouteDAO routeDAO = new JDBCRouteDAO();
                route = routeDAO.find(rsObj.getInt("id"));*/

                AirplaneDAO planeDAO = new JDBCAirplaneDAO();
                Airplane plane = planeDAO.find(rsObj.getInt("airplane_id"));

                AirportDAO airportDAO = new JDBCAirportDAO();
                Airport origin = airportDAO.findName(rsObj.getString("origin"));
                Airport destination = airportDAO.findName(rsObj.getString("destination"));

                route.setId(rsObj.getInt("id"));
                route.setOrigin(origin);
                route.setDestination(destination);
                route.setPlane(plane);
                route.setTicketPrice(rsObj.getInt("ticketprice"));
                route.setLuggagePrice(rsObj.getInt("luggageprice"));
                route.setTax(rsObj.getDouble("tax"));
            }
            dbDisconnect();
        } catch (SQLException e) {
            System.out.println("Not inserted. " + e);
        }
        return route;
    }

    @Override
    public boolean insert(Route route) {
        boolean inserted = false;
        int insertedId = 0;
        String query = "INSERT INTO routes (origin, destination, airplane_id, ticketprice, luggageprice, tax) "
                + "VALUES (?,?,?,?,?,?)";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            stmtObj.setInt(1, route.getOrigin().getId());
            stmtObj.setInt(2, route.getDestination().getId());
            stmtObj.setInt(3, route.getPlane().getId());
            stmtObj.setDouble(4, route.getTicketPrice());
            stmtObj.setDouble(5, route.getLuggagePrice());
            stmtObj.setDouble(5, route.getTax());

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
    public boolean update(Route route) {
        boolean updated = false;
        int updatedId = 0;
        String query = "UPDATE routes SET origin = ?, destination = ?,"
                + "airplane_id = ?, ticketprice = ?,luggageprice = ?, tax = ?"
                + "WHERE id = ?;";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);

            stmtObj.setInt(1, route.getOrigin().getId());
            stmtObj.setInt(2, route.getDestination().getId());
            stmtObj.setInt(3, route.getPlane().getId());
            stmtObj.setDouble(4, route.getTicketPrice());
            stmtObj.setDouble(5, route.getLuggagePrice());
            stmtObj.setDouble(6, route.getTax());
            stmtObj.setInt(7, route.getId());
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
        String query = "DELETE FROM routes WHERE id= ?;";
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
    public ArrayList<Route> findAll() {
        ArrayList<Route> routeList = new ArrayList<Route>();
        String query = "SELECT * FROM routes";

        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            rsObj = stmtObj.executeQuery();

            while (rsObj.next()) {
                RouteDAO routeDAO = new JDBCRouteDAO();
                Route route = routeDAO.find(rsObj.getInt("id"));

                AirplaneDAO planeDAO = new JDBCAirplaneDAO();
                Airplane plane = planeDAO.find(rsObj.getInt("airplane_id"));

                AirportDAO airportDAO = new JDBCAirportDAO();
                Airport origin = airportDAO.find(rsObj.getInt("origin"));
                Airport destination = airportDAO.find(rsObj.getInt("destination"));

                route.setId(rsObj.getInt("id"));
                route.setOrigin(origin);
                route.setDestination(destination);
                route.setPlane(plane);
                route.setTicketPrice(rsObj.getInt("ticketprice"));
                route.setLuggagePrice(rsObj.getInt("luggageprice"));
                route.setTax(rsObj.getDouble("tax"));

                routeList.add(route);
            }
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return routeList;
    }

    @Override
    public ArrayList<Route> findRoute(String origin, String destination, int passengers) {
        ArrayList<Route> routeList = new ArrayList<Route>();
        String query = "SELECT * FROM routes WHERE origin = '" + origin + "' AND destination = '" + destination + "'";
        try {
            connObj = dbConnect();
            stmtObj = connObj.prepareStatement(query);
            //stmtObj.setString(1, "'"+origin+"'");
            //stmtObj.setString(2, "'"+destination+"'");
            rsObj = stmtObj.executeQuery();
            while (rsObj.next()) {
                Route route = new Route();
                AirplaneDAO planeDAO = new JDBCAirplaneDAO();
                Airplane plane = planeDAO.find(rsObj.getInt("airplane_id"));

                AirportDAO airportDAO = new JDBCAirportDAO();
                Airport originAirpot = airportDAO.findName(rsObj.getString("origin"));
                Airport destinationAirpot = airportDAO.findName(rsObj.getString("destination"));

                route.setId(rsObj.getInt("id"));
                route.setOrigin(originAirpot);
                route.setDestination(destinationAirpot);
                route.setPlane(plane);
                route.setTicketPrice(rsObj.getInt("ticketprice"));
                route.setLuggagePrice(rsObj.getInt("luggageprice"));
                route.setTax(rsObj.getDouble("tax"));
                if (plane.getPlaces() > passengers) {
                    routeList.add(route);
                }
            }
            dbDisconnect();

        } catch (SQLException e) {
            System.out.println("Error Retrieving Data. " + e);
        }
        return routeList;
    }

}
