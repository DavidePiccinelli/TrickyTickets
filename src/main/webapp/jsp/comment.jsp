<%@page import="polimi.provafinale.trickytickets.ctl.CommentCtl"%>
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
<title>Commento</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp dopodiche consente di compilare la form
per la creazione del commento da associare al ticket precedentemente selezionato, consente poi di salvare il ticket
o ripristinare il campo -->


	<%@ include file="header.jsp"%> 
		<hr>
		<br> 
		<div class="row">
			<div class="col-1"></div>
			<div class="col-6">
				<h3>Commento</h3>
				
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></b> 
				<b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font></b>				
				<hr>
				<form method="post" action="<%=ViewsCtls.COMMENT_CTL%>">

					<jsp:useBean id="bean" class="polimi.provafinale.trickytickets.bean.CommentBean"
						scope="request"></jsp:useBean>
						
						<!-- Tracciamento utenti e timestamp modifiche -->
						
					<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=bean.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

					
					
					<div class="form-outline mb-4">
					
						<textarea rows="4" cols="4"
							placeholder="Commento..." class="form-control bd" name="comment"></textarea> 
							<font color="red"><%=ServletUtility.getErrorMessage("comment", request)%></font>
					</div>


					
					<!-- Bottone di invio e di reset -->
					
					<input type="submit" class="btn btn-success  mb-4" name="operation"
						value="<%=CommentCtl.OP_SAVE%>">
						&nbsp;&nbsp; 
						<input
						type="submit" class="btn btn-warning  mb-4" name="operation"
						value="<%=CommentCtl.OP_RESET%>">	
						
				</form>
			</div>
			<div class="col-5"></div>
		</div>


	<%@ include file="footer.jsp"%>
</body>
</html>