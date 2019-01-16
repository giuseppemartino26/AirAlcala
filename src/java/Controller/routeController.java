/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Airplane;
import Model.Airport;
import Model.Repository.AirplaneDAO;
import Model.Repository.AirportDAO;
import Model.Repository.JDBC.JDBCAirplaneDAO;
import Model.Repository.JDBC.JDBCAirportDAO;
import Model.Repository.JDBC.JDBCRouteDAO;
import Model.Repository.RouteDAO;
import Model.Route;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class routeController extends HttpServlet {

    private Route route;
    private RouteDAO routeDAO;
    private AirportDAO airportDAO;
    private AirplaneDAO airplaneDAO;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        routeDAO = new JDBCRouteDAO();
        airportDAO = new JDBCAirportDAO();
        airplaneDAO = new JDBCAirplaneDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String forward = "";
        String operation = request.getParameter("operation");
        ArrayList<Airport> airports=new ArrayList();
        ArrayList<Airplane> airplanes =new ArrayList();
        airports=airportDAO.findAll();
        airplanes=airplaneDAO.findAll();
        boolean success = false;
        int routeId;
        
        ArrayList<Airport> airportList = new ArrayList<Airport>();
        airportList = airportDAO.findAll();
        
        ArrayList<Airplane> airplaneList = new ArrayList<Airplane>();
        airplaneList = airplaneDAO.findAll();        

        if (operation.equalsIgnoreCase("delete")) {
            routeId = Integer.parseInt(request.getParameter("routeId"));
            success = routeDAO.delete(routeId);
            forward = "listRoutes.jsp";
            request.setAttribute("routes", routeDAO.findAll());
        } else if (operation.equalsIgnoreCase("add")) {
            forward = "createRoute.jsp";
            request.setAttribute("airplanes", airplaneList);
            request.setAttribute("airports", airportList);
        } else if (operation.equalsIgnoreCase("edit")) {
            routeId = Integer.parseInt(request.getParameter("routeId"));
            forward = "editRoute.jsp";
            Route route = routeDAO.find(routeId);
            request.setAttribute("route", route);
            request.setAttribute("airports", airports);
            request.setAttribute("airplanes", airplanes);
        } else if (operation.equalsIgnoreCase("list")) {
            forward = "listRoutes.jsp";
            request.setAttribute("routes", routeDAO.findAll());
            System.out.println(routeDAO.findAll().toString());
        } else if (operation.equalsIgnoreCase("view")) {
            routeId = Integer.parseInt(request.getParameter("routeId"));
            forward = "viewRoute.jsp";
            request.setAttribute("route", routeDAO.find(routeId));
        } else {
            forward = "listRoutes.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
        /*ArrayList<Route> routeList;
     
        routeList = routeDAO.findAll();
        
        request.setAttribute("routeList", routeList);
       
        RequestDispatcher dispatcher = request.getRequestDispatcher(
          "/Web Pages/manageRoutes.jsp");
        dispatcher.forward(request, response);*/
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        
        Boolean success=false;
        route=new Route();
        Airport origin = airportDAO.find(Integer.parseInt(req.getParameter("originID")));
        Airport destination = airportDAO.find(Integer.parseInt(req.getParameter("destinationID")));
        Airplane plane= airplaneDAO.find(Integer.parseInt(req.getParameter("airplaneID")));
        route.setOrigin(origin);
        route.setDestination(destination);
        route.setPlane(plane);
        route.setTicketPrice(Double.parseDouble(req.getParameter("ticketprice")));
        if(req.getParameter("id")==null || req.getParameter("id").isEmpty()){
            success= routeDAO.insert(route);
            if(success){
                RequestDispatcher view= req.getRequestDispatcher("viewRoute.jsp");
                req.setAttribute("route", route);
                view.forward(req, res);
            }else{
                RequestDispatcher view= req.getRequestDispatcher("listRoutes.jsp");
                req.setAttribute("routes", routeDAO.findAll());
                view.forward(req, res);
            }
        }else{
            route.setId(Integer.parseInt(req.getParameter("id")));
            success=routeDAO.update(route);
            if(success){
                RequestDispatcher view= req.getRequestDispatcher("viewRoute.jsp");
                req.setAttribute("route", route);
                view.forward(req, res);
            }else{
                RequestDispatcher view= req.getRequestDispatcher("listRoutes.jsp");
                req.setAttribute("routes", routeDAO.findAll());
                view.forward(req, res);
            }
        }
    }
}


