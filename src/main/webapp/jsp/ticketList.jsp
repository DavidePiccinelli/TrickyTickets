
<%@page import="polimi.provafinale.trickytickets.bean.TicketBean"%>
<%@page import="polimi.provafinale.trickytickets.bean.CategoryBean"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@page import="java.util.Iterator"%> 
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco ticket</title>
<link href="<%=HTSView.APP_CONTEXT%>/css/login.css" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp"%> 
	<hr>
	<br>
	<form method="post" action="<%=HTSView.TICKET_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Elenco ticket</h5>
			<div class="card-body">
				<div class="row g-3">  

					<div class="col">
						<input type="text" placeholder="Ricerca per numero ticket..."
							name="ticketNo" class="form-control"
							value="<%=ServletUtility.getParameter("ticketNo", request)%>">
					</div>
					<div class="col">
						<input type="text" placeholder="Ricerca per titolo..."
							name="title" class="form-control"
							value="<%=ServletUtility.getParameter("title", request)%>">
					</div>

					<div class="col">
						<input type="text" placeholder="Ricerca per nome categoria..."
							name="categoryName" class="form-control"
							value="<%=ServletUtility.getParameter("categoryName", request)%>">
					</div>

					<div class="col">
						<input type="submit" class="btn  btn-outline-primary"
							name="operation" value="Cerca"></input> oppure <input type="submit"
							class="btn  btn-outline-secondary" name="operation" value="Ripristina">
					</div>
				</div>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
							<th scope="col">Numero ticket</th>
							<th scope="col">Categoria</th>
							<th scope="col">Titolo</th>
							<th scope="col">Aperto da</th>
							<th scope="col">Data</th>
							<th scope="col">Stato</th>
							<th scope="col">Descrizione</th>
							<th scope="col">Gestisci</th>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						TicketBean bean = null;
						@SuppressWarnings("unchecked")
						List<TicketBean> list = ServletUtility.getList(request);
						Iterator<TicketBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
				
							<td scope="row"><%=bean.getTicketNo()%></td>
							<td scope="row"><%=bean.getCategoryName()%></td>
							<td scope="row"><%=bean.getTitle()%></td>
							<td scope="row"><%=bean.getUserName()%></td>
							<td scope="row"><%=bean.getDate()%></td>
							<td scope="row"><%=bean.getStatus()%></td>
							<td scope="row"><%=bean.getDescription()%></td>
							<%if(userBean.getRoleId()==2){%>
							<td>
							<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_CTL%>?tId=<%=bean.getId()%>">aggiungi commento</a>
							<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_LIST_CTL%>?tId=<%=bean.getId()%>">vedi commenti</a>
							</td>
							<%} %>	
							
							<%if(userBean.getRoleId()==1){%>
							<td><a class="btn btn-sm btn-info" href="<%=HTSView.STATUS_CTL%>?tId=<%=bean.getId()%>">modifica stato
									</a>
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_CTL%>?tId=<%=bean.getId()%>">aggiungi commento
									</a>
									
									<a class="btn btn-sm btn-info" href="<%=HTSView.COMMENT_LIST_CTL%>?tId=<%=bean.getId()%>">vedi commenti
									</a>
									</td>
							<%} %>		
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="clearfix">
					
					<nav aria-label="Page navigation example float-end">
						<ul class="pagination justify-content-end" style="font-size: 13px">
							<li class="page-item"><input type="submit" name="operation"
								class="page-link " <%=(pageNo == 1) ? "disabled" : ""%>
								value="Precedente"></li>

							<li class="page-item"><input type="submit" name="operation"
								class="page-link "
								<%=((list.size() < pageSize) || size == pageNo * pageSize) ? "disabled" : ""%>
								value="Successiva"></li>
						</ul>
					</nav>
				</div>
				<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
					type="hidden" name="pageSize" value="<%=pageSize%>">

			</div>

		</div>
	</form>

	<%@ include file="footer.jsp"%>
	
	
</body>
</html>