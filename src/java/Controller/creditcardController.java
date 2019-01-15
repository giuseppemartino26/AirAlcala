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
        int userId;
        int creditCard_Id;
        
        if(operation.equalsIgnoreCase("delete")){
            userId=Integer.parseInt(request.getParameter("userId"));
            creditCard_Id = creditCardDAO.findByUserId(userId).getId();
            sucess=creditCardDAO.delete(creditCard_Id);
            forward = "createCreditCard.jsp";
        }else if(operation.equalsIgnoreCase("add")){
            forward = "createCreditCard.jsp";
        }else if(operation.equals("edit")){
            userId=Integer.parseInt(request.getParameter("userId"));
            creditCard_Id = creditCardDAO.findByUserId(userId).getId();
            System.out.println(creditCard_Id);
            forward="editCreditCard.jsp";
            CreditCard cc = creditCardDAO.find(creditCard_Id);
            request.setAttribute("creditCard", cc);
        }else if(operation.equalsIgnoreCase("view")){
            userId=Integer.parseInt(request.getParameter("userId"));
            creditCard_Id = creditCardDAO.findByUserId(userId).getId();
            forward="viewCreditCard.jsp";
            CreditCard cc = creditCardDAO.find(creditCard_Id);
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
        long number = Long.parseLong(req.getParameter("number"));
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
            s.setAttribute("creditCardId", creditCard.getId());
            if (success) {
                RequestDispatcher view = req.getRequestDispatcher("viewCreditCard.jsp");
                req.setAttribute("credtiCard", creditCard);
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
