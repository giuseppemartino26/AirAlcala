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
 * @author marti
 */
public class userController extends HttpServlet {
    private User user;
    private UserDAO userDAO;
    
    public userController() {
        super();
        userDAO = new JDBCUserDAO();
    }
    
    // Will be run at GET Events (e.g. hitting a link)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);  
        String forward="";
        String operation = request.getParameter("operation");
        boolean success = false;
        int userId;
          
            if (operation.equalsIgnoreCase("delete")){
                userId = Integer.parseInt(request.getParameter("userId"));
                success = userDAO.delete(userId);
                forward = "listUsers.jsp";
                request.setAttribute("users", userDAO.findAll());
            } else if (operation.equalsIgnoreCase("add")){
                forward = "createUser.jsp";
                request.setAttribute("user", user);
            } else if (operation.equalsIgnoreCase("edit")){
                userId = Integer.parseInt(request.getParameter("userId"));
                forward = "editUser.jsp";
                User user = userDAO.find(userId);
                request.setAttribute("user", user);
            } else if (operation.equalsIgnoreCase("list")){
                forward = "listUsers.jsp";
                request.setAttribute("users", userDAO.findAll());
            } else if (operation.equalsIgnoreCase("view")){
                userId = Integer.parseInt(request.getParameter("userId"));
                forward = "viewUser.jsp";
                request.setAttribute("user", userDAO.find(userId));
            } else {
                forward = "listUsers.jsp";
            }

            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
    }
    
    // Will be run at POST Events (e.g. sending a form)
    @Override
    public void doPost(HttpServletRequest req,
    HttpServletResponse res) throws ServletException, IOException{
        HttpSession s = req.getSession(true);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
        
        user = new User();
        user.setPname(req.getParameter("pname"));
        user.setSname1(req.getParameter("sname1"));
        user.setSname2(req.getParameter("sname2"));
        user.setEmail(req.getParameter("email"));
        user.setPass(securePass);
        user.setBday(userBday);
        user.setAddress(req.getParameter("addr"));
        user.setPcode(req.getParameter("pcode"));
        user.setCity(req.getParameter("city"));
        user.setCountry(req.getParameter("country"));
        
        //This is the "add user" case
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
            success = userDAO.insert(user);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewUser.jsp");
                req.setAttribute("user", user);
                view.forward(req, res);
                res.sendRedirect(res.encodeRedirectURL("viewUser.jsp"));
            }
        }
        // This is the "edit user" case
        else{
            user.setId(Integer.parseInt(req.getParameter("id")));
            success = userDAO.update(user);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewUser.jsp");
                req.setAttribute("user", user);
                view.forward(req, res);
                res.sendRedirect(res.encodeRedirectURL("viewUser.jsp"));
            }
        }
    }
}
