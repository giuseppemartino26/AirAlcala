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
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marti
 */
public class loginController extends HttpServlet {
    private User user;
    private UserDAO userDAO;
    
    @Override
    public void init(ServletConfig cfg) throws ServletException{
       userDAO = new JDBCUserDAO();
    }

    @Override
    public void service(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        int loginUserId = 0;
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        
        loginUserId = userDAO.login(email, pass);
        if(loginUserId > 0)
        {
            RequestDispatcher rs = req.getRequestDispatcher("welcome");
            rs.forward(req, res);
        }
        else
        {
           out.println("Username or Password incorrect");
           RequestDispatcher rs = req.getRequestDispatcher("index.html");
           rs.include(req, res);
        }
    }  
}
