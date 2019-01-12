<%-- 
    Document   : ListFlies
    Created on : 09-ene-2019, 13:12:03
    Author     : Martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>AirAlcala</title>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables.css">
   <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables_themeroller.css">
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
   <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="styles/styles.css">    
    
    <script>
    $(document).ready( function () {
        $('#datatable').DataTable();
    } );
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
            <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Administrador
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href="userController?operation=list">Usarios</a></li>
                      <li><a href="#">Vuelos</a></li>
                      <li><a href="#">Rutas</a></li>
                      <li><a href="#">...</a></li>
                    </ul>
            </li>         
        </ul>
      </div>
    </nav>
        
    <br>
    <br>
    <div class="container">
        <h4>Lista Usarios</h4>
        <div class="btn-group topButton" role="group" aria-label="Basic example">
            <a href="userController?operation=list" class="btn btn-primary" role="button">Volver a Lista</a>
            <a href="userController?operation=add" class="btn btn-primary" role="button">Añadir Usario</a>        
        </div>

        <br>
        <table class="table table-striped" style="width:100%">
            <tr>
                <th>Id</td> 
                    <td>${user.id}</td>
            </tr>
            <tr>
                <th>Nombre</td>
                    <td>${user.pname}</td>
            </tr>
            <tr>
                <th>Primer Apelido</td>
                    <td>${user.sname1}</td>
            </tr>
            <ttr>
                <th>Segundo Apelido</td>
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
