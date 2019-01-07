<%-- 
    Document   : SignUp
    Created on : 06-ene-2019, 22:36:12
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        var passengers=3;/*these instead of 3 <%=session.getAttribute("Passengers")%>*/
        $(document).ready(function () {
            var i=1;
             
            while(i<=passengers){   /*Show shows only the passengers we want*/
                $("#passenger"+i).toggle();
                i++;
            };
        });
        function check_data(){
            var i=1;
            while(i<=passengers){       /*validate the data of the passengers we want*/
                if($("#name_"+i).val()=="" || $("#dni_"+i).val()==""){
                    alert("Faltan datos del pasajero "+i+"");
                    return false;
                };
                i++;
            };
            return true;
        };
        
        
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
        <table class="table table-striped">
            <tr>
                <h4>Datos Personales</h4> 
                <td>Nombre</td>
                <td>Name</td><!--<%=session.getAttribute("Name")%>-->
                <td>Apellido</td>
                <td>Apellido</td><!--<%=session.getAttribute("Subname")%>-->
            </tr>
        </table> 
        
        <table class="table table-striped">
            <tr>
                <h4>Compras Realizadas</h4> 
                <td>Origen</td>
                <td>Origin</td><!--<%=session.getAttribute("flightOrigin")%>-->
                <td>Destino</td>
                <td>Destination</td><!--<%=session.getAttribute("flightDest")%>-->
                <td>Fecha</td>
                <td>Date</td><!--<%=session.getAttribute("datetime")%>-->
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