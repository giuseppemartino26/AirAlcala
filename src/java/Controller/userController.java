/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.User;
import Model.Repository.JDBC.JDBCUserDAO;
import Model.Repository.UserDAO;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 *
 * @author marti
 */
public class userController extends HttpServlet {
    private User user;
    private UserDAO userDAO;
    
    @Override
    public void init(ServletConfig cfg) throws ServletException{
       userDAO = new JDBCUserDAO();
    }

    @Override
    public void service(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        HttpSession s = req.getSession(true);
        String operation=(String)req.getParameter("manageUser");
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date userBday = new Date();
        Boolean success = false;
        try {
            userBday = format.parse(req.getParameter("birthday"));
        } catch (ParseException ex) {
            Logger.getLogger(flightController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String securePass = null;
        String clearPass = req.getParameter("pass");
        SecurePasswordHelper sec = new SecurePasswordHelper();
        
        try {
            securePass = sec.generateSecurePasswordHash(clearPass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(operation=="add"){
            user = new User(0,req.getParameter("prename"),
                    req.getParameter("surname1"),
                    req.getParameter("surname2"),
                    req.getParameter("email"), 
                    securePass, userBday,
                    req.getParameter("address"),
                    req.getParameter("postalcode"),
                    req.getParameter("country"),
                    Integer.parseInt(req.getParameter("flights_booked")));
            
            success = userDAO.insert(user);
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertUserSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/InsertUserError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
        if(operation == "edit"){
            user = new User(Integer.parseInt(req.getParameter("id")),
                    req.getParameter("prename"),
                    req.getParameter("surname1"),
                    req.getParameter("surname2"),
                    req.getParameter("email"), 
                    securePass, userBday,
                    req.getParameter("address"),
                    req.getParameter("postalcode"),
                    req.getParameter("country"),
                    Integer.parseInt(req.getParameter("flights_booked")));
            
            success = userDAO.update(user);
            
            s.setAttribute("pname", user.getPname());
            s.setAttribute("sname1", user.getSname1());
            s.setAttribute("sname2", user.getSname2());
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateUserSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/UpdateUserError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
        if(operation=="delete"){
            success = userDAO.delete(Integer.parseInt(req.getParameter("id")));
            if(success){
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteUserSuccess.jsp")); // o conseguir mensaje Alarma con AJAX/JavaScript
            } else{
                res.sendRedirect(res.encodeRedirectURL("/MVC/deleteUserError.jsp"));  // o conseguir mensaje Alarma con AJAX/JavaScript
            }
        }
    }
    
    @Override
    public void destroy(){
    }
}
