<%-- 
    Document   : index
    Created on : 16-gen-2019, 19.53.13
    Author     : fabri
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Model.Airport"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Repository.JDBC.JDBCAirportDAO"%>
<%@page import="Model.Repository.AirportDAO"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                if($('#round_trip').is(':checked')){
                    $("#div_departure_2").show();       /*only if use back in browser*/
                } ;
            });
            
            function check_data() {
                var dNow = new Date();
                dNow.setHours(0,0,0,0);
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
                if($("#source").val()===$("#destination").val()){
                    alert("El origen no puede ser igual al destino");
                    return false;
                }
                return true;
            }
            ;
        </script>

    </head>
    <body>
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
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
                        <li class="active"><a href="loginController?operation=login">Cuenta Usuario</a></li>
                        <li class="active"><a href="adminloginController">Login Admin</a></li>
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
                        <li><a href="loginController?operation=login"><span class="glyphicon glyphicon-log-out">
                                </span>Login</a></li>
                                <% }%>
                    </ul>
                </div>
            </nav>
            <br>
            <div class="container">                
                <form method="POST" action="searchController" class="form-container" onsubmit="return check_data()">
                    <div class="form-group">
                        <label for="source" class="control-label">Origen</label>
                        <select name="source" class="form-control" id="source" required>
                            <%
                                try {
                                    String Query = "select * from airports";
                                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                                    Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
                                    Statement stm = conn.createStatement();
                                    ResultSet rs = stm.executeQuery(Query);
                                    while (rs.next()) {
                                        String name = rs.getString("name");
                            %>
                            <option value="<%=name%>"> <%=name%> </option>

                            <%
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    out.println("Error" + ex.getMessage());
                                }
                            %>


                        </select>
                    </div>
                    <div class="form-group">
                        <label for="destination">Destino</label>
                        <select name="destination" class="form-control" id="destination" required>
                            <%
                                try {
                                    String Query = "select * from airports";
                                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                                    Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala", "root", "root");
                                    Statement stm = conn.createStatement();
                                    ResultSet rs = stm.executeQuery(Query);
                                    while (rs.next()) {
                                        String name = rs.getString("name");
                            %>
                            <option value="<%=name%>"> <%=name%> </option>

                            <%
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    out.println("Error" + ex.getMessage());
                                }
                            %>


                        </select>
                    </div>
                    <div class="form-group">
                        <label for="departure_date">Fecha de Salida</label>
                        <input type="date" id="departure_date" name="departure_date" class="form-control" required> 
                    </div>
                    <div class="form-group">
                        <!--Only 5 passengers with you-->
                        <label for="passengers">Pasajeros</label>
                        <input type="number" min="0" id="passengers" name="passengers" required>
                    </div>
                    <label for="round_trip">Ida/Vuelta</label>
                    <input type="checkbox" name="ida_vuelta" id="round_trip"><br>

                    <!--hide until you check round_trip-->
                    <div class="form-group" id="div_departure_2" style="display:none">  
                        <label for="departure_date_2" id="label_date_2" >Fecha de salida del vuelo de vuelta</label>
                        <input type="date" name="departure_date_2" id="departure_date_2" class="form-control">
                    </div>

                    <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-arrow-right"></i>Buscar</button>
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
                            <li><a href="adminloginController">Login Administrador</a></li>
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