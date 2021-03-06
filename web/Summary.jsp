<%-- 
    Document   : Summary
    Created on : 28-dic-2018, 18:57:48
    Author     : pablo
--%>

<%--
    I don't know if we use sessions or we use sql
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                if(<%=session.getAttribute("saleID_2")%> == null){
                    $("#roundTrip").toggle();
                }
            });
        </script>
    </head>
    <body>
        <%
            //allow access only if session exists
            if (session.getAttribute("sessionUserId") == null) {
                response.sendRedirect("loginController");
            };
            /*String precio_1 = String.valueOf(session.getAttribute("price_1"));
            String precio_2 = String.valueOf(session.getAttribute("price_2"));
            Double compra= Double.parseDouble(precio_1) + Double.parseDouble(precio_2);*/
            String departure2 = "No hay vuelo de vuelta";
            if (session.getAttribute("saleID_2") != null) {
                departure2 = String.valueOf(session.getAttribute("Departure_2"));
            };
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
            <div class="col-md-4">
                <h4>Resumen de la compra vuelo de ida</h4>
                <table class="table table-striped">
                    <tr>
                        <td>ID Compra</td>
                        <td><%=session.getAttribute("saleID")%></td>
                    </tr>
                    <tr>
                        <td>Origen</td>
                        <td><%=session.getAttribute("origin")%></td><!---->
                    </tr>
                    <tr>
                        <td>Destino</td>
                        <td><%=session.getAttribute("destination")%></td><!---->
                    </tr>
                    <tr>
                        <td>Fecha Ida</td>
                        <td><%=session.getAttribute("Departure_date")%></td><!---->
                    </tr>
                    <tr>
                        <td>Tiempo de Salida Ida</td>
                        <td><%=session.getAttribute("departuretimeIda")%></td><!---->
                    </tr>
                    <tr>
                        <td>Tiempo de Llegada Ida</td>
                        <td><%=session.getAttribute("arrivaltimeIda")%></td><!---->
                    </tr>                     
                    <tr>
                        <td>N&uacute;mero de pasajeros</td>
                        <td><%=session.getAttribute("passengers")%></td><!---->
                    </tr>
                    <tr>
                        <td>Precio</td>
                        <td><%=session.getAttribute("price_1")%></td><!---->
                    </tr>
                </table>
            </div>
            <div class="col-md-4" id="roundTrip" >
                <h4>Resumen de la compra vuelo de vuelta</h4>
                <table class="table table-striped">
                    <tr>
                        <td>ID Compra</td>
                        <td><%=session.getAttribute("saleID_2")%></td>
                    </tr>
                    <tr>
                        <td>Origen</td>
                        <td><%=session.getAttribute("destination")%></td><!---->
                    </tr>
                    <tr>
                        <td>Destino</td>
                        <td><%=session.getAttribute("origin")%></td><!---->
                    </tr>
                    <tr>
                        <td>Fecha Vuelta</td>
                        <td><%=session.getAttribute("Departure_2")%></td><!---->
                    </tr>
                    <tr>
                        <td>Tiempo de Salida Ida</td>
                        <td><%=session.getAttribute("departuretimeVuelta")%></td><!---->
                    </tr>
                    <tr>
                        <td>Tiempo de Llegada Ida</td>
                        <td><%=session.getAttribute("arrivaltimeVuelta")%></td><!---->
                    </tr> 
                    <tr>
                        <td>N&uacute;mero de pasajeros</td>
                        <td><%=session.getAttribute("passengers")%></td><!---->
                    </tr>
                    <tr>
                        <td>Precio</td>
                        <td><%=session.getAttribute("price_2")%></td><!---->
                    </tr>
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
