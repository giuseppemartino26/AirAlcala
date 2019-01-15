/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Flight;
import Model.Repository.FlightDAO;
import Model.Repository.JDBC.JDBCFlightDAO;
import Model.Repository.JDBC.JDBCRouteDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.io.IOException;
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
        routeDAO=new JDBCRouteDAO();
        flightDAO=new JDBCFlightDAO();
    }
    
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
       String origin = (String) req.getParameter("source");
       String destination = (String) req.getParameter("destination");
       int passengers = Integer.parseInt(req.getParameter("passengers"));
       s.setAttribute("origin", origin);
       s.setAttribute("destination", destination);
       s.setAttribute("passengers", passengers);
       ArrayList<Route> routes= routeDAO.findRoute(origin, destination, passengers);
       System.out.println(routes.toString());
       ArrayList<Flight> allFlights =new ArrayList();
       for (int i = 0; i < routes.size(); i++) {
            ArrayList<Flight> flights = flightDAO.findFlights(routes.get(i));
            System.out.println(flights.toString());
            allFlights.addAll(flights);
        }
       RequestDispatcher view = req.getRequestDispatcher("flightsSelected.jsp");
       req.setAttribute("flights", allFlights);
       view.forward(req, resp);
    }
    
}
