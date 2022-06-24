
<%@page import="java.util.List"%>
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%>
<%@page import="polimi.provafinale.trickytickets.bean.CategoryBean"%> 
<%@page import="polimi.provafinale.trickytickets.ctl.TicketCtl"%> 
<%@page import="polimi.provafinale.trickytickets.util.HTMLUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.CategoryCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.DataUtility"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Apri ticket</title>
</head>
<body>

<!--La pagina innanzitutto ottiene i dati per il timestamp, poi va a creare un nuovo ticket, la categoria è selezionata da
menu a tendina -->


	<%@ include file="header.jsp"%>
		<hr>
		<br>
		<div class="row">
			<div class="col-1"></div> 
			<div class="col-6"> 
				<h3>Apri ticket</h3>
				
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b>
				<hr>
				<form method="post" action="<%=ViewsCtls.TICKET_CTL%>">

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
		
					<%// @SuppressWarnings("unchecked")
					List<?> list=(List<?>)request.getAttribute("categoryList"); %>
			
					<!-- input -->
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Categoria</label>  
							<%=HTMLUtility.getList("categoryId",String.valueOf(bean.getCategoryId()), list) %>
						 <font
							color="red"><%=ServletUtility.getErrorMessage("categoryId", request)%></font>
					</div>
					
					<!-- Invio input -->
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Titolo</label>
						<input type="text" 
							placeholder="Titolo..." class="form-control bd" name="title"
							value="<%=DataUtility.getStringData(bean.getTitle())%>"> <font
							color="red"><%=ServletUtility.getErrorMessage("title", request)%></font>
					</div>
					
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Descrizione</label>
						<textarea rows="4" cols="4"
							placeholder="Descrizione..." class="form-control bd" name="description"
							><%=DataUtility.getStringData(bean.getDescription())%></textarea> <font
							color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
					</div>

					
					<!-- Submit button -->
					<input type="submit" class="btn btn-success  mb-4" name="operation"
						value="<%=TicketCtl.OP_SAVE%>">&nbsp;&nbsp; <input
						type="submit" class="btn btn-warning  mb-4" name="operation"
						value="<%=TicketCtl.OP_RESET%>">
					
						
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	

	<%@ include file="footer.jsp"%>
</body>
</html>