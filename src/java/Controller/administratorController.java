/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.Administrator;
import Model.Repository.AdministratorDAO;
import Model.Repository.JDBC.JDBCAdministratorDAO;
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
public class administratorController extends HttpServlet {
    private Administrator admin;
    private AdministratorDAO adminDAO;
    
    public administratorController() {
        super();
        adminDAO = new JDBCAdministratorDAO();
    }
    
    // Will be run at GET Events (e.g. hitting a link)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String operation = request.getParameter("operation");
        boolean success = false;
        int adminId;
        System.err.print(operation);
        
        if (operation.equalsIgnoreCase("delete")){
            adminId = Integer.parseInt(request.getParameter("adminId"));
            success = adminDAO.delete(adminId);
            forward = "listAdmins.jsp";
            request.setAttribute("admins", adminDAO.findAll());
        } else if (operation.equalsIgnoreCase("add")){
            forward = "createAdmin.jsp";
            request.setAttribute("admin", admin);
        } else if (operation.equalsIgnoreCase("edit")){
            adminId = Integer.parseInt(request.getParameter("adminId"));
            forward = "editAdmin.jsp";
            admin = adminDAO.find(adminId);
            request.setAttribute("admin", admin);
        } else if (operation.equalsIgnoreCase("list")){
            forward = "listAdmins.jsp";
            request.setAttribute("admins", adminDAO.findAll());
        } else if (operation.equalsIgnoreCase("view")){
            adminId = Integer.parseInt(request.getParameter("adminId"));
            forward = "viewAdmin.jsp";
            System.out.println(adminDAO.find(adminId).getEmail());
            request.setAttribute("admin", adminDAO.find(adminId));
        } else {
            forward = "listAdmins.jsp";
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
        String clearPass = req.getParameter("pass1");
        SecurePasswordHelper sec = new SecurePasswordHelper();
        
        // Convert Password into secure hash using helper class
        try {
            securePass = sec.generateSecurePasswordHash(clearPass);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        admin = new Administrator();
        admin.setPname(req.getParameter("pname"));
        admin.setSname1(req.getParameter("sname1"));
        admin.setSname2(req.getParameter("sname2"));
        admin.setEmail(req.getParameter("email"));
        admin.setPass(securePass);
        
        //This is the "add user" case
        if(req.getParameter("id") == null || req.getParameter("id").isEmpty()){
            success = adminDAO.insert(admin);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAdmin.jsp");
                req.setAttribute("admin", admin);
                view.forward(req, res);
            }
        }
        // This is the "edit user" case
        else{
            admin.setId(Integer.parseInt(req.getParameter("id")));
            success = adminDAO.update(admin);
            req.setAttribute("success", success);
            if(success){
                RequestDispatcher view = req.getRequestDispatcher("viewAdmin.jsp");
                req.setAttribute("admin", admin);
                view.forward(req, res);
            }
        }
    }
}
