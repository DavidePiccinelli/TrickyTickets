<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html> 
<html>
<head>
<meta charset="ISO-8859-1"> 
<title>Tricky Tickets - Home</title>
</head>
<body>
<!-- In questa JSP sono presenti unicamente lo sfondo e il messaggio di benvenuto, personalizzato nell'header -->

<%@ include file="header.jsp" %>


<!-- Sfondo -->
  <div
          class="p-5 text-center bg-image"
          style="background-image: url('https://img.wallpapersafari.com/desktop/1920/1080/23/23/RYrHwA.jpg');
          height: 805px;">
  
    <div class="mask" style="background-color: rgba(0, 0, 0, 0.2);">
      <div class="d-flex justify-content-center align-items-center h-100">
        <div class="text-white">
          <h1 class="mb-3">Benvenut* su Tricky Tickets, <%=welcomeMsg2%>!</h1> 
        </div>
      </div>
    </div>
  </div>
  
  
  
<%@ include file="footer.jsp" %>
</body>
</html>