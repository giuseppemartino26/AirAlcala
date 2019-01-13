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
public class creditCardController extends HttpServlet {

    private CreditCard creditCard;
    private User user;
    private CreditCardDAO creditCardDAO;
    private UserDAO userDAO;

    public creditCardController() {
        super();
        creditCardDAO = new JDBCCreditCardDAO();
        userDAO = new JDBCUserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String operation = request.getParameter("operation");
        boolean success = false;
        int creditCardId, userId;
        System.err.print(operation);

        if (operation.equalsIgnoreCase("delete")) {
            creditCardId = Integer.parseInt(request.getParameter("creditCardId"));
            success = creditCardDAO.delete(creditCardId);
            //Only permit 1 creditCard for user so when this one is delete we need to create other
            forward = "createCreditCard.jsp";
            request.setAttribute("creditCard", creditCard);
        } else if (operation.equalsIgnoreCase("add")) {
            forward = "createCreditCard.jsp";
            request.setAttribute("creditCard", creditCard);
        } else if (operation.equalsIgnoreCase("edit")) {
            creditCardId = Integer.parseInt(request.getParameter("creditCardId"));
            forward = "editCreditCard.jsp";
            CreditCard cc = creditCardDAO.find(creditCardId);
            request.setAttribute("creditCard", cc);
        } else if (operation.equalsIgnoreCase("view")) {
            userId = Integer.parseInt(request.getParameter("userId"));
            forward = "viewCreditCard.jsp";
            request.setAttribute("creditCard", creditCardDAO.findByUserId(userId));
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        DateFormat format = new SimpleDateFormat("yyyy-mm");
        java.sql.Date expiration;
        Boolean success = false;
        //userId from session that we save in loginUser.jsp session.setAtribute("userId",id)
        int userId = (int) s.getAttribute("userId");
        user = userDAO.find(userId);
        int number = Integer.parseInt(req.getParameter("number"));
        expiration = java.sql.Date.valueOf(req.getParameter("year") + "-" + req.getParameter("month"));
        int cvc = Integer.parseInt(req.getParameter("cvc"));
        
        creditCard = new CreditCard();
        creditCard.setUser(user);
        creditCard.setNumber(number);
        creditCard.setExpiration(expiration);
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
                resp.sendRedirect(resp.encodeRedirectURL("viewCreditCard.jsp"));
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
                resp.sendRedirect(resp.encodeRedirectURL("viewCreditCard.jsp"));
            }
        }
    }

}
