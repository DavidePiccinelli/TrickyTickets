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
<title>Elenco categorie</title>
</head>

<body>
	<%@ include file="header.jsp"%>
	<hr>
	<br>
	<form method="post" action="<%=HTSView.CATEGORY_LIST_CTL%>">

		<!-- Area per la ricerca nelle categorie con pulsante Reset -->

		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Elenco
				categorie</h5>
			<div class="card-body">
				<div class="row g-3">
					<div class="col">
						<input type="text" placeholder="Ricerca per nome..." name="name"
							class="form-control"
							value="<%=ServletUtility.getParameter("name", request)%>">
					</div>

					<div class="col">
						<input type="submit" class="btn  btn-outline-primary"
							name="operation" value="Cerca"></input> oppure <input
							type="submit" class="btn  btn-outline-secondary" name="operation"
							value="Ripristina">
					</div>
				</div>

				<!-- Area per messaggi di errore/successo -->

				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></b>
				<b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%></font></b>

				<br>

				<!-- Elenco dinamico delle categorie presenti e porzione dedicata al Paging -->

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
							<th scope="col">ID</th>
							<th scope="col">Nome</th>
							<th scope="col">Descrizione</th>
						</tr>
					</thead>
					<tbody>

						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						CategoryBean bean = null;
						@SuppressWarnings("unchecked")
						List<CategoryBean> list = ServletUtility.getList(request); 
						Iterator<CategoryBean> iterator = list.iterator();
						while (iterator.hasNext()) {bean = iterator.next();	%>

						<tr>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getName()%></td>
							<td scope="row"><%=bean.getDescription()%></td>
						</tr>

						<%}%>

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