<%-- 
    Document   : Summary
    Created on : 28-dic-2018, 18:57:48
    Author     : pablo
--%>

<%--
    I don't know if we use sessions or we use sql
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
            var round_trip=true; /*round_trip=<%=session.getAttribute("Roundtrip")%>*/
            if(round_trip){
                $("#departure_2").toggle();
            };
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
        <h4>Resumen de la compra</h4>
        <table class="table table-striped">
            <tr>
                <td>ID Compra</td>
                <td>Sales ID</td><!--<%=session.getAttribute("ID_sale")%>-->
            </tr>
            <tr>
                <td>Origen</td>
                <td>Origin</td><!--<%=session.getAttribute("Origin")%>-->
            </tr>
            <tr>
                <td>Destino</td>
                <td>Destination</td><!--<%=session.getAttribute("Destination")%>-->
            </tr>
            <tr>
                <td>Fecha Ida</td>
                <td>departure date</td><!--<%=session.getAttribute("Departure_date")%>-->
            </tr>
            <tr style="display:none" id="departure_2">
                <td>Fecha Vuelta</td>
                <td>Departure 2</td><!--<%=session.getAttribute("Departure_2")%>-->
            </tr>
            <tr>
                <td>N&uacute;mero de pasajeros</td>
                <td>Passengers</td><!--<%=session.getAttribute("Passengers")%>-->
            </tr>
            <tr>
                <td>Precio</td>
                <td>Price</td><!--<%=session.getAttribute("Price")%>-->
            </tr>
        </table>
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