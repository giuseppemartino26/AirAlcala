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

</head>

<body>
    <div id="wrapper">
    <header>
        <div class="container">
            <h1>Air Alcala</h1>
        </div>
    </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">AirAlcalá</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.html">Inicio</a></li>
                    <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Administrador
                            <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                              <li><a href="userController?operation=list">Usarios</a></li>
                              <li><a href="#">Vuelos</a></li>
                              <li><a href="#">Rutas</a></li>
                              <li><a href="adminController?operation=list">Administradores</a></li>
                            </ul>
                    </li>         
                </ul>
              </div>
            </nav>
            <br>
            <div class="container">
            <h2>Crear Cuenta de Administrador</h2>
            <br>
        <div class="btn-group topButton" role="group" aria-label="Basic example">
            <a href="adminController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
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