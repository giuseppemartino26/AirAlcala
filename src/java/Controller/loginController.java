/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.Repository.JDBC.JDBCUserDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marti
 */
public class loginController extends HttpServlet {
    private User user;
    private UserDAO userDAO;
    
    public loginController() {
        super();
        userDAO = new JDBCUserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        String flightId = request.getParameter("flightId");
        s.setAttribute("flightId", flightId);
        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
         view.forward(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        
        String email = req.getParameter("email");
        String clearPass = req.getParameter("pass");
        SecurePasswordHelper sec = new SecurePasswordHelper();
        Boolean success = false;
        System.out.println("Test");

        
        User user = new User();
        UserDAO userDAO = new JDBCUserDAO();
        user = userDAO.findbyEmail(email);
        
        if(user != null) {
            // Check typed in password with stored hash in Database
            try {
                success = sec.validatePassword(clearPass, user.getPass());
            }catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(success){
                HttpSession s = req.getSession(true);
                s.setAttribute("sessionUserId", user.getId());
                s.setAttribute("sessionUserPname", user.getPname());                
                        
                res.sendRedirect("flightController?operation=search");
            } else {
                res.sendRedirect("loginController");
            }
        }else {
                RequestDispatcher view = req.getRequestDispatcher("index.html");
                PrintWriter out= res.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");                
                view.forward(req, res);
        }
    }
}
