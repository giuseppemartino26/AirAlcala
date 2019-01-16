/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.Airplane;
import Model.Repository.JDBC.JDBCAirplaneDAO;
import Model.Repository.AirplaneDAO;
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
public class airplaneController extends HttpServlet {
    private Airplane airplane;
    private AirplaneDAO airplaneDAO;
    
    public airplaneController() {
        super();
        airplaneDAO = new JDBCAirplaneDAO();
    }
    
    // Will be run at GET Events (e.g. hitting a link)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String operation = request.getParameter("operation");
        boolean success = false;
        int airplaneId;
        System.err.print(operation);
        
        if (operation.equalsIgnoreCase("delete")){
            airplaneId = Integer.parseInt(request.getParameter("airplaneId"));
            success = airplaneDAO.delete(airplaneId);
            forward = "listAirplanes.jsp";
            request.setAttribute("airplanes", airplaneDAO.findAll());
        } else if (operation.equalsIgnoreCase("add")){
            forward = "createAirplane.jsp";
            request.setAttribute("airplanes", airplane);
        } else if (operation.equalsIgnoreCase("edit")){
            airplaneId = Integer.parseInt(request.getParameter("airplaneId"));
            forward = "editAirplane.jsp";
            Airplane airplane = airplaneDAO.find(airplaneId);
            request.setAttribute("airplane", airplane);
        } else if (operation.equalsIgnoreCase("list")){
            forward = "listAirplanes.jsp";
            request.setAttribute("airplanes", airplaneDAO.findAll());
            System.out.println(airplaneDAO.findAll().toString());
        } else if (operation.equalsIgnoreCase("view")){
            airplaneId = Integer.parseInt(request.getParameter("airplaneId"));
            forward = "viewAirplane.jsp";
            request.setAttribute("airplane", airplaneDAO.find(airplaneId));
        } else {
            forward = "listAirplanes.jsp";
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    // Will be run at POST Events (e.g. sending a form)
    @Override
    public void doPost(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        HttpSession s = req.getSession(true);

        Boolean success = false;
        String securePass = "";

        airplane = new Airplane();
        airplane.setName(req.getParameter("name"));
        airplane.setPlaces(Integer.parseInt(req.getParameter("places")));
        
        //This is the "add user" case
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
            success = airplaneDAO.insert(airplane);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAirplane.jsp");
                req.setAttribute("airplane", airplane);
                view.forward(req, res);
            }
        }
        // This is the "edit user" case
        else{
            airplane.setId(Integer.parseInt(req.getParameter("id")));
            success = airplaneDAO.update(airplane);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAirplane.jsp");
                req.setAttribute("airplane", airplane);
                view.forward(req, res);
            }
        }
    }
}
