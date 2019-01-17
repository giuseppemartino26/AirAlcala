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
    private Flight flight, flight_arrival;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String forward = "";
        String operation = request.getParameter("operation");
        boolean success = false;
        int userId;

        if (operation.equalsIgnoreCase("list")) {
            userId = Integer.parseInt(request.getParameter("userId"));
            forward = "listSales.jsp";
            request.setAttribute("sales", saleDAO.findByUserId(userId));
        } else if (operation.equalsIgnoreCase("view")) {
            String saleId = request.getParameter("saleId");
            forward = "viewSale.jsp";
            request.setAttribute("sale", saleDAO.find(saleId));
        } else if (operation.equalsIgnoreCase("overview")){
            request.setAttribute("flights", flightDAO.findAll());
            forward = "stadistics.jsp";
        }else {
            forward = "listUsers.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, resp);
        /* ArrayList<Sale> saleList;

        saleList = saleDAO.findAll();

        req.setAttribute("saleList", saleList);

        RequestDispatcher view = req.getRequestDispatcher("Web Pages/listSales.jsp");
        view.forward(req, resp);*/
    }

    /*only use the doPost with pay_data.jsp for create a sale and we are forced to have de information of
    userId, flight id, Place, NoLuggage, CreditCardId and the price of the buy with sessions*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        //get userId and flightId from sessions that we save in login (userId) and selectFlight(flightId) ...
        // with session.setAtribute on the jsp pages
        int userId = (int) s.getAttribute("sessionUserId");
        int flightId = (int) s.getAttribute("flightIdDeparture");
        int flightIdArrival = 0;
        String place = (String) s.getAttribute("origin");
        String flightArrival = String.valueOf(s.getAttribute("flightIdArrival"));
        double price_2 = 0;
        //System.out.println(s.getAttribute("flightIdArrival"));
        if (s.getAttribute("flightIdArrival") !=null) {
            flightIdArrival = Integer.parseInt(flightArrival);
            String price2=String.valueOf(s.getAttribute("price_2"));
            price_2 = Double.parseDouble(price2) ;
            s.setAttribute("price_2",price_2);
        }
        int passengers = (int) s.getAttribute("passengers");
        int creditCardId = Integer.parseInt(req.getParameter("creditCardId")) ;
        String price1=String.valueOf(s.getAttribute("price_1"));
        double price_1 = Double.parseDouble(price1);
        boolean success = false;
        int sales = saleDAO.numberSales(userId) + 1;
        System.out.println("sales: "+sales);
        if (sales % 3 == 0) {
            price_1 = price_1 / 2;
            s.setAttribute("price_1", "Precio rebajado (50%) por haber contratado 2 viajes anteriormente: " + price_1);
        }
        user = userDAO.find(userId);
        flight = flightDAO.find(flightId);
        // Hay que elegir una durante el proceso de venta!!! Luego elegir objeto con find
        creditCard = creditCardDAO.find(creditCardId);

        sale = new Sale();
        String id = saleDAO.generarID();
        while (!saleDAO.comprobarId(id)) {
            id = saleDAO.generarID();
        }
        sale.setId(id);
        sale.setFlight(flight);
        sale.setUser(user);
        sale.setPlace(place);
        sale.setPassengers(passengers);
        sale.setCreditCard(creditCard);
        sale.setPrice(price_1);
        success = saleDAO.insert(sale);
        s.setAttribute("saleID", sale.getId());
        System.out.println("text: "+flightIdArrival);
        //Reset sessions
        s.setAttribute("saleID_2", null);
        if (flightIdArrival > 0) {
            //System.out.println("text2");
            sales ++;
            if (sales % 3 == 0) {
                price_2 = price_2 / 2;
                s.setAttribute("price_2", "Precio rebajado (50%) por haber contratado 2 viajes anteriormente: " + price_2);
            }
            flight_arrival = flightDAO.find(flightIdArrival);
            System.out.println(flight_arrival.toString());
            
            id = saleDAO.generarID();
            while (!saleDAO.comprobarId(id)) {
                id = saleDAO.generarID();
            }
            
            //place = (String) s.getAttribute("destination");
            Sale sale2=new Sale();
            sale2.setId(id);
            sale2.setFlight(flight_arrival);
            sale2.setUser(user);
            sale2.setPlace(sale2.getFlight().getRoute().getOrigin().getName());
            sale2.setPassengers(passengers);
            sale2.setCreditCard(creditCard);
            sale2.setPrice(price_2);
            System.out.println(flight.getRoute().getDestination().getName());
            s.setAttribute("Departure_2", flight_arrival.getDeparture());
            success = saleDAO.insert(sale2);
            s.setAttribute("saleID_2", sale2.getId());
        }

        s.setAttribute("Departure_date", flight.getDeparture());
        s.setAttribute("price", price_1+price_2);
        resp.sendRedirect("Summary.jsp");
        /*if (success) {
            resp.sendRedirect("Summary.jsp");
        } else {
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>En el proceso de compra se ha producido un error vuelva a intentarlo</font>");
            view.forward(req, resp);
        }*/
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

}
