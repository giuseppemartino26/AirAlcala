<%-- 
    Document   : newjsp
    Created on : 14-gen-2019, 0.53.08
    Author     : Giuseppe
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>

<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>AirAlcala</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        <script>
            $(document).ready(function () {
                $("#round_trip").on("click", function () {
                    $("#div_departure_2").toggle();        /*only show if you select the checkbox*/
                });
            });
            function check_data() {
                var dNow = new Date();
                var date = new Date($("#departure_date").val());
                if (dNow > date) {  /*cant pick today */
                    alert("Fecha de salida invalida");
                    return false;
                }
                if ($("#round_trip").prop("checked")) {   /*validate only with round_trip checked */
                    var date2 = new Date($("#departure_date_2").val());
                    if (date > date2) {
                        alert("Fecha de salida del vuelo de vuelta invalida");
                        return false;
                    }
                    ;
                }
                return true;
            }
            ;


        </script>
    </head>
    <body>

        <%
            //allow access only if session exists
            if (session.getAttribute("sessionUserId") == null) {
                response.sendRedirect("loginController");
            }
        %>


        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
                </div>
            </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">AirAlcal</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <%if (session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) { %>
                        <li class="active"><a href="userController?operation=list">Usarios</a></li>
                        <li class="active"><a href="administratorController?operation=list">Administradores</a></li>
                        <li class="active"><a href="airplaneController?operation=list">Aviones</a></li>
                        <li class="active"><a href="airportController?operation=list">Aeropuertos</a></li>
                        <li class="active"><a href="flightController?operation=list">Vuelos</a></li>        
                        <li class="active"><a href="routeController?operation=list">Rutas</a></li>        
                        <li class="active"><a href="saleController?operation=overview">Estadsticas</a></li>    <!-- an no existe, hay que crearlo y calcular las estadsticas en el Controlador (GET) -->
                            <%}
                                if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li class="active"><a href="flightController?operation=search">Buscar Vuelos</a></li>
                        <li class="active"><a href="salesController?operation=list&userId=<%=session.getAttribute("sessionUserId")%>">Mirar Compras</a></li>
                        <li class="active"><a href="creditcardController?operation=list">Editar Medios de Pago</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) { %>
                        <li class="active"><a href="index.jsp">Inicio</a></li>
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
                <h2>Hola <%=session.getAttribute("sessionUserPname")%>! Bscate un Vuelo...</h2>
                <br>
                <form method="POST" action="loginController" class="form-container">
                    <div class="row">
                        <div class="col-lg-6">
                            <label for="departure"><b>Origen</b></label>
                            <input placeholder="Aeropuerto de salida" name="departure" id="departure" type="text" required pattern="[A-zÀ-ž\s]{1,}">
                        </div>
                        <div class="col-lg-6">
                            <label for="destination"><b>Destino</b></label>
                            <input placeholder="Aeropuerto de destino" name="destination" id="destination" type="text" required pattern="[A-zÀ-ž\s]{1,}">
                        </div>
                    </div> 
                    <div class="row">
                        <div class="col-lg-6">
                            <label for="date"><b>Fecha</b></label>
                            <input name="date" id="date" type="date">
                        </div>

                        <div class="col-lg-6">
                            <button type="submit" class="btn">Busca</button>
                        </div>
                    </div>
                </form>
            </div>   
        </div>

        <footer>
            <div class='container'>
                <div class="row">
                    <div class="col-xs-6">
                        <p>
                            AirAlcala - Alcal&aacute; de Henares, Madrid, Espaa
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