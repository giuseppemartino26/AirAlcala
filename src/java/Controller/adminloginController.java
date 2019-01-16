/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.Repository.JDBC.JDBCAdministratorDAO;
import Model.Repository.AdministratorDAO;
import Model.Administrator;
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
public class adminloginController extends HttpServlet {
    private Administrator admin;
    private AdministratorDAO adminDAO;
    
    public adminloginController() {
        super();
        adminDAO = new JDBCAdministratorDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("adminLogin.jsp");
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

        
        Administrator admin = new Administrator();
        AdministratorDAO adminDAO = new JDBCAdministratorDAO();
        admin = adminDAO.findbyEmail(email);
        System.out.println(admin.toString());
        
        if(admin != null) {
            // Check typed in password with stored hash in Database
            try {
                success = sec.validatePassword(clearPass, admin.getPass());
            }catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(success){
                HttpSession s = req.getSession(true);
                s.setAttribute("sessionAdminId", admin.getId());
                s.setAttribute("sessionAdminPname", admin.getPname());                
                        
                res.sendRedirect("userController?operation=list");
            } else {
                res.sendRedirect("adminloginController");
            }
        }else {
                RequestDispatcher view = req.getRequestDispatcher("index.html");
                PrintWriter out= res.getWriter();
                out.println("<font color=red>Either user name or password is wrong.</font>");                
                view.forward(req, res);
        }
    }
}
