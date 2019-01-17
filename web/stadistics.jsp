<%-- 
    Document   : stadistics
    Created on : 17-ene-2019, 5:23:44
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="Model.Flight"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Repository.JDBC.JDBCFlightDAO"%>
<%@page import="Model.Repository.FlightDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
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
                $('#datatable2').DataTable();
                $('#datatable3').DataTable();
                $("#viajeros").on("click", function(){
                    $("#passenger_flight").show();
                    $("#earnings_flight").hide();
                    $("#earnings_total").hide();
                });
                $("#ganancias").on("click", function(){
                    $("#passenger_flight").hide();
                    $("#earnings_flight").show();
                    $("#earnings_total").hide();
                });
                $("#total").on("click", function(){
                    $("#passenger_flight").hide();
                    $("#earnings_flight").hide();
                    $("#earnings_total").show();
                });
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
                     <div class="btn-group btn-group-justified" >
                         <div class="btn-group">
                             <button id="viajeros" class="btn btn-primary">Viajeros por vuelo</button>
                         </div>
                         <div class="btn-group">
                             <button id="ganancias" class="btn btn-primary">Ganancias por vuelo</button>
                         </div>
                         <div class="btn-group">
                             <button id="total" class="btn btn-primary">Ganancias Totales</button>
                         </div>
            </div>


            <br>
            <br>
            <div id="passenger_flight" class="container" style="display: none">
                <table id="datatable" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Ruta</th>
                            <th>Fecha salida</th>
                            <th>Hora salida</th>
                            <th>Viajeros</th>                
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${flights}" var="flight">
                            <tr>
                                <td><c:out value="${flight.id}" /></td>
                                <td><c:out value="${flight.route.origin.name} a ${flight.route.destination.name}" /></td>
                                <td><c:out value="${flight.departure}" /></td>
                                <td><c:out value="${flight.departuretime}" /></td>
                                <td><c:out value="${flight.route.plane.places-flight.availableSeats}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th>Id</th>
                            <th>Ruta</th>
                            <th>Fecha salida</th>
                            <th>Hora salida</th>
                            <th>Viajeros</th> 

                        </tr> 
                    </tfoot>
                </table>
            </div>
            <br>
            <div id="earnings_flight" class="container" style="display: none">
                <table id="datatable2" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Ganancias</th>
                            <th>Id_vuelo</th>                
                        </tr>
                    </thead>
                    <%
                        try {
                            String Query = "select sum(price), flight_id from sales group by flight_id";
                            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
                            Statement stm = conn.createStatement();
                            ResultSet rs = stm.executeQuery(Query);
                            while (rs.next()) {
                                Double price = rs.getDouble(1);
                                int flightId = rs.getInt(2);
                    %>
                    <tbody>
                        <tr>
                            <td> <%=price%> </td>
                            <td> <%=flightId%> </td>
                        </tr>
                    </tbody>
                    <%
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            out.println("Error" + ex.getMessage());
                        }
                    %>
                    <tfoot>
                        <tr>
                            <th>Ganancias</th>
                            <th>Id_vuelo</th> 

                        </tr> 
                    </tfoot>
                </table>
            </div>
            <div id="earnings_total" class="container" style="display: none">
                <table id="datatable3" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Ganancias Totales</th>               
                        </tr>
                    </thead>
                    <%
                        try {
                            String Query = "select sum(price) from sales ";
                            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
                            Statement stm = conn.createStatement();
                            ResultSet rs = stm.executeQuery(Query);
                            while (rs.next()) {
                                Double price = rs.getDouble(1);
                    %>
                    <tbody>
                        <tr>
                            <td style=""> <%=price%> </td>
                        </tr>
                    </tbody>
                    <%
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            out.println("Error" + ex.getMessage());
                        }
                    %>
                    <tfoot>
                        <tr>
                            <th>Ganancias Totales</th>  
                        </tr> 
                    </tfoot>
                </table>
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


<!--<div class="container">
                <h4>Lista de Administradores</h4>
                <div class="topButton"><a href="administratorController?operation=add" class="btn btn-primary" role="button">Añadir Administrador</a></div>
                <br>
                <table id="datatable" class="display" style="width:100%">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nombre</th>
                            <th>Primer Apelido</th>
                            <th>Segundo Apelido</th>
                            <th>Email</th>
                            <th></th>
                            <th></th>
                            <th></th>                
                        </tr>
                    </thead>
                    <tbody>
<c:forEach items="${admins}" var="admin">
    <tr>
        <td><c:out value="${admin.id}" /></td>
        <td><c:out value="${admin.pname}" /></td>
        <td><c:out value="${admin.sname1}" /></td>
        <td><c:out value="${admin.sname2}" /></td>
        <td><c:out value="${admin.email}" /></td>
        <td><a href="administratorController?operation=view&adminId=<c:out value="${admin.id}"/>">mirar</a></td>
        <td><a href="administratorController?operation=edit&adminId=<c:out value="${admin.id}"/>">actualizar</a></td>
        <td><a href="administratorController?operation=delete&adminId=<c:out value="${admin.id}"/>">borrar</a></td>
    </tr>
</c:forEach>
</tbody>
<tfoot>
<tr>
    <th>Id</th>
    <th>Nombre</th>
    <th>Primer Apelido</th>
    <th>Segundo Apelido</th>
    <th>Email</th>
    <th></th>
    <th></th>
    <th></th>

</tr> 
</tfoot>
</table>
</div>-->

