<%-- 
    Document   : ListUsers
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
    <title>Lista de Administradores</title>
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
    <br>
    <div class="container">
        <h4>Lista de Administradores</h4>
        <div class="topButton"><a href="administratorController?operation=add" class="btn btn-primary" role="button">Añadir Administrador</a></div>
        <br>
        <table id="datatable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Localizador</td>
                <th>Ruta</td>
                <th>Salida</td>
                <th>Llegada</td>
                <th></th>
                <th></th>
                <th></th>                
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${flights}" var="flight">
                    <tr>
                        <td><c:out value="${flight.locator}" /></td>
                        <td><c:out value="${flight.ruta.origin} a ${flights.ruta.destination} "/></td>
                        <td><c:out value="${flight.departure}" /></td>
                        <td><c:out value="${flight.arrival}" /></td>
                        <td><a href="administratorController?operation=view&adminId=<c:out value="${flight.id}"/>">mirar</a></td>
                        <td><a href="administratorController?operation=edit&adminId=<c:out value="${flight.id}"/>">actualizar</a></td>
                        <td><a href="administratorController?operation=delete&adminId=<c:out value="${flight.id}"/>">borrar</a></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Id</td>
                <th>Nombre</td>
                <th>Primer Apelido</td>
                <th>Segundo Apelido</td>
                <th>Email</th>
                <th></th>
                <th></th>
                <th></th>

            </tr> 
            </tfoot>
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
