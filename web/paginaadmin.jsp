<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;" %>

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
                        <a class="navbar-brand" href="#">AirAlcal�</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="index.html">Inicio</a></li>
                </div>
            </nav>
            <br>
            <div class="container">
                <h2>P&aacutegina de el Administrador</h2>
                <br>

                <table class="table">
                    <tbody>
                        <tr>
                            <td>   <div class="topButton"><a href="userController?operation=list" class="btn btn-primary" role="button">Gestionar usuarios</a></div> </td>



                            <td>     <div class="topButton"><a href="flightController?operation=list" class="btn btn-primary" role="button">Gestionar vuelos</a></div> </td>


                            <td>   <div class="topButton"><a href="airportController?operation=list" class="btn btn-primary" role="button">Gestionar aeropuertos</a></div> </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <footer>
            <div class='container'>
                <div class="row">
                    <div class="col-xs-6">
                        <p>
                            AirAlcala - Alcal&aacute; de Henares, Madrid, Espa�a
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