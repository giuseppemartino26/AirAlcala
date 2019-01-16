<%-- 
    Document   : newjsp
    Created on : 09-en-2019, 9.40.51
    Author     : Martin
--%>

<%@page import="Model.User"%>
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
            function checkPasswordMatch() {
                if ($("#pass1").val() !== $("pass2").val())
                    alert("Passwords do not match!");
            }
        </script>

    </head>
    <body>     
        <%
            // Realizes that a user can only open his own edit form
            User user = (User) request.getAttribute("user");
            int userId=-1;
            if(user!=null)
                userId = user.getId();
            Integer sessionUserId = (Integer) session.getAttribute("sessionUserId");
            //allow access only if session exists
            if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null)
                response.sendRedirect("loginController");
            if(user != null){
                if( sessionUserId != userId)
                    response.sendRedirect("userController?operation=edit&userId="+sessionUserId);
            } else{
                response.sendRedirect("userController?operation=edit&userId="+sessionUserId);
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
                        <a class="navbar-brand" href="index.html">AirAlcalá</a>
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
                        <li class="active"><a href="index.html">Buscar Vuelos</a></li>
                        <li class="active"><a href="saleController?operation=list&userId=<%=session.getAttribute("sessionUserId")%>">Mirar Compras</a></li>
                        <li class="active"><a href="creditcardController?operation=edit&userId=<%=session.getAttribute("sessionUserId")%>">Editar Medios de Pago</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) { %>
                        <li class="active"><a href="index.html">Inicio</a></li>
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
                <h2>Editar Cuenta de Usario <%=session.getAttribute("sessionUserId")%></h2>
                <br>
                <div class="btn-group topButton" role="group" aria-label="Basic example">
                    <% if (session.getAttribute("sessionAdminId") != null) { %>
                    <a href="userController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
                    <a href="userController?operation=add" class="btn btn-primary" role="button">Añadir Usario</a>        
                    <% }
                        if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                    <a href="userController?operation=view&userId=<%=session.getAttribute("sessionUserId")%>" class="btn btn-primary" role="button">Volver a Mirar Datos</a>  
                    <% }%>
                </div>
                <form method="POST" action="userController" class="form-container" onsubmit="showResponse()">
                    <div class="row">
                        <input type="hidden" name="id" value="${user.id}" />
                        <div class="col-lg-4">
                            <label for="pname"><b>Nombre</b></label>
                            <input placeholder="Nombre" name="pname" id="pname" type="text"
                                   value="${user.pname}" required pattern="[A-zÀ-ž\s]{1,}"/>
                        </div>

                        <div class="col-lg-4">              
                            <label for="sname1"><b>Primer Apelido</b></label>
                            <input placeholder="Primer Apelido" name="sname1" id="sname1" type="text"
                                   value="${user.sname1}" required pattern="[A-zÀ-ž\s]{1,}" />
                        </div>

                        <div class="col-lg-4">
                            <label for="sname2"><b>Segundo Apelido</b></label>
                            <input placeholder="Segundo Apelido" name="sname2" id="sname2" type="text"
                                   value="${user.sname2}" pattern="[A-zÀ-ž\s]{1,}" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-6">
                            <label for="email"><b>Email</b></label>
                            <input placeholder="Email" name="email" id="email" type="email"
                                   value="${user.email}" />
                        </div>

                        <div class="col-lg-6">               
                            <label for="bday"><b>Fecha de Nacimiento</b></label> 
                            <input  name="bday" id="bday" type="date"
                                    value="${user.bday}" required />
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-6">
                            <label for="pass1"><b>Contraseña</b></label>
                            <input placeholder="sin cambio" name="pass1" id="pass1" type="password"
                                   value="" required onChange="checkPasswordMatch()"/>
                        </div>
                        <div class="col-lg-6">
                            <label for="pass2"><b>Repetir Contraseña</b></label>
                            <input placeholder="sin cambio" name="pass2" id="pass2" type="password"
                                   value="" required onChange="checkPasswordMatch()"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-4">
                            <label for="sname2"><b>Dirección</b></label>
                            <input placeholder="Dirección" name="addr" id="addr" type="text"
                                   value="${user.address}" required/>
                        </div>

                        <div class="col-lg-2">    
                            <label for="pcode"><b>Código Postal</b></label>
                            <input placeholder="12345" name="pcode" id="pcode" type="number"
                                   value="${user.pcode}" required maxlength="5"/>
                        </div>

                        <div class="col-lg-3">    
                            <label for="pcode"><b>Ciudad</b></label>
                            <input placeholder="Alcalá de Henares" name="city" id="city" type="text"
                                   value="${user.city}" required pattern="[A-zÀ-ž\s]{1,}" />
                        </div>

                        <div class="col-lg-3">
                            <label for="sname2"><b>País</b></label>
                            <input placeholder="España" name="country" id="country" type="text"
                                   value="${user.country}" required pattern="[A-zÀ-ž\s]{1,}" />
                        </div>               
                    </div>

                    <div class="row">
                        <div class="col-lg-6">
                            <button type="submit" class="btn">Env&iacute;o</button>
                        </div>

                        <div class="col-lg-6">
                            <button type="reset" class="reset" >Reiniciar</button>
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