/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Repository.JDBC.JDBCUserDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.io.IOException;
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
public class adminlogoutController extends HttpServlet {
    private User user;
    private UserDAO userDAO;
    
    public adminlogoutController() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//invalidate the session if exists
    	HttpSession session = request.getSession(false);
        System.out.println("User="+session.getAttribute("sessionAdminPname"));
    	if(session != null){
    		session.invalidate();
    	}
    	response.sendRedirect("adminloginController");         
    }
}
