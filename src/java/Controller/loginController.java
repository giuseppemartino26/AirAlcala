/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Helpers.SecurePasswordHelper;
import Model.CreditCard;
import Model.Flight;
import Model.Repository.CreditCardDAO;
import Model.Repository.FlightDAO;
import Model.Repository.JDBC.JDBCCreditCardDAO;
import Model.Repository.JDBC.JDBCFlightDAO;
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
        double iva = 0.21;
        int flightId = 0;
        System.out.println(request.getParameter("flightId"));
        if (request.getParameter("flightId")!=null) {
            flightId = Integer.parseInt(request.getParameter("flightId"));
            Flight flight = new Flight();
            FlightDAO flightDAO = new JDBCFlightDAO();
            flight = flightDAO.find(flightId);
            double compra = 0;
            System.out.println(flight.getId());
            System.out.println(flight.getLocator());
            double price = flight.getRoute().getTicketPrice();
            double tax = flight.getRoute().getOrigin().getTax();
            compra = ((price + tax) * iva)*((int) s.getAttribute("passengers"));
            s.setAttribute("flightId", flightId);
            s.setAttribute("price", compra);
            s.setAttribute("proceso", "ventaFlight");

        } else {
            s.setAttribute("proceso", "myAcount");
        }

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        String email = req.getParameter("email");
        String clearPass = req.getParameter("pass");
        SecurePasswordHelper sec = new SecurePasswordHelper();
        Boolean success = false;
        System.out.println("Test");

        User user = new User();
        UserDAO userDAO = new JDBCUserDAO();
        user = userDAO.findbyEmail(email);
        CreditCard creditCard = new CreditCard();
        CreditCardDAO creditCardDAO = new JDBCCreditCardDAO();
        creditCard = creditCardDAO.findByUserId(user.getId());

        if (user != null) {
            // Check typed in password with stored hash in Database
            try {
                success = sec.validatePassword(clearPass, user.getPass());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                HttpSession s = req.getSession(true);
                s.setAttribute("sessionUserId", user.getId());
                s.setAttribute("sessionUserPname", user.getPname());
                if (s.getAttribute("proceso").equals("ventaFlight")) {
                    RequestDispatcher view = req.getRequestDispatcher("Pay_data.jsp");
                    req.setAttribute("user", user);
                    req.setAttribute("creditCard", creditCard);
                    view.forward(req, res);
                } else {
                    res.sendRedirect("flightController?operation=search");
                }
            } else {
                res.sendRedirect("loginController");
            }
        } else {
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            PrintWriter out = res.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            view.forward(req, res);
        }
    }
}
