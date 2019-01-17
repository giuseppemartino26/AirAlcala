/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Airport;
import Model.Flight;
import Model.Repository.AirportDAO;
import Model.Repository.FlightDAO;
import Model.Repository.JDBC.JDBCAirportDAO;
import Model.Repository.JDBC.JDBCFlightDAO;
import Model.Repository.JDBC.JDBCRouteDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pablo
 */
public class searchController extends HttpServlet {

    private RouteDAO routeDAO;
    private FlightDAO flightDAO;

    public searchController() {
        super();
        routeDAO = new JDBCRouteDAO();
        flightDAO = new JDBCFlightDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        String origin_name = (String) req.getParameter("source");
        String destination_name = (String) req.getParameter("destination");
        //Airport airport =new Airport();
        AirportDAO airportDAO = new JDBCAirportDAO();
        int origin = airportDAO.findName(origin_name).getId();
        int destination = airportDAO.findName(destination_name).getId();
        int passengers = Integer.parseInt(req.getParameter("passengers"));
        String departure_date = req.getParameter("departure_date");
        String comeback_date = req.getParameter("departure_date_2");
        System.out.println("fecha: " + departure_date);
        System.out.println("fecha: " + comeback_date);
        s.setAttribute("origin", origin_name);
        s.setAttribute("destination", destination_name);
        s.setAttribute("passengers", passengers);
        //Date departure_date = Date.valueOf(date1);
        ArrayList<Route> routes = routeDAO.findRoute(origin, destination);
        System.out.println(routes.toString());
        ArrayList<Flight> allFlights_departure = new ArrayList();
        ArrayList<Flight> allFlights_arrival = new ArrayList();
        for (int i = 0; i < routes.size(); i++) {
            allFlights_departure.addAll(flightDAO.findFlights(routes.get(i), departure_date, passengers));
            System.out.println(allFlights_departure.toString());
            //allFlights_departure.addAll(flights);
        }
        if (!comeback_date.equals("")) {
            ArrayList<Route> routesArrival = routeDAO.findRoute(destination, origin);
            for (int i = 0; i < routes.size(); i++) {
                ArrayList<Flight> flights = flightDAO.findFlights(routesArrival.get(i), comeback_date, passengers);
                System.out.println(flights.toString());
                allFlights_arrival.addAll(flights);
                req.setAttribute("flights_arrival", allFlights_arrival);
            }
        }
        RequestDispatcher view = req.getRequestDispatcher("flightsSelected.jsp");
        req.setAttribute("flights_departure", allFlights_departure);
        view.forward(req, resp);
    }

}
