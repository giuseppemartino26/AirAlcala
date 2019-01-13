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

    public void init(ServletConfig cfg) throws ServletException{
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
        HttpSession s =req.getSession(true);
        //get userId and flightId from sessions that we save in login (userId) and selectFlight(flightId) ...
        // with session.setAtribute on the jsp pages
        int id = 0; // we need a alfanumeric key for sale like NIK23A
        int userId = (int) s.getAttribute("userId");
        int flightId = (int) s.getAttribute("flightId");
        String place = (String)s.getAttribute("place");
        int luggage = (int)s.getAttribute("luggage");
        int creditCardId = (int)s.getAttribute("creditCardId");
        double price = (double)s.getAttribute("price");
        boolean success = false;
        
        user = userDAO.find(userId);
        flight = flightDAO.find(flightId);
        creditCard = creditCardDAO.find(creditCardId);
        
        sale =  new Sale(id,flight,user,place,luggage,creditCard,price);
        s.setAttribute("saleID", sale.getId());
        s.setAttribute("Origin", flight.getRoute().getOrigin().getName());
        s.setAttribute("Destination", flight.getRoute().getDestination().getName());
        s.setAttribute("Departure_date", flight.getDate());
        success = saleDAO.insert(sale);
        if(success){
            s.setAttribute("success", true);
        }else{
            s.setAttribute("success", false);
        }
        resp.sendRedirect("/Web pages/Summaty.jsp");
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }
   
    
      
    
}
