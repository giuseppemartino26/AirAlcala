<%-- 
    Document   : editCreditCard
    Created on : 12-ene-2019, 23:14:51
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>AirAlcala</title>   
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">

        <script>
            /*validate visa*/
            function visa_cardnumber() {
                var cardno = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/; /*visa format*/
                if ($("#number_credit").val().match(cardno)) {
                    return true;
                } else {
                    return false;
                }
            }
            ;
            /*validate mastercard*/
            function mc_cardnumber() {
                var cardno = /^(?:5[1-5][0-9]{14})$/;/*mastercard format*/
                if ($("#number_credit").val().match(cardno)) {
                    return true;
                } else {
                    return false;
                }
            }
            ;
            function check_credit_data() {
                if (!mc_cardnumber() && !visa_cardnumber()) {
                    alert("Numero de tarjeta de credito no valido");
                    return false;
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
                response.sendRedirect("loginController?operation=myacount");
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
                <h2>Editar Tarjeta de Cr&eacute;dito</h2>
                <div class="btn-group topButton" role="group" aria-label="Basic example">
                    <a href="creditcardController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
                </div>
                <form method="POST" action="creditcardController" class="form-container" onsubmit="return check_credit_data()">
                    <input type="hidden" name="id" value="${creditCard.id}" />
                    <div class="row">
                        <div class= "col-lg-6">
                        <label for="name_credit">Nombre del titular</label>
                        <input type="text" id="name_credit" class="form-control input-lg" 
                               value="${creditCard.user.pname} ${creditCard.user.sname1} ${creditCard.user.sname2}" required pattern="[A-zÀ-ž\s]{1,}">
                        </div>
                        <div class="col-lg-6">
                        <label for="number">Número de Tarjeta</label>
                        <input type="number" name="number" id="number" class="form-control input-lg"
                               value="${creditCard.number}" required>
                        </div>
                   </div>
                    <div class="row">
                        <div class="col-xs-2">
                        <label for="month">Expiración (Mes)</label>
                        <input type="number" name="month" id="month" class="form-control input-lg" value="${creditCard.month}" min="1" max="12" required>
                        <!--1-12 Range-->
                        </div>
                        <div class="col-xs-2">
                        <label for="year">Expiració (Año)</label>
                        <input type="number" name="year" id="year" class="form-control input-lg" value="${creditCard.year}" min="2019" required>
                        <!--+19 Range-->
                        </div> 
                        <div class="col-xs-2" >
                        <label for="cvc">cvc</label>
                        <input type="number" id="cvc" name="cvc" class="form-control input-lg" value="${creditCard.securityCode}" min="1" max="999" required>
                        <!--3 digits Range-->
                        </div>
                   </div>
                    <button type="submit" class="btn btn-primary">Env&iacute;o</button>
                </form>
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
