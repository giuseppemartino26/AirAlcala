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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
    
    public void init(ServletConfig cfg) throws ServletException{
       flightDAO = new JDBCFlightDAO();
       routeDAO = new JDBCRouteDAO();    }


    public void service(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        HttpSession s = req.getSession(true);
        String operation=(String)req.getParameter("UpdateFlight");
        Route route = routeDAO.find(Integer.parseInt(req.getParameter("route_id")));
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date flightDate = new Date();
        Boolean success = false;
        
        try {
            flightDate = format.parse(req.getParameter("datetime"));
        } catch (ParseException ex) {
            Logger.getLogger(flightController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(operation=="add"){
            flight = new Flight(0,req.getParameter("locator"),
                    route,flightDate, Integer.parseInt(req.getParameter("interval")),
                    Integer.parseInt(req.getParameter("places_left")));
            success = flightDAO.insert(flight);
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertSuccess.jsp"));
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertError.jsp"));
            }
        }
        if(operation == "edit"){
            flight = new Flight(Integer.parseInt(req.getParameter("id")),
                    req.getParameter("locator"),route,flightDate,
                    Integer.parseInt(req.getParameter("interval")),
                    Integer.parseInt(req.getParameter("places_left")));
            success = flightDAO.update(flight);
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
        if(operation=="delete"){
            success = flightDAO.delete(Integer.parseInt(req.getParameter("id")));
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
    }


    public void destroy(){
    }

}
