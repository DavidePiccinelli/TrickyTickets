<%@page import="polimi.provafinale.trickytickets.ctl.LoginCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="polimi.provafinale.trickytickets.util.DataUtility"%>  
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp poi consente di inserire le credenziali per l'accesso da inviare tramite post
con comando login-->

	<%@ include file="header.jsp"%>
	<hr>
	<br>	
			<div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
				
				<form method="post" action="<%=ViewsCtls.LOGIN_CTL%>">
					<jsp:useBean id="bean"
								class="polimi.provafinale.trickytickets.bean.UserBean"
								scope="request"></jsp:useBean> 
								
								
								<% 
							String uri = (String) request.getAttribute("uri");
							%>

							<input type="hidden" name="uri" value="<%=uri%>"> <input
								type="hidden" name="id" value="<%=bean.getId()%>"> <input
								type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy"
								value="<%=bean.getModifiedBy()%>"> <input type="hidden"
								name="createdDatetime"
								value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
								
				
			
					<b><font color="red" size="2px"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green" size="2px"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>

					<!-- Form credenziali -->
					<div class="form-outline mb-4">
					<h3>Inserisci le tue credenziali</h3>
					<label
							class="form-label" for="form3Example3">Username</label>
						<input type="text" id="form3Example3" name="userName"
							class="form-control bd"
							placeholder="Enter UserName" value="<%=DataUtility.getStringData(bean.getUserName())%>" /> 
							<font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
					</div>

					<!-- Password input -->
					<div class="form-outline mb-3">
					<label class="form-label" for="form3Example4">Password</label>
						<input type="password" id="form3Example4" name="password"
							class="form-control  bd" placeholder="Enter password" value="<%=DataUtility.getStringData(bean.getPassword())%>" />
						
						<font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>

					<div class="d-flex justify-content-between align-items-center">
						<!-- Checkbox -->
						<input type="submit" class="btn btn-primary btn-lg" name="operation"
									value="<%=LoginCtl.OP_SIGN_IN%>"
							style="padding-left: 2.5rem; padding-right: 2.5rem;">
						
					</div>

				

				</form>
			</div>
	
	<br />
	<br />
	<%@ include file="footer.jsp"%>
</body>
</html>