<%-- 
    Document   : newjsp
    Created on : 09-en-2019, 9.40.51
    Author     : Martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;" %>
<!DOCTYPE html>

   <html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>Crear Cuenta de Administrador</title>   
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<script>
                function validacion() {
                    opciones = document.getElementsByName("email");
                    if(opciones[5].checked){
                        valor = parseInt(document.getElementById("otro_g_coche").value);
                        if($("#email").val().length < 1){
                            alert('[ERROR] El campo email no debe estar vacio');
                        return false;
                        }
                        if($("#otro_n_coche").val().length < 1){
                            alert('[ERROR] El campo nombre no debe estar vacio');
                            return false;
                        }
                    }
                    opciones = document.getElementsByName("circuito");
                    if(opciones[5].checked){
                        valor = parseInt(document.getElementById("otro_n_c_circuito").value);
                        if(valor<6 || valor>20){
                            alert('[ERROR] El campo curvas por vuelta debe tener un valor entre 6 y 20');
                            return false;
                        }
                        valor = parseInt(document.getElementById("otro_l_circuito").value);
                        if(valor<3000 || valor>9000){
                            alert('[ERROR] El campo longitud por vuelta debe tener un valor entre 3.000 m y 9.000 m');
                            return false;
                        }
                        valor = parseInt(document.getElementById("otro_n_v_circuito").value);
                        if(valor<40 || valor>80){
                            alert('[ERROR] El campo numero de vueltas debe tener un valor entre 40 y 80');
                            return false;
                        }
                        if($("#otro_n_circuito").val().length < 1){
                            alert('[ERROR] El campo nombre del circuito no debe estar vacio');
                            return false;
                        }
                        if($("#otro_c_circuito").val().length < 1){
                            alert('[ERROR] El campo ciudad del circuito no debe estar vacio');
                            return false;
                        }
                        if($("#otro_p_circuito").val().length < 1){
                            alert('[ERROR] El campo pais del circuito no debe estar vacio');
                            return false;
                        }
                    }
                    return true;
                }
</script>
</head>

<body>
    <%
    //allow access only if session exists
    if(session.getAttribute("sessionAdminId") == null){
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
            <a class="navbar-brand" href="#">AirAlcala</a>
        </div>
        <ul class="nav navbar-nav">
            <%if(session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)){ %>
                <li class="active"><a href="userController?operation=list">Usarios</a></li>
                <li class="active"><a href="administratorController?operation=list">Administradores</a></li>
                <li class="active"><a href="airplaneController?operation=list">Aviones</a></li>
                <li class="active"><a href="airportController?operation=list">Aeropuertos</a></li>
                <li class="active"><a href="flightController?operation=list">Vuelos</a></li>        
                <li class="active"><a href="routeController?operation=list">Rutas</a></li>        
                <li class="active"><a href="saleController?operation=overview">Estadísticas</a></li>    <!-- aún no existe, hay que crearlo y calcular las estadísticas en el Controlador (GET) -->
            <%} if(session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)){%>
                <li class="active"><a href="flightController?operation=search">Buscar Vuelos</a></li>
                <li class="active"><a href="salesController?operation=list&userId=<%=session.getAttribute("sessionUserId")%>">Mirar Compras</a></li>
                <li class="active"><a href="creditcardController?operation=list">Editar Medios de Pago</a></li>
            <%} if(session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null){ %>
                <li class="active"><a href="index.jsp">Inicio</a></li>
                <li class="active"><a href="userController?operation=add">Crear Cuenta</a></li>
            <%}%>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <%if(session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)){ %>
                <li><a href="administratorController?operation=view&adminId=<%=session.getAttribute("sessionAdminId")%>">
                        <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionAdminPname")%></a></li>
                <li><a href="adminlogoutController"><span class="glyphicon glyphicon-log-out"></span>Admin Logout</a></li>
            <%} if(session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)){%>
                <li><a href="userController?operation=view&userId=<%=session.getAttribute("sessionUserId")%>">
                        <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionUserPname")%></a></li>
                <li><a href="logoutController"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            <%} if(session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null){%>
                <li><a href="loginController"><span class="glyphicon glyphicon-log-out">
                    </span>Login</a></li>
            <% } %>
        </ul>
      </div>
    </nav>
            <br>
            <div class="container">
            <h2>Crear Cuenta de Administrador</h2>
            <br>
        <div class="btn-group topButton" role="group" aria-label="Basic example">
            <a href="administratorController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
        </div>
            <form method="POST" action="administratorController" class="form-container" onsubmit="showResponse()">
            <div class="row">
                <div class="col-lg-4">
                <label for="pname"><b>Nombre</b></label>
                <input placeholder="Nombre" name="pname" id="pname" type="text">
                </div>
                
                <div class="col-lg-4">              
                <label for="sname1"><b>Primer Apelido</b></label>
                <input placeholder="Primer Apelido" name="sname1" id="sname1" type="text">
                </div>
                
                <div class="col-lg-4">
                <label for="sname2"><b>Segundo Apelido</b></label>
                <input placeholder="Segundo Apelido" name="sname2" id="sname2" type="text">
                </div>
            </div>
                
            <div class="row">
                <div class="col-lg-4">
                <label for="email"><b>Email</b></label>
                <input placeholder="Email" name="email" id="email" type="email">
                </div>
                <div class="col-lg-4">
                <label for="pass1"><b>Contraseña</b></label>
                <input name="pass1" id="pass1" type="password">
                </div>
                <div class="col-lg-4">
                <label for="pass2"><b>Repetir Contraseña</b></label>
                <input name="pass2" id="pass2" type="password">
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