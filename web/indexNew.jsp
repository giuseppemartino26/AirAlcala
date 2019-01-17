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
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
                </div>
            </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="index.html">AirAlcalá</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="index.html">Inicio</a></li>
                        <li class="active"><a href="paginauser.jsp">Login Usuario</a></li>  
                        <li class="active"><a href="adminloginController">Login Admin</a></li>
                    </ul>
                </div>
            </nav>
            <br>
            <div class="container">                
                <form method="POST" action="searchController" class="form-container" onsubmit="return check_data()">
                    <div class="form-group">
                        <label for="source" class="control-label">Origen</label>
                        <select name="source" id="source" required>
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
                        <select name="destination" id="destination" required>
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
                        <select class="form-control" name="passengers" id="passengers" required>
                            <option value="1">1 pasajero</option>
                            <option value="2">2 pasajeros</option>
                            <option value="3">3 pasajeros</option>
                            <option value="4">4 pasajeros</option>
                            <option value="5">5 pasajeros</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="class">Clase</label>
                        <select class="form-control" name="flyclass" id="class" required>
                            <option value="economy">Turista</option>
                            <option value="first">Primera</option>
                        </select>
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