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
<title>Elenco utenti</title>
<link href="<%=ViewsCtls.APP_CONTEXT%>/css/login.css" rel="stylesheet">
</head>
<body>

<!--La pagina visualizza l'elenco navigabile degli utenti con paginazione. Deve essere accessibile solo ai fornitori che però
non possono vedere altri fornitori, solo utenti-->

	<%@ include file="header.jsp"%> 
	
	
	<hr>
	<br>
	<form method="post" action="<%=ViewsCtls.USER_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header" 
				style="background-color: #00061df7; color: white;">Elenco utenti</h5>
			<div class="card-body">
				<div class="row g-3">

					<div class="col"> 
						<input type="text" placeholder="Ricerca per nome..."
							name="name" class="form-control"
							value="<%=ServletUtility.getParameter("name", request)%>">
					</div>

					<div class="col">
						<input type="text" placeholder="Ricerca per email..."
							name="email" class="form-control"
							value="<%=ServletUtility.getParameter("email", request)%>">
					</div>

					<div class="col">
						<input type="submit" class="btn  btn-outline-primary"
							name="operation" value="Cerca"></input> oppure <input type="submit"
							class="btn  btn-outline-secondary" name="operation"
							value="Ripristina">
					</div>
				</div>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
					
							<th scope="col">ID</th>
							<th scope="col">Nome</th>
							<th scope="col">Email</th>
							<th scope="col">Telefono</th>
							<th scope="col">Settore aziendale</th>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						UserBean bean = null;
						@SuppressWarnings("unchecked")
						List<UserBean> list = ServletUtility.getList(request);
						Iterator<UserBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
							
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getName()%></td>
							<td scope="row"><%=bean.getEmail()%></td>
							<td scope="row"><%=bean.getContactNo()%></td>
							<td scope="row"><%=bean.getBusinessArea()%></td>
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
								class="page-link " <%=(pageNo == 1) ? "disabled" : ""%> value="Precedente"></li>
							
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