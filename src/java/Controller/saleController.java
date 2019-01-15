/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CreditCard;
import Model.Flight;
import Model.Repository.CreditCardDAO;
import Model.Repository.FlightDAO;
import Model.Repository.JDBC.JDBCCreditCardDAO;
import Model.Repository.JDBC.JDBCFlightDAO;
import Model.Repository.JDBC.JDBCSaleDAO;
import Model.Repository.JDBC.JDBCUserDAO;
import Model.Repository.SaleDAO;
import Model.Repository.UserDAO;
import Model.Sale;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pablo
 */
public class saleController extends HttpServlet {

    private Sale sale;
    private Flight flight;
    private User user;
    private CreditCard creditCard;
    private SaleDAO saleDAO;
    private FlightDAO flightDAO;
    private UserDAO userDAO;
    private CreditCardDAO creditCardDAO;

    public void init(ServletConfig cfg) throws ServletException {
        saleDAO = new JDBCSaleDAO();
        userDAO = new JDBCUserDAO();
        flightDAO = new JDBCFlightDAO();
        creditCardDAO = new JDBCCreditCardDAO();
    }

    /*We only need a list of sales because the sales dont edit or delete only  
        we just need to see them*/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Sale> saleList;

        saleList = saleDAO.findAll();

        req.setAttribute("saleList", saleList);

        RequestDispatcher view = req.getRequestDispatcher("Web Pages/listSales.jsp");
        view.forward(req, resp);
    }

    /*only use the doPost with pay_data.jsp for create a sale and we are forced to have de information of
    userId, flight id, Place, NoLuggage, CreditCardId and the price of the buy with sessions*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        //get userId and flightId from sessions that we save in login (userId) and selectFlight(flightId) ...
        // with session.setAtribute on the jsp pages
        int userId = (int) s.getAttribute("sessionUserId");
        int flightId = (int) s.getAttribute("flightId");
        String place = (String) s.getAttribute("origin");
        int passengers = (int) s.getAttribute("passengers");
        int creditCardId = 1; //(int)s.getAttribute("creditCardId");
        double price = (double) s.getAttribute("price");
        boolean success = false;

        user = userDAO.find(userId);
        flight = flightDAO.find(flightId);
        creditCard = creditCardDAO.find(creditCardId);

        sale = new Sale();
        sale.setFlight(flight);
        sale.setUser(user);
        sale.setPlace(place);
        sale.setPassengers(passengers);
        sale.setCreditCard(creditCard);
        sale.setPrice(price);

        s.setAttribute("Departure_date", flight.getDeparture());
        success = saleDAO.insert(sale);
        s.setAttribute("saleID", sale.getId());
        if (success) {
            resp.sendRedirect("Summary.jsp");
        } else {
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>En el proceso de compra se ha producido un error vuelva a intentarlo</font>");
            view.forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

}
