<%-- 
    Document   : manageRoutes
    Created on : 12-gen-2019, 21.05.42
    Author     : fabri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Routes</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
    </head>
    <body>
        <h2>Pagina de gestión de las rutas</h2>

        <table>

            <tbody>
                <tr>
                    <td > <input name="UpdateRoute" id="addRoute" value="add" type="radio">
                        <label for="addRoute">Añadir ruta</label> </td>

                    <td><input name="UpdateRoute" id="editRoute" value="edit" type="radio">
                        <label for="editRoute">Modificar ruta</label><br> </td>

                    <td><input name="UpdateRoute" id="deleteRoute" value="delete" type="radio">
                        <label for="deleteRoute">Borrar ruta</label><br>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="form-popup" id="myForm">

            <form class="form-container" action="routeController" method="post"> 

                <label for="id"><b>ID Ruta</b></label>
                <input placeholder="Insertar el ID" name="id" id="id" type="number" required=""> 
                
                <label for="originID"><b>ID origen</b></label> 
                <input placeholder="Insertar ID de la origen" name="originID" id="originID" required="" type="number">
                
                <label for="destinationID"><b>ID destino</b></label> 
                <input placeholder="Insertar ID del destino" name="destinationID" id="destinationID" required="" type="number">
                
                <label for="airplaneID"><b>ID ruta</b></label> 
                <input placeholder="Insertar ID de la ruta" name="airplaneID" id="airplaneID" required="" type="number">

                <label for="ticketPrice"><b>Precio del billete</b></label> 
                <input placeholder="Insertar el precio del billete" name="ticketPrice" id="ticketPrice" type="number"> 
                
                <label for="tax"><b>Tax</b></label> 
                <input placeholder="Insertar el percentaje de taxas" name="tax" id="tax" type="number"> 
                
                <label for="luggagePrice"><b>Precio por malleta</b></label> 
                <input placeholder="Insertar el precio de una malleta" name="luggagePrice" id="luggagePrice" type="number"> 

                <button type="submit" class="btn">Envío</button> </form>
        </div>


    </body>
</html>
