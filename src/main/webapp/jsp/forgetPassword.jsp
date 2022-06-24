<%@page import="polimi.provafinale.trickytickets.ctl.ForgetPasswordCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head> 
<meta charset="ISO-8859-1">
<title>Password dimenticata</title>
</head>
<body>

<!-- Pagina con form in cui inserire lo username per il recupero della password-->


	<%@ include file="header.jsp"%>
	<div class="container">
		<hr>
		<br> 
		<div class="row">
			<div class="col-1"></div> 
			<div class="col-6">
				<h3>Password dimenticata</h3>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b>
				<hr>
				<form method="post" action="<%=ViewsCtls.FORGET_PASSWORD_CTL%>">

					

					<!-- Inserimento username per recupero -->
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Username</label>
						<input type="text" 
							placeholder="Inserisci username..." class="form-control bd" name="userName"
							value="<%=ServletUtility.getParameter("userName", request)%>"> <font
							color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
					</div>
					
					

					
					<!-- Submit button -->
					
					<input type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=ForgetPasswordCtl.OP_GO%>">
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>