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
            <script>
                function validation() {
                    if(document.getElementById('email').value == ''){
                        alert('Introduzca un email');
                        return false;
                    }
                    if(document.getElementById('pass').value == '') {
                        alert('Introduzca una contraseña');
                        return false;
                    }return true;
                }
            </script>
    </head>
    <body>
<%
    if(session.getAttribute("sessionAdminId") != null ){
        response.sendRedirect("userController?operation=list");
    }
    if(session.getAttribute("sessionUserId") != null && !(session.getAttribute("sessionUserId") != null && session.getAttribute("sessionAdminId") != null)){
         response.sendRedirect("flightController?operation=search");
    }
%>
        <div id="wrapper">
            <header>
                <div class="container">
                    <h1>AirAlcala</h1>
                </div>
            </header>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">AirAlcalá</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.html">Inicio</a></li>
                    <li class="active"><a href="userController?operation=add">Crear Cuenta</a></li>       
                </ul>
              </div>
            </nav>
            <br>
            <div class="container">
            <h2>Login</h2>
            <br>
            <form method="POST" action="loginController" class="form-container" onsubmit="return validation()">
            <div class="row">
                <div class="col-lg-6">
                <label for="email"><b>Email</b></label>
                <input placeholder="Email" name="email" id="email" type="email">
                </div>
                <div class="col-lg-6">
                <label for="pass"><b>Contraseña</b></label>
                <input name="pass" id="pass" type="password">
                </div>
            </div>   
            <div class="row">
                <div class="col-lg-12">
                <button type="submit" class="btn">Login</button>
                </div>
            </div>
            </form>
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
                            <li><a href="adminloginController">Login Administrador</a></li>
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