<%@page import="polimi.provafinale.trickytickets.ctl.ChangePasswordCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.DataUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cambia password</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp poi va ad aggiornare la password dell'utente 
tramite la corretta compilazione della form -->

	<%@ include file="header.jsp"%>
	<div class="container">
		<hr>
		<br>
		<div class="row">
			<div class="col-1"></div>
			<div class="col-6">
				<h3>Cambia password</h3>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b>
				<hr>
				<form method="post" action="<%=ViewsCtls.CHANGE_PASSWORD_CTL%>">

					<!-- Tracciamento utenti e timestamp modifiche -->

					<jsp:useBean id="bean"
						class="polimi.provafinale.trickytickets.bean.UserBean"
						scope="request"></jsp:useBean>

					<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=bean.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

					<!-- Area inserimento dati -->

					<div class="form-outline mb-4">
						<label class="form-label">Vecchia password</label> <input
							type="password" placeholder="Inserisci vecchia password..."
							class="form-control bd" name="oldPassword"
							value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "": DataUtility.getString(request.getParameter("oldPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("oldPassword", request)%></font>
					</div>

					<div class="form-outline mb-4">
						<label class="form-label">Nuova password</label> <input
							type="password" placeholder="Inserisci nuova password..."
							class="form-control bd" name="newPassword"
							value=<%=DataUtility.getString(
		request.getParameter("newPassword") == null ? "" : DataUtility.getString(request.getParameter("newPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("newPassword", request)%></font>
					</div>

					<div class="form-outline mb-4">
						<label class="form-label">Conferma password</label> <input
							type="password" placeholder="Conferma password..."
							class="form-control bd" name="confirmPassword"
							value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" : DataUtility.getString(request.getParameter("confirmPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
					</div>


					<!-- Bottone di invio dati -->
					
					<input type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>">
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>