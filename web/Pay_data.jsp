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
        /*first form*/
        function check_first_data(){
            if($("#name").val()==""){
                alert("Falta el Nombre y Apellido/s");
                return false;
            }
            if($("#address").val()==""){
                alert("Falta el destino del vuelo");
                return false;
            }
            if($("#zipcode").val()==""){
                alert("Falta el codigo postal");
                return false;
            }
            if($("#city").val()==""){
                alert("Falta la ciudad");
                return false;
            }
            if($("#country").val()==""){
                alert("Falta el pais");
                return false;
            }
            return true;
        };
        /*second form*/
        
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
            <form action="" onsubmit="return check_first_data()">
                <p>Cantidad a Pagar: <%=session.getAttribute("Price")%></p>
                <p>Datos de Pago</p>
                <fieldset>
                    <div class="form-group">
                            <label for="name">Nombre y Apellido/s</label>
                            <input type="text" id="name" class="form-control">
                    </div>
                    <div class="form-group">
                            <label for="address">Direcci&oacute;n</label>
                            <input type="text" id="address" class="form-control">
                    </div>
                    <div class="form-group">
                            <label for="zipcode">C&oacute;digo Postal</label>
                            <input type="text" id="zipcode" class="form-control">
                    </div>    
                    <div class="form-group">
                            <label for="city">Ciudad</label>
                            <input type="text" id="city" class="form-control">
                    </div>    
                    <div class="form-group">
                            <label for="country">Pa&iacute;s</label>
                            <input type="text" id="country" class="form-control">
                    </div>    
                        <!--check the first data of this form-->
                        <button type="submit" class="btn btn-primary" id="button_pay">Pagar</button>
                </fieldset>  
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