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
    <title>AirAlcala</title>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables.css">
   <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables_themeroller.css">
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
   <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/jquery.dataTables.min.js"></script>
   
   <style>         
        /*Styles for get nice header and a sticky footer at the bottom of the page*/
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
            
            .topButton{
                padding-top: 5px;
                padding-right: 3px;
                padding-bottom: 5px;
                padding-left: 3px;
            }
    </style>
    
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
            <a class="navbar-brand" href="index.html">AirAlcalá</a>
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
        <div class="topButton"><a href="userController?operation=add" class="btn btn-primary" role="button">Añadir Usario</a></div>
        <br>
        <table id="datatable" class="display" style="width:100%">
            <thead>
            <tr>
                <th>Id</td>
                <th>Nombre</td>
                <th>Primer Apelido</td>
                <th>Segundo Apelido</td>
                <th>Email</th>
                <th>Birthday</th>
                <th>Dirección</th>
                <th>Codigo Postal</th>
                <th>Ciudad</th>
                <th>País</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.id}" /></td>
                        <td><c:out value="${user.pname}" /></td>
                        <td><c:out value="${user.sname1}" /></td>
                        <td><c:out value="${user.sname2}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        <td><c:out value="${user.bday}" /></td>
                        <td><c:out value="${user.address}" /></td>
                        <td><c:out value="${user.pcode}" /></td>
                        <td><c:out value="${user.city}" /></td>
                        <td><c:out value="${user.country}" /></td>
                        <td><a href="userController?operation=view&userId=<c:out value="${user.id}"/>">mirar</a></td>
                        <td><a href="userController?operation=edit&userId=<c:out value="${user.id}"/>">actualizar</a></td>
                        <td><a href="userController?operation=delete&userId=<c:out value="${user.id}"/>">borrar</a></td>
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
                <th>Birthday</th>
                <th>Dirección</th>
                <th>Codigo Postal</th>
                <th>Ciudad</th>
                <th>País</th>
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
