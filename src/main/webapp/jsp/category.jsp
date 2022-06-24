<%@page import="polimi.provafinale.trickytickets.ctl.CategoryCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.DataUtility"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%> 
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Categoria</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp poi consente di creare una nuova categoria riempiendo la form
inserendo quindi titolo e descrizione della categoria. Alla pagina possono accedere solo i fornitori -->

	<%@ include file="header.jsp"%>
	<hr>
	<br>
	<div class="row">
		<div class="col-1"></div>
		<div class="col-6">
			<h3>Crea categoria</h3>
			<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></b>
			<b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font></b>
			<hr>
			<form method="post" action="<%=ViewsCtls.CATEGORY_CTL%>">
				<!-- Timestamp -->
				<jsp:useBean id="bean"
					class="polimi.provafinale.trickytickets.bean.CategoryBean"
					scope="request"></jsp:useBean>
				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				<!-- Inserimento nome e descrizione categoria -->
				<div class="form-outline mb-4">
					<label class="form-label">Nome</label> <input type="text"
						placeholder="Inserisci nome..." class="form-control bd"
						name="name" value="<%=DataUtility.getStringData(bean.getName())%>">
					<font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
				</div>
				<div class="form-outline mb-4">
					<label class="form-label">Descrizione</label>
					<textarea rows="4" cols="4" placeholder="Inserisci descrizione..."
						class="form-control bd" name="description">
						<%=DataUtility.getStringData(bean.getDescription())%></textarea>
					<font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
				</div>
				<!-- Submit / Reset button -->
				<input type="submit" class="btn btn-success  mb-4" name="operation"
					value="<%=CategoryCtl.OP_SAVE%>"> &nbsp;&nbsp; <input
					type="submit" class="btn btn-warning  mb-4" name="operation"
					value="<%=CategoryCtl.OP_RESET%>">
			</form>
		</div>
		<div class="col-5"></div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>