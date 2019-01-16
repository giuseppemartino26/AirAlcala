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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marti
 */

public class flightController extends HttpServlet {
    private Flight flight;
    private FlightDAO flightDAO;
    private RouteDAO routeDAO;
    
    @Override
    public void init(ServletConfig cfg) throws ServletException{
       flightDAO = new JDBCFlightDAO();
       routeDAO = new JDBCRouteDAO();    
    }

    @Override
    protected void doGet(
      HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
  
        String forward="";
        String operation = request.getParameter("operation");
        boolean success = false;
        int flightId;
        
        ArrayList<Route> routeList = new ArrayList<Route>();
        RouteDAO routeDAO = new JDBCRouteDAO();
        routeList = routeDAO.findAll();
        
        
        if (operation.equalsIgnoreCase("delete")){
            flightId = Integer.parseInt(request.getParameter("flightId"));
            success = flightDAO.delete(flightId);
            if(success)
                System.out.println("se borro vuelo");
            forward = "listFlights.jsp";
            request.setAttribute("flights", flightDAO.findAll());
        } else if (operation.equalsIgnoreCase("add")){
            forward = "createFlight.jsp";
            //request.setAttribute("flight", flight);
            System.out.println(routeList.toString());
            request.setAttribute("routes", routeList);
        } else if (operation.equalsIgnoreCase("search")){
            forward = "searchFlights.jsp";
            request.setAttribute("routes", routeList);
        } else if (operation.equalsIgnoreCase("edit")){
            flightId = Integer.parseInt(request.getParameter("flightId"));
            forward = "editFlight.jsp";
            Flight flight = flightDAO.find(flightId);
            request.setAttribute("routes", routeList);
            request.setAttribute("flight", flight);
        } else if (operation.equalsIgnoreCase("list")){
            forward = "listFlights.jsp";
            request.setAttribute("flights", flightDAO.findAll());
        } else if (operation.equalsIgnoreCase("view")){
            flightId = Integer.parseInt(request.getParameter("flightId"));
            forward = "viewFlights.jsp";
            request.setAttribute("flights", flightDAO.find(flightId));
        } else {
            forward = "listFlights.jsp";
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        Boolean success = false;
        
        flight = new Flight();
        Route route = new Route();
        RouteDAO routeDAO = new JDBCRouteDAO();
        int routeId=Integer.parseInt(req.getParameter("route"));
        route=routeDAO.find(routeId);
        System.out.println("ruta- "+route.getId()+"-"+routeId);
        
        java.sql.Date departureDate = java.sql.Date.valueOf(req.getParameter("departure"));
        java.sql.Date arrivalDate = java.sql.Date.valueOf(req.getParameter("arrival"));
        
        flight.setLocator(req.getParameter("locator"));
        flight.setRoute(route);
        flight.setDeparture(departureDate);
        flight.setArrival(arrivalDate);

        //This is the "add flight" case
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
            success = flightDAO.insert(flight);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewFlights.jsp");
                req.setAttribute("flight", flight);
                view.forward(req, res);
            }
        }
        // This is the "edit flight" case
        else{
            flight.setId(Integer.parseInt(req.getParameter("id")));
            success = flightDAO.update(flight);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewFlights.jsp");
                req.setAttribute("flight", flight);
                view.forward(req, res);
            }
        }
    }
}
