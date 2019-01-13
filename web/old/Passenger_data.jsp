<%-- 
    Document   : Passenger_data
    Created on : 28-dic-2018, 18:46:49
    Author     : pablo
--%>

<%--
    I don't know if we use sessions or we use sql
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
        <form action="" method="post" onsubmit="return check_data()">
            <div class="form-group" id="passenger1" style="display:none">
                <h4>Primer Pasajero</h4>
                <label for="name_1">Nombre y Apellidos</label>
                <input type="text" id="name_1" class="form-control">
                <label for="dni_1">DNI/Pasaporte/NIE</label>
                <input type="text" id="dni_1" class="form-control">
            </div>
            <div class="form-group" id="passenger2" style="display:none">
                <h4>Segundo Pasajero</h4> 
                <label for="name_2">Nombre y Apellidos</label>
                <input type="text" id="name_2" class="form-control">
                <label for="dni_2">DNI/Pasaporte/NIE</label>
                <input type="text" id="dni_2" class="form-control">
            </div>
            <div class="form-group" id="passenger3" style="display:none">
                <h4>Tercer Pasajero</h4>
                <label for="name_3">Nombre y Apellidos</label>
                <input type="text" id="name_3" class="form-control">
                <label for="dni_3">DNI/Pasaporte/NIE</label>
                <input type="text" id="dni_3" class="form-control">
            </div>
            <div class="form-group" id="passenger4" style="display:none">
                <h4>Cuarto Pasajero</h4>
                <label for="name_4">Nombre y Apellidos</label>
                <input type="text" id="name_4" class="form-control">
                <label for="dni_4">DNI/Pasaporte/NIE</label>
                <input type="text" id="dni_4" class="form-control">
            </div>
            <div class="form-group" id="passenger5" style="display:none">
                <h4>Quinto Pasajero</h4>
                <label for="name_5">Nombre y Apellidos</label>
                <input type="text" id="name_5" class="form-control">
                <label for="dni_5">DNI/Pasaporte/NIE</label>
                <input type="text" id="dni_5" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Siguiente</button>
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