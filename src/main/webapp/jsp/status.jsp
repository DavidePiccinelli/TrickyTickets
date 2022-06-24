
<%@page import="polimi.provafinale.trickytickets.util.HTMLUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.StatusCtl"%> 
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
<title>Stato ticket</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp, 
dopodiche consente la selezione da menu a tendina
del nuovo stato in cui porre il ticket e aggiorna il ticket tramite post col comando Save
  -->

	<%@ include file="header.jsp"%>
	<div class="container">
		<hr>
		<br>
		<div class="row">
			<div class="col-1"></div>
			<div class="col-6">
				<h3>Stato ticket</h3> 
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b>
				<hr>
				<form method="post" action="<%=ViewsCtls.STATUS_CTL%>">

					<jsp:useBean id="bean" class="polimi.provafinale.trickytickets.bean.TicketBean"
						scope="request"></jsp:useBean> 
					<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=bean.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">					
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Stato</label>
						<%=HTMLUtility.getList("status", String.valueOf(bean.getStatus()), StatusCtl.statusMap()) %>
						<font
							color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</div>


					
					<!-- Bottoni invio -->
					<input type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=StatusCtl.OP_SAVE%>">&nbsp;&nbsp; <input
						type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=StatusCtl.OP_RESET%>">
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
</body>
</html>