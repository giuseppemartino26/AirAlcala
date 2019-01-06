<%-- 
    Document   : LoginUser
    Created on : 06-ene-2019, 17:59:53
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Air Alcala</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
* {
  margin: 0px;
  padding: 0px;
}
body {
  font-size: 120%;
  background: #F8F8FF;
}

h1{
font-size: 50px;
font-family: Arial, Helvetica, sans-serif;
color: DarkBlue;
margin-left: 40px;
margin-top: 25px
}

.header {
  width: 30%;
  margin: 50px auto 0px;
  color: white;
  background: darkblue;
  text-align: center;
  border: 1px solid #B0C4DE;
  font-family: Arial, Helvetica, sans-serif;
  border-bottom: none;
  border-radius: 10px 10px 0px 0px;
  padding: 20px;
}
form, .content {
  width: 30%;
  margin: 0px auto;
  padding: 20px;
  border: 1px solid #B0C4DE;
  background: white;
  border-radius: 0px 0px 10px 10px;
}
.input-group {
  margin: 10px 0px 10px 0px;
}
.input-group label {
  display: block;
  text-align: left;
  margin: 3px;
}
.input-group input {
  height: 30px;
  width: 93%;
  padding: 5px 10px;
  font-size: 16px;
  border-radius: 5px;
  border: 1px solid gray;
}
.btn {
  padding: 10px;
  font-size: 15px;
  color: white;
  background: darkblue;
  border: none;
  border-radius: 5px;
}

</style>

    </head>
    
<body>
    <div id="wrapper">
    <header>
        <div class="container">
            <h1>AirAlcala</h1>
        </div>
    </header>
    
  <div class="header">
  	<h2> Login</h2>
  </div>
	 
  <form action="/airAlcala/Servelt" method="POST" onsubmit="return validacion()">
  	
  	<div class="input-group">
  		<label>Username</label>
  		<input type="text" id="User" name="username" >
  	</div>
  	<div class="input-group">
  		<label>Password</label>
  		<input type="password" id="pass" name="password">
  	</div>
  	<div class="input-group">
  		<button type="submit" class="btn" name="login_user">Login</button>
  	</div>
      
        <div class="input-group">
  		<button type="submit" class="btn" name="sign_in">Sign-in</button>
  	</div>
  	
  </form>
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
