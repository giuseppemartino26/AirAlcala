<!DOCTYPE html>

<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>AirAlcala</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        <%--<script>  
            function validation() {
                if(document.getElementById('email').value == ''){
                    alert('Introduzca un email');
                    return false;
                    }
                if(document.getElementById('pass').value == '') {
                    alert('Introduzca una contrase�a');
                    return false;
                    }
                    return true;
                }
        </script>--%>
    </head>
    <body>
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
                </div>
            </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="indexNew.jsp">AirAlcal�</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <%if (session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) { %>
                        <li class="active"><a href="userController?operation=list">Usarios</a></li>
                        <li class="active"><a href="administratorController?operation=list">Administradores</a></li>
                        <li class="active"><a href="airplaneController?operation=list">Aviones</a></li>
                        <li class="active"><a href="airportController?operation=list">Aeropuertos</a></li>
                        <li class="active"><a href="flightController?operation=list">Vuelos</a></li>        
                        <li class="active"><a href="routeController?operation=list">Rutas</a></li>        
                        <li class="active"><a href="saleController?operation=overview">Estad�sticas</a></li>    <!-- a�n no existe, hay que crearlo y calcular las estad�sticas en el Controlador (GET) -->
                            <%}
                                if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li class="active"><a href="indexNew.jsp">Buscar Vuelos</a></li>
                        <li class="active"><a href="saleController?operation=list&userId=<%=session.getAttribute("sessionUserId")%>">Mirar Compras</a></li>
                        <li class="active"><a href="creditcardController?operation=edit&userId=<%=session.getAttribute("sessionUserId")%>">Editar Medios de Pago</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) { %>
                        <li class="active"><a href="indexNew.jsp">Inicio</a></li>
                        <li class="active"><a href="userController?operation=add">Crear Cuenta</a></li>
                            <%}%>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <%if (session.getAttribute("sessionAdminId") != null || (session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li><a href="administratorController?operation=view&adminId=<%=session.getAttribute("sessionAdminId")%>">
                                <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionAdminPname")%></a></li>
                        <li><a href="adminlogoutController"><span class="glyphicon glyphicon-log-out"></span>Admin Logout</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)) {%>
                        <li><a href="userController?operation=view&userId=<%=session.getAttribute("sessionUserId")%>">
                                <span class="glyphicon glyphicon-user"></span><%=session.getAttribute("sessionUserPname")%></a></li>
                        <li><a href="logoutController"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                            <%}
                                if (session.getAttribute("sessionUserId") == null && session.getAttribute("sessionAdminId") == null) {%>
                        <li><a href="loginController?operation=login"><span class="glyphicon glyphicon-log-out">
                                </span>Login</a></li>
                                <% }%>
                    </ul>
                </div>
            </nav>
            <br>
            <div class="container">
                <h2>Login de Administradores</h2>
                <br>
                <form method="POST" action="adminloginController" class="form-container">
                    <div class="row">
                        <div class="col-lg-6">
                            <label for="email"><b>Email</b></label>
                            <input placeholder="Email" name="email" id="email" type="email" required>
                        </div>
                        <div class="col-lg-6">
                            <label for="pass"><b>Contrase�a</b></label>
                            <input name="pass" id="pass" type="password" required>
                        </div>
                    </div>   
                    <div class="row">
                        <div class="col-lg-12">
                            <button type="submit" class="btn">Login</button>
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
                                AirAlcala - Alcal&aacute; de Henares, Madrid, Espa�a
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
        </div>
    </body>
</html>