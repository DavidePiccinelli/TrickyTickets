<%@page import="polimi.provafinale.trickytickets.bean.CommentBean"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="polimi.provafinale.trickytickets.ctl.ViewsCtls"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head> 
<meta charset="ISO-8859-1">
<title>Lista commenti</title>
</head>
<body>

<!-- Vista dell'elenco dei commenti relativi al ticket selezionato precedentemente, visualizzabile dai fornitori e dal creatore del ticket-->


	<%@ include file="header.jsp"%> 
	<hr>
	<br> 
	<form method="post" action="<%=ViewsCtls.COMMENT_LIST_CTL%>">
		<div class="card"> 
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Lista commenti</h5>
			<div class="card-body">
				
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
							<th scope="col">Numero ticket</th>
							<th scope="col">Titolo</th>
							<th scope="col">Autore</th>
							<th scope="col">Data</th>
							<th scope="col">Commento</th>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						CommentBean bean = null;
						@SuppressWarnings("unchecked")
						List<CommentBean> list = ServletUtility.getList(request);
						Iterator<CommentBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
							<td scope="row"><%=bean.getTicketNo()%></td>
							<td scope="row"><%=bean.getTicketTitle()%></td>
							<td scope="row"><%=bean.getUserName()%></td>
							<td scope="row"><%=bean.getDate()%></td>
							<td scope="row"><%=bean.getComment()%></td> 
						</tr>
						<%
						}
						%>
					</tbody>
				</table> 
<div>
			<a class="btn btn-success  mb-4"  href="<%=ViewsCtls.COMMENT_CTL%>?tId=<%=Integer.parseInt(request.getParameter("tId"))%>">aggiungi commento</a>
					</div>
			</div>									
		</div>
	</form>
	
	<%@ include file="footer.jsp"%>
</body>
</html>