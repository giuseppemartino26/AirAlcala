<%-- 
    Document   : ListUsers
    Created on : 09-ene-2019, 13:12:03
    Author     : Martin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Flight"%>
<%@page import="Model.Repository.SaleDAO" %>
<%@page import="Model.Repository.JDBC.JDBCSaleDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>AirAlcala</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables.css">
        <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables_themeroller.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script type="text/javascript" charset="utf-8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" charset="utf-8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/jquery.dataTables.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">

        <script>
            $(document).ready(function () {
                $('#datatable').DataTable();
            });
        </script>

    </head>
    <body>
        <%
            //allow access only if session exists
            if (session.getAttribute("sessionAdminId") == null) {
                response.sendRedirect("adminloginController");
            }
        %>
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>Air Alcala</h1>
                </div>
            </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="indexNew.jsp">AirAlcalá</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <%if (session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) { %>
                        <li class="active"><a href="userController?operation=list">Usarios</a></li>
                        <li class="active"><a href="administratorController?operation=list">Administradores</a></li>
                        <li class="active"><a href="airplaneController?operation=list">Aviones</a></li>
                        <li class="active"><a href="airportController?operation=list">Aeropuertos</a></li>
                        <li class="active"><a href="flightController?operation=list">Vuelos</a></li>        
                        <li class="active"><a href="routeController?operation=list">Rutas</a></li>        
                        <li class="active"><a href="saleController?operation=overview">Estadísticas</a></li>    <!-- aún no existe, hay que crearlo y calcular las estadísticas en el Controlador (GET) -->
                            <%}
                                if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li class="active"><a href="indexNew.jsp">Buscar Vuelos</a></li>
                        <li class="active"><a href="saleController?operation=list&userId=<%=session.getAttribute("sessionUserId")%>">Mirar Compras</a></li>
                        <li class="active"><a href="creditcardController?operation=list">Editar Medios de Pago</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) { %>
                        <li class="active"><a href="indexNew.jsp">Inicio</a></li>
                        <li class="active"><a href="userController?operation=add">Crear Cuenta</a></li>
                            <%}%>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <%if (session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li><a href="administratorController?operation=view&adminId=<%=session.getAttribute("sessionAdminId")%>">
                                <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionAdminPname")%></a></li>
                        <li><a href="adminlogoutController"><span class="glyphicon glyphicon-log-out"></span>Admin Logout</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li><a href="userController?operation=view&userId=<%=session.getAttribute("sessionUserId")%>">
                                <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionUserPname")%></a></li>
                        <li><a href="logoutController"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) {%>
                        <li><a href="loginController"><span class="glyphicon glyphicon-log-out">
                                </span>Login</a></li>
                                <% }%>
                    </ul>
                </div>
            </nav>
            <br>
            <br>
            <div class="container">
                <h4>Lista de Vuelos</h4>
                <div class="topButton"><a href="flightController?operation=add" class="btn btn-primary" role="button">Añadir Vuelo</a></div>
                <br>
                <table id="datatable" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Localizador</th>
                            <th>Ruta</th>
                            <th>Fecha de Salida</th>
                            <th>Tiempo de Salida</th>
                            <th>Tiempo de Llegada</th>
                            <th>Plazas disponibles</th>
                            <th>Plazas ocupadas</th>
                            <th>Ganancia</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<Flight> flights = (ArrayList<Flight>) request.getAttribute("flights");
                            for (Flight flight : flights) {
                        %>
                        <tr>                                
                            <td><%=flight.getLocator()%></td>
                            <td><%=flight.getRoute().getOrigin().getName()%> a <%=flight.getRoute().getDestination().getName()%></td>
                            <td><%=flight.getDeparture()%></td>
                            <td><%=flight.getDeparturetime()%></td>
                            <td><%=flight.getArrivaltime()%></td>
                            <td><%=flight.getAvailableSeats()%></td>
                            <td><%=flight.getRoute().getPlane().getPlaces() - flight.getAvailableSeats()%></td>
                            <!--<td><%=flight.getRoute().getTicketPrice() * (flight.getRoute().getPlane().getPlaces() - flight.getAvailableSeats())%></td>-->
                            <% SaleDAO saleDAO = new JDBCSaleDAO();
                                double total = saleDAO.sumByFlight(flight.getId());%>
                            <td><%=total%></td>
                            <td><a href="flightController?operation=edit&flightId=<%=flight.getId()%>">actualizar</a></td>
                            <td><a href="flightController?operation=delete&flightId=<%=flight.getId()%>">borrar</a></td>
                        </tr>
                        <% }
                        %>

                    </tbody>
                    <tfoot>
                        <tr>
                            <th>Localizador</th>
                            <th>Ruta</th>
                            <th>Fecha de Salida</th>
                            <th>Tiempo de Salida</th>
                            <th>Tiempo de Llegada</th>
                            <th>Plazas disponibles</th>
                            <th>Plazas ocupadas</th>
                            <th>Ganancia</th>
                            <th></th>
                            <th></th>

                        </tr> 
                    </tfoot>
                </table>
                <%
                    double gananciaTotal = 0;
                    SaleDAO saleDAO = new JDBCSaleDAO();
                                
                    for (Flight flight : flights) {
                        gananciaTotal += saleDAO.sumByFlight(flight.getId());
                    }
                %>
                <br> <br> <h3>Ganancia Total: <%=gananciaTotal%></h3>
            </div>
        </div>
        <footer>
            <div class='container'>
                <div class="row">
                    <div class="col-xs-6">
                        <p>
                            AirAlcala - Alcal&aacute; de Henares, Madrid, España
                        </p>
                    </div>
                    <div class="col-xs-6">
                        <ul class="list-inline text-right">
                            <li><a href="https://twitter.com/">Twitter</a></li>
                            <li><a href="https://www.facebook.com/">Facebook</a></li>
                            <li>Contacto 900 900 411 <a href="mailto:ciu@uah.es">ciu@uah.es</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    </body>
</html>
