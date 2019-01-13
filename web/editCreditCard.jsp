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
        <title>PaginaAd</title>   
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
                if ($("#name_credit").val() == "") {
                    alert("Falta el titular");
                    return false;
                }
                if ($("#number_credit").val() == "") {
                    alert("Falta el numero de tarjeta de credito");
                    return false;
                }
                if ($("#month").val() == "" || $("#year").val() == "") {
                    alert("Falta el mes y/o año");
                    return false;
                }
                if ($("#cvc").val() == "") {
                    alert("Falta el numero cvc");
                    return false;
                }
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
                <h2>Editar Tarjeta de Cr&eacute;dito</h2>
                <br>
                <form method="POST" action="creditCardController" class="form-container" onsubmit="check_credit_data()">
                    <input type="hidden" name="id" value="${creditCard.id}" />
                    <div class="form-group">
                        <label for="name_credit">Nombre del titular</label>
                        <input type="text" id="name_credit" class="form-control">
                    </div>
                    <div class="form-group">
                        <input type="number" id="number" class="form-control" placeholder="${creditCard.number}">
                    </div>    
                    <div class="form-group col-xs-2">
                        <input type="number" id="month" class="form-control" placeholder="${creditCard.month}" min="1" max="12">
                        <!--1-12 Range-->
                        <br>
                        <input type="number" id="year" class="form-control" placeholder="${creditCard.year}" min="2019" >
                        <!--+19 Range-->
                    </div>    
                    <div class="form-group" >
                        <input type="number" id="cvc" class="form-control" placeholder="${creditCard.securityCode}" min="1" max="999">
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
    </body>
</html>
