/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Flight;
import Model.Repository.FlightDAO;
import Model.Repository.JDBC.JDBCFlightDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class selectflightController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        double iva = 0.21;
        String forward = "";
        if(req.getParameter("flightDeparture")==null){
            resp.sendRedirect("indexNew.jsp");
        }else{
        int flightIdDeparture = Integer.parseInt(req.getParameter("flightDeparture"));
        Flight flight;
        FlightDAO flightDAO = new JDBCFlightDAO();
        flight = flightDAO.find(flightIdDeparture);
        double compra = 0;
        double price = flight.getRoute().getTicketPrice();
        System.out.println(price);
        double tax=flight.getRoute().getDestination().getTax();
        compra = (price+(price*(tax/100))+(price*iva))* ((int) s.getAttribute("passengers"));
        s.setAttribute("flightIdDeparture", flightIdDeparture);
        s.setAttribute("price_1", compra);
        s.setAttribute("departuretimeIda", flight.getDeparturetime());
        s.setAttribute("arrivaltimeIda", flight.getArrivaltime());
        String flightArrival = req.getParameter("flightArrival");
        s.setAttribute("price_2", null);
        s.setAttribute("flightIdArrival", null);
        if (req.getParameter("flightArrival")!=null) {
            System.out.println("text");
            int flightIdArrival = Integer.parseInt(flightArrival);
            flight = flightDAO.find(flightIdArrival);
            price = flight.getRoute().getTicketPrice();
            tax=flight.getRoute().getDestination().getTax();
            double compra_2 =(price+(price*(tax/100))+(price*iva))* ((int) s.getAttribute("passengers"));
            s.setAttribute("flightIdArrival", flightIdArrival);
            s.setAttribute("departuretimeVuelta", flight.getDeparturetime());
            s.setAttribute("arrivaltimeVuelta", flight.getArrivaltime());
            s.setAttribute("price_2", compra_2);
            s.setAttribute("price", compra+compra_2);
        }else{
            s.setAttribute("price", compra);
        }
        
        s.setAttribute("proceso", "ventaFlight");
        forward = "index.jsp";
        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
}
