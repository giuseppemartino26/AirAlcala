<%-- 
    Document   : newjsp
    Created on : 10-en-2019, 9.40.51
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.sql.*;" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Crear Vuelos</title>   
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        
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
            <div class="container">
                <h2>Crear Vuelo</h2>
                <br>
                <div class="btn-group topButton" role="group" aria-label="Basic example">
                    <a href="flightController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
                </div>
                <form method="POST" action="flightController?operation=add" class="form-container" onsubmit="showResponse()">
                    <div class="row">
                         <div class="col-lg-6">
                            <label for="locator"><b>Locator:</b></label>
                            <input name="locator" id="locator" type="text" required>
                        </div>
                        <div class="col-lg-6">
                            <label for="routeID"><b>Ruta: </b></label>
                           <select name="routeID" class="form-control" id="routeID">
                                <c:forEach items="${routes}" var="route">    
                                    <option value="${route.id}">${route.origin.name} a ${route.destination.name}</option>
                                </c:forEach> 
                           </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-4">
                            <label for="departuredate"><b>Fecha de Salida: </b></label>
                            <input name="departuredate" class="form-control" id="departuredate" type="date" required>
                        </div>
                        <div class="col-lg-4">
                            <label for="departuretime"><b>Hora de Salida: </b></label>
                            <input name="departuretime" class="form-control" id="departuretime" type="time" required>
                        </div>
                        <div class="col-lg-4">
                            <label for="arrivaltime"><b>Hora de Llegada: </b></label>
                            <input name="arrivaltime" class="form-control" id="arrivaltime" type = "time" required>
                        </div>
                    </div>

                        <div class="col-lg-6">
                            <button type="submit" class="btn">Env&iacute;o</button>
                        </div>

                        <div class="col-lg-6">
                            <button type="reset" class="reset" >Reiniciar</button>
                        </div>
                    </div>
                </form>
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
        </div>
    </body>
</html>
