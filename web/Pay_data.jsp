<%-- 
    Document   : Pay_data
    Created on : 29-dic-2018, 8:24:39
    Author     : pablo
--%>

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
            if (session.getAttribute("sessionUserId") == null) {
                response.sendRedirect("loginController");
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
                <form method="POST" action="saleController"  class="form-container"onsubmit="showResponse()">
                    <p>Cantidad a Pagar: <%=session.getAttribute("price")%></p>
                    <p>Comprobacion de Datos de Pago</p>
                    <table class="table table-striped" style="width:90%">
                        <tr>
                            <th>Nombre</th>
                            <td>${user.pname}</td>
                            <th>Primer Apelido</th>
                            <td>${user.sname1}</td>
                        </tr>
                        <tr>

                            <th>Segundo Apelido</th>
                            <td>${user.sname2}</td>
                        </tr>
                        <tr>
                            <th>Segundo Apelido</th>
                            <td>${user.sname2}</td>
                            <th>Email</th>
                            <td>${user.email}</td>
                        </tr>
                        <tr>
                            <th>Birthday</th>
                            <td>${user.bday}</td>
                            <th>Dirección</th>
                            <td>${user.address}</td>
                        </tr>
                        <tr>
                            <th>Ciudad</th>
                            <td>${user.city}</td>
                            <th>País</th>
                            <td>${user.country}</td>
                        </tr>
                        <tr>
                            <th>Elige una Tarjeta Cr&eacute;dito</th>
                            <td>
                                <select name="creditCardId" class="form-control" id="creditCardId" required>
                            <c:forEach items="${creditcards}" var="creditCard">    
                                <option value="${creditCard.id}">${creditCard.number}</option>
                            </c:forEach> 
                        </select>
                            </td>
                        </tr>
                        


                    </table>
                    <button type="submit" class="btn">Env&iacute;o</button>
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