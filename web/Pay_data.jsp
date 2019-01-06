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
        /*validate visa*/
        function visa_cardnumber(){
            var cardno = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/; /*visa format*/
            if($("#number_credit").val().match(cardno)){
                    return true;
            }else{
                    return false;
            }
        };
        /*validate mastercard*/
        function mc_cardnumber(){
            var cardno = /^(?:5[1-5][0-9]{14})$/;/*mastercard format*/
            if($("#number_credit").val().match(cardno)){
                    return true;
            }else{
                    return false;
            }
        };
        $(document).ready(function () {
            $("#button_pay").on("click",function(){
                if( check_first_data()){
                    $("#modal-credit-card").modal();/*run modal*/
                    /*https://getbootstrap.com/docs/4.0/components/modal/*/
                };  
            });
        });
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
        function check_credit_data(){
            if($("#name_credit").val()==""){
                alert("Falta el titular");
                return false;
            }
            if($("#number_credit").val()==""){
                alert("Falta el numero de tarjeta de credito");
                return false;
            }
            if($("#month").val()==""||$("#year").val()==""){
                alert("Falta el mes y/o año");
                return false;
            }
            if($("#cvc").val()==""){
                alert("Falta el numero cvc");
                return false;
            }
            if(!mc_cardnumber() && !visa_cardnumber()){
                alert("Numero de tarjeta de credito no valido");
                return false;
            }
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
            <form action="" onsubmit="return check_credit_data()">
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
                        <button type="button" class="btn btn-primary" id="button_pay" onclick="return check_first_data()">Pagar</button>
                </fieldset>  
                <!-- /.modal -->
                <div class="modal fade" id="modal-credit-card" tabindex="-1" role="dialog">
                        <!-- /.modal-dialog -->
                        <div class="modal-dialog" role="document">
                          <!-- /.modal-content -->
                          <div class="modal-content">
                            <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                              <h4 class="modal-title">Tarjeta de Cr&eacute;dito (Visa o Mastercard)</h4>
                            </div>
                            <!--Second form   validate credit card-->
                            <div class="modal-body">
                                <div class="form-group">
                                        <label for="name_credit">Nombre del titular</label>
                                        <input type="text" id="name_credit" class="form-control">
                                </div>
                                <div class="form-group">
                                        <input type="number" id="number_credit" class="form-control" placeholder="Numeros de tarjeta de crédito">
                                </div>    
                                <div class="form-group col-xs-2">
                                        <input type="number" id="month" class="form-control" placeholder="mm" min="1" max="12">
                                        <!--1-12 Range-->
                                        <br>
                                        <input type="number" id="year" class="form-control" placeholder="yy" min="19" >
                                        <!--+19 Range-->
                                </div>    
                                <div class="form-group" >
                                        <input type="number" id="cvc" class="form-control" placeholder="cvc" min="1" max="999">
                                        <!--3 digits Range-->
                                </div>            
                            </div>
                            <div class="modal-footer">
                              <button type="submit" class="btn btn-primary">Pagar</button>
                              <!--submit of all form-->
                            </div>
                        </div>
                    </div>
                </div>
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