<%-- 
    Document   : Pay_data
    Created on : 29-dic-2018, 8:24:39
    Author     : pablo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>AirAlcala</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>         /*Styles for get nice header and a sticky footer at the bottom of the page*/
            * {
                margin: 0;
                padding: 0;
            }
            html,body {
                height:100%;
            }
            #wrapper {  /*wraps the body and the header to make the footer work*/
                min-height:100%;
            }
            header{
                background:#597ea2;
                color:#fff;
            }
            footer {
                position: relative;
                height: 40px;
                padding:5px 0px;
                clear: both;
                background: #8aa4bd;
                text-align: center;
                color: #fff;
            }
        </style>
        <script>
            $(document).ready(function () {
                $('#datatable').DataTable();
            });
        </script>
    </head>
    <body>
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
                </div>
            </header>
            <div class="btn-group btn-group-justified" >
                <a href="index.html" class="btn btn-primary"><span class="glyphicon glyphicon-home"></span> Inicio</a>
                <a href="#" class="btn btn-primary"><span class="glyphicon glyphicon-user"></span> Mi cuenta</a>
                <a href="#" class="btn btn-primary"><span class="glyphicon glyphicon-lock"></span> Administrador</a>
            </div>
            <br>
            <div class="container">
                <form method="POST" action="saleController" class="form-container" onsubmit="showResponse()">
                <p>Cantidad a Pagar: <%=session.getAttribute("price")%></p>
                <p>Comprobacion de Datos de Pago</p>
                <table class="table table-striped" style="width:100%">
            <tr>
                <th>Id</th> 
                    <td>${user.id}</td>
            </tr>
            <tr>
                <th>Nombre</th>
                    <td>${user.pname}</td>
            </tr>
            <tr>
                <th>Primer Apelido</th>
                    <td>${user.sname1}</td>
            </tr>
            <tr>
                <th>Segundo Apelido</th>
                    <td>${user.sname2}</td>
            </tr>
            <tr>
                <th>Email</th>
                    <td>${user.email}</td>
            </tr>
            <tr>
                <th>Birthday</th>
                    <td>${user.bday}</td>
            </tr>
            <tr>
                <th>Dirección</th>
                    <td>${user.address}</td>
            </tr>
            <tr>
                <th>Codigo Postal</th>
                    <td>${user.pcode}</td>
            </tr>
            <tr>
                <th>Ciudad</th>
                    <td>${user.city}</td>
            </tr>
            <tr>
                <th>País</th>
                    <td>${user.country}</td>
            </tr>
            <tr>
                <th>Tarjeta Cr&eacute;dito</th>
                <td>${creditCard.number}</td>
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