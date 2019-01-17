/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CreditCard;
import Model.Repository.CreditCardDAO;
import Model.Repository.JDBC.JDBCCreditCardDAO;
import Model.Repository.JDBC.JDBCUserDAO;
import Model.Repository.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pablo
 */
public class creditcardController extends HttpServlet {

    private CreditCard creditCard;
    private User user;
    private CreditCardDAO creditCardDAO;
    private UserDAO userDAO;

    public creditcardController() {
        super();
        creditCardDAO = new JDBCCreditCardDAO();
        userDAO = new JDBCUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String forward="";
        String operation = request.getParameter("operation");
        boolean sucess=false;
        int id;
        int userId;
        int ccId;
        
        if(operation.equalsIgnoreCase("delete")){
            ccId=Integer.parseInt(request.getParameter("id"));
            sucess=creditCardDAO.delete(ccId);
            forward = "createCreditCard.jsp";
        }else if(operation.equalsIgnoreCase("add")){
            userId = (Integer) session.getAttribute("sessionUserId");
            user = userDAO.find(userId);
            forward = "createCreditCard.jsp";
            request.setAttribute("user", user);
            
        }else if(operation.equals("edit")){
            id= Integer.parseInt(request.getParameter("id"));
            ccId = creditCardDAO.find(id).getId();
            
            forward="editCreditCard.jsp";
            CreditCard cc = creditCardDAO.find(ccId);
            request.setAttribute("creditCard", cc);
        } else if (operation.equalsIgnoreCase("list")) {
            userId = (Integer) session.getAttribute("sessionUserId");

            ArrayList<CreditCard> ccList = new ArrayList<CreditCard>();
            ccList = creditCardDAO.findAllByUserId(userId);
         
            forward = "listCreditCards.jsp";
            request.setAttribute("creditcards", ccList);    
        }else if(operation.equalsIgnoreCase("view")){
            id = Integer.parseInt(request.getParameter("id"));
            ccId = creditCardDAO.find(id).getId();
            forward="viewCreditCard.jsp";
            CreditCard cc = creditCardDAO.find(ccId);
            request.setAttribute("creditCard", cc);
        }else{
            forward="paginauser.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        java.sql.Date expiration;
        Boolean success = false;
        //userId from session that we save in loginUser.jsp session.setAtribute("userId",id)
        int userId = (int) s.getAttribute("sessionUserId");
        user = userDAO.find(userId);
        
        String number = req.getParameter("number");
        System.out.println(req.getParameter("year"));
        int year=Integer.parseInt(req.getParameter("year"));
        int month=Integer.parseInt(req.getParameter("month")) ;
        //expiration = java.sql.Date.valueOf(req.getParameter("year") + "-" + req.getParameter("month"));
        int cvc = Integer.parseInt(req.getParameter("cvc"));
        
        creditCard = new CreditCard();
        creditCard.setUser(user);
        creditCard.setNumber(number);
        creditCard.setYear(year);
        creditCard.setMonth(month);
        creditCard.setSecurityCode(cvc);
        
        //This is the "add cc" case
        if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            success = creditCardDAO.insert(creditCard);
            req.setAttribute("success", success);
            if (success) {
                RequestDispatcher view = req.getRequestDispatcher("viewCreditCard.jsp");
                req.setAttribute("creditCard", creditCard);
                view.forward(req, resp);
            }
        } //This is the "edit cc" case
        else {
            creditCard.setId(Integer.parseInt(req.getParameter("id")));
            success = creditCardDAO.update(creditCard);
            req.setAttribute("success", success);
            s.setAttribute("creditCardId", creditCard.getId());
            if (success) {
                RequestDispatcher view = req.getRequestDispatcher("viewCreditCard.jsp");
                req.setAttribute("credtiCard", creditCard);
                view.forward(req, resp);
            }
        }
    }

}
