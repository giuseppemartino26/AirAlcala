<%-- 
    Document   : newjsp
    Created on : 8-gen-2019, 9.40.51
    Author     : Giuseppe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*;" %>
<!DOCTYPE html>

   <html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>PaginaAd</title>   
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


 <style>
           html, body {
              height: 100%;
              background-color: #e6f3ff;
             overflow: auto;
            }


            .form-popup {
  display: none;
  
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}

            .form-container {
                padding: 20px;
                border: 1px solid #B0C4DE;
                background: white;
                border-radius: 0px 0px 10px 10px;
                width: 100%;
            }

            .form-container input[type=text], .form-container input[type=number], .form-container input[type=date] {
                width: 100%;
                padding: 15px;
                margin: 5px 0 22px 0;
                border: none;
                background: #f1f1f1;
            }

            /* Set a style for the "envio" button */
            .form-container .btn {
                background-color: #4CAF50;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-bottom:10px;
                opacity: 0.8;
            }
            
             .form-container .btn2 {
                background-color: deepskyblue;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-bottom:10px;
                opacity: 0.8;
            }

                
                /* Add a red background color to the cancel button */
                .form-container .cancel {
                background-color: red;
               }


            /* Add some hover effects to button */
            .form-container .btn:hover, .open-button:hover {
                opacity: 1;
            }

            h2{
                font-size: 50px;
                font-family: Arial, Helvetica, sans-serif;
                color: DarkBlue;
                margin-left: 40px;
                margin-top: 25px
            }
            
            table,td{
                border: 0;  
                text-align: left;
                height: 23px;
                margin-left: 44px;
                width: 1149px;
                padding: 2px;
                border-collapse: separate;
                border-spacing: 2px;



            }
        </style>

    </head>
    <body>
        
                
            }
        <br>

        <h2>P&aacute;gina de gesti&oacute;n de los vuelos</h2>

        <br>
        
       <table>

            <tbody>
                <tr>
                    <td > <input name="UpdateFlight" onclick="show()" id="Anadirvuelo" value="Anadirvuelo" type="radio">
                        <label for="Anadirvuelo">A&ntilde;adir vuelo</label> </td>

                    <td><input name="UpdateFlight" onclick="show2()" id="Modificarvuelo" value="Modificarvuelo" type="radio">
			 <label for="Modificarvuelo">Modificar vuelo</label><br> </td>

                    <td><input name="UpdateFlight" onclick="show3()" id="Borrarvuelo" value="Borrarvuelo" type="radio">
                        <label for="Borrarvuelo">Borrar vuelo</label><br>
                    </td>
                </tr>
            </tbody>
        </table>

        

            <div class="form-popup" id="myForm"> 
                <form action="/servlet" class="form-container">
                    
		<label for="Locator"><b>Localizador</b></label>
                <input placeholder="Insertar Localizador" name="Locator" id="Locator" type="text"> 

		<label>Ruta:</label>
                <select class="form-control"  style="width: 250px;">
                <%
                try
                {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                    Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala","root","root");
                    Statement stm=conn.createStatement();
                    ResultSet rs=stm.executeQuery("select * from routes");
                    while(rs.next())
                    {
                        %>
                        <option><%=rs.getString("origin")+" -> "+rs.getString("destination")%></option>
                        <%
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    out.println("Error "+ex);
                }
            
                %>     
        </select>

		<label for="date"><b>Fecha</b></label> 
		<input  name="date" id="date" type="date"> 

		<label for="Interval"><b>Intervalo</b></label> 
		<input placeholder="Insertar Intervalo" id="Interval" name="Interval" type="text"> 

		<label for="Places"><b>Plazas disponibles</b></label> 
		<input placeholder="Insertar numero de las plazas disponibles" name="Places" id="Places" type="number"> 

		<button type="submit" class="btn">Env&iacute;o</button> 
                 <button type="button" class="btn cancel" onclick="closeForm()">Cerrar</button>
                </form>
            </div>
        
        <div class="form-popup" id="myForm2"> 
                <form action="/servlet" class="form-container">
                    
		
           
                    <label>Vuelo:</label>
        <select class="form-control"  style="width: 250px;">  
            <option>Eliges un vuelo</option>
             <%
               try
               {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                    Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala","root","root");
                    Statement stm=conn.createStatement();
                    ResultSet rs=stm.executeQuery("SELECT * FROM flights");
                    while(rs.next())
                    {
                        %>
                        <option><%=rs.getString("locator")%></option>
                        <%
                    }
               }
               catch(Exception ex)
               {
                 ex.printStackTrace();
                 out.println("Error"+ex.getMessage());
               }
            
            %>     
        </select>
             
        <label>Ruta:</label>
        <select class="form-control"  style="width: 250px;">
             <option>Elige la ruta</option>
                <%
                try
                {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                    Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala","root","root");
                    Statement stm=conn.createStatement();
                    ResultSet rs=stm.executeQuery("select * from routes");
                    while(rs.next())
                    {
                        %>
                        <option><%=rs.getString("origin")+" -> "+rs.getString("destination")%></option>
                        <%
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    out.println("Error "+ex);
                }
            
                %>     
        </select>
                
		<label for="date"><b>Fecha</b></label> 
		<input  name="date" id="date" type="date"> 

		<label for="Interval"><b>Intervalo</b></label> 
		<input placeholder="Insertar Intervalo" id="Interval" name="Interval" type="text"> 

		<label for="Places"><b>Plazas disponibles</b></label> 
		<input placeholder="Insertar numero de las plazas disponibles" name="Places" id="Places" type="number"> 

		<button type="submit" class="btn">Env&iacute;o</button> 
                 <button type="button" class="btn cancel" onclick="closeForm()">Cerrar</button>
                </form>

            </div>
        
        
         <div class="form-popup" id="myForm3"> 
                <form action="/servlet" class="form-container">
                    
                     <label>Vuelo:</label>
        <select class="form-control"  style="width: 250px;">
             <option>Eliges el vuelo</option>
             <%
               try
               {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                    Connection conn=DriverManager.getConnection("jdbc:derby://localhost:1527/airAlcala","root","root");
                    Statement stm=conn.createStatement();
                    ResultSet rs=stm.executeQuery("SELECT * FROM flights");
                    while(rs.next())
                    {
                        %>
                        <option><%=rs.getString("locator")%></option>
                        <%
                    }
               }
               catch(Exception ex)
               {
                 ex.printStackTrace();
                 out.println("Error"+ex.getMessage());
               }
            
            %>       
        </select>
                    
        <br>
        <br>

		<button type="submit" class="btn2">Borrar vuelo</button> 
                 <button type="button" class="btn cancel" onclick="closeForm()">Cerrar</button>
                </form>
            </div>
        
          <script>
            function show(){
                document.getElementById("myForm").style.display = "block";
                document.getElementById("myForm2").style.display = "none"; 
                 document.getElementById("myForm3").style.display = "none";
                }
                
                 function show2(){
                document.getElementById("myForm2").style.display = "block";
                document.getElementById("myForm").style.display = "none";
                 document.getElementById("myForm3").style.display = "none";
                }
                
                function show3(){
                document.getElementById("myForm3").style.display = "block";
                document.getElementById("myForm").style.display = "none";
                 document.getElementById("myForm2").style.display = "none";
                }
                
		function closeForm() {
		  document.getElementById("myForm").style.display = "none";
}

        </script>
        
    </body>
</html>