/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Airplane;
import Model.Airport;
import Model.Repository.AirplaneDAO;
import Model.Repository.AirportDAO;
import Model.Repository.JDBC.JDBCAirplaneDAO;
import Model.Repository.JDBC.JDBCAirportDAO;
import Model.Repository.JDBC.JDBCRouteDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author fabri
 */    
@WebServlet(
  name = "StudentServlet", 
  urlPatterns = "/Web Pages/manageFlights")
public class routeController extends HttpServlet {
    private Route route;
    private RouteDAO routeDAO;
    private AirportDAO airportDAO;
    private AirplaneDAO airplaneDAO;
    
    @Override
    public void init(ServletConfig cfg) throws ServletException{
       routeDAO = new JDBCRouteDAO();   
       airportDAO = new JDBCAirportDAO();
       airplaneDAO = new JDBCAirplaneDAO();
    }

    @Override
    protected void doGet(
      HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {
  
        ArrayList<Route> routeList;
     
        routeList = routeDAO.findAll();
        
        request.setAttribute("routeList", routeList);
       
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "/Web Pages/manageRoutes.jsp");
        dispatcher.forward(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        String operation=(String)req.getParameter("UpdateRoute");
        Boolean success;
        
        if(operation.equals("add")){
            Airport origin = airportDAO.find(Integer.parseInt(req.getParameter("originID")));
            Airport destination = airportDAO.find(Integer.parseInt(req.getParameter("destinationID")));
            Airplane plane = airplaneDAO.find(Integer.parseInt(req.getParameter("airplaneID")));
            int ticketPrice = Integer.parseInt(req.getParameter("ticketPrice"));
            int tax = Integer.parseInt(req.getParameter("tax"));
            int luggagePrice = Integer.parseInt(req.getParameter("luggagePrice"));
            route = new Route(0, origin, destination, plane, ticketPrice, tax, luggagePrice);
            success = routeDAO.insert(route);
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
        if(operation.equals("edit")){
            Airport origin = airportDAO.find(Integer.parseInt(req.getParameter("originID")));
            Airport destination = airportDAO.find(Integer.parseInt(req.getParameter("destinationID")));
            Airplane plane = airplaneDAO.find(Integer.parseInt(req.getParameter("airplaneID")));
            int ticketPrice = Integer.parseInt(req.getParameter("ticketPrice"));
            int tax = Integer.parseInt(req.getParameter("tax"));
            int luggagePrice = Integer.parseInt(req.getParameter("luggagePrice"));
            route = new Route(Integer.parseInt(req.getParameter("id")), origin, destination, plane, ticketPrice, tax, luggagePrice);
            success = routeDAO.update(route);
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
        if(operation.equals("delete")){
            success = routeDAO.delete(Integer.parseInt(req.getParameter("id")));
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
    }
}
