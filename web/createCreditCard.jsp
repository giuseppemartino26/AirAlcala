<%-- 
    Document   : createCreditCard
    Created on : 12-ene-2019, 23:14:45
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>PaginaAd</title>   
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
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
                    <li class="active"><a href="userController?operation=list">Usarios</a></li>
                    <li class="active"><a href="administratorController?operation=list">Administradores</a></li>
                    <li class="active"><a href="airplaneController?operation=list">Aviones</a></li>
                    <li class="active"><a href="airportController?operation=list">Aeropuertos</a></li>
                    <li class="active"><a href="flightController?operation=list">Vuelos</a></li>        
                    <li class="active"><a href="routeController?operation=list">Rutas</a></li>        
                    <li class="active"><a href="saleController?operation=overview">Estadísticas</a></li>    <!-- aún no existe, hay que crearlo y calcular las estadísticas en el Controlador (GET) -->
                </ul>
              </div>
            </nav>
            <br>
            <div class="container">
                <h2>Crear Tarjeta de Cr&eacute;dito</h2>
                <br>
                
                <form method="POST" action="creditCardController" class="form-container" onsubmit="check_credit_data()">
                    <div class="form-group">
                        <label for="name_credit">Nombre del titular</label>
                        <input type="text" id="name_credit" class="form-control">
                    </div>
                    <div class="form-group">
                        <input type="number" id="number" class="form-control" placeholder="Numeros de tarjeta de crédito">
                    </div>    
                    <div class="form-group col-xs-2">
                        <input type="number" id="month" class="form-control" placeholder="mm" min="1" max="12">
                        <!--1-12 Range-->
                        <br>
                        <input type="number" id="year" class="form-control" placeholder="yy" min="2019" >
                        <!--+19 Range-->
                    </div>    
                    <div class="form-group" >
                        <input type="number" id="cvc" class="form-control" placeholder="cvc" min="1" max="999">
                        <!--3 digits Range-->
                    </div> 
                    <button type="submit" class="btn btn-primary">Env&iacute;o</button>
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
