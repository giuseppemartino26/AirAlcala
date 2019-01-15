/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.Airport;
import Model.Repository.JDBC.JDBCAirportDAO;
import Model.Repository.AirportDAO;
import java.io.IOException;
import static java.lang.System.out;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author David
 */
public class airportController extends HttpServlet {
    private Airport airport;
    private AirportDAO airportDAO;
    
    public airportController() {
        super();
        airportDAO = new JDBCAirportDAO();
    }
    
    // Will be run at GET Events (e.g. hitting a link)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String forward="";
        String operation = request.getParameter("operation");
        boolean success=false;
        int airportId;
        
        if (operation.equalsIgnoreCase("delete")){
            airportId = Integer.parseInt(request.getParameter("airportId"));
            success = airportDAO.delete(airportId);
            forward = "listAirports.jsp";
            request.setAttribute("airports", airportDAO.findAll());
        } else if (operation.equalsIgnoreCase("add")){
            forward = "createAirport.jsp";
            request.setAttribute("airports", airport);
        } else if (operation.equalsIgnoreCase("edit")){
            airportId = Integer.parseInt(request.getParameter("airportId"));
            forward = "editAirport.jsp";
            Airport airport = airportDAO.find(airportId);
            request.setAttribute("airports", airport);
        } else if (operation.equalsIgnoreCase("list")){
            forward = "listAirports.jsp";
            
            request.setAttribute("airports", airportDAO.findAll());
            System.out.println(airportDAO.findAll());
            
        } else if (operation.equalsIgnoreCase("view")){
            airportId = Integer.parseInt(request.getParameter("airportId"));
            forward = "viewAirport.jsp";
            request.setAttribute("airport", airportDAO.find(airportId));
        } else {
            forward = "listAirports.jsp";
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    // Will be run at POST Events (e.g. sending a form)
    @Override
    public void doPost(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        HttpSession s = req.getSession(true);
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        java.sql.Date userBday;
        Boolean success = false;
        String securePass = "";

        userBday = java.sql.Date.valueOf(req.getParameter("bday"));
        
        String clearPass = req.getParameter("pass1");
        SecurePasswordHelper sec = new SecurePasswordHelper();
        
        // Convert Password into secure hash using helper class
        try {
            securePass = sec.generateSecurePasswordHash(clearPass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        airport = new Airport();
        airport.setName(req.getParameter("name"));
        airport.setCountry(req.getParameter("country"));
        airport.setTax(Integer.parseInt(req.getParameter("tax")));
        
        //This is the "add user" case
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
            success = airportDAO.insert(airport);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAirport.jsp");
                req.setAttribute("airport", airport);
                view.forward(req, res);
            }
        }
        // This is the "edit user" case
        else{
            airport.setId(Integer.parseInt(req.getParameter("id")));
            success = airportDAO.update(airport);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAirport.jsp");
                req.setAttribute("airport", airport);
                view.forward(req, res);
            }
        }
    }
}