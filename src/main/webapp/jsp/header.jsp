<%@page import="polimi.provafinale.trickytickets.ctl.HTSView"%>
<%@page import="polimi.provafinale.trickytickets.ctl.BaseCtl"%>
<%@page import="polimi.provafinale.trickytickets.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>


<!-- Font Awesome -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet" />
		
<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />	
	
<!-- MDB --> 
<link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.10.0/mdb.min.css" rel="stylesheet" />
	
<!-- MDB -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.10.0/mdb.min.js"> </script>
	
	 

<style type="text/css">
.bd {
	border: 1px solid #2a2fdb !important;
}
</style>
</head>
<body>

	<%
	UserBean userBean = (UserBean) session.getAttribute("user");

	boolean userLoggedIn = userBean != null;

	String welcomeMsg = "";
	String welcomeMsg2 = "";

	if (userLoggedIn) {
		welcomeMsg += userBean.getName() + " (" + userBean.getRoleName() + ")";
		welcomeMsg2 = userBean.getName();
	} else {
		welcomeMsg += "Visitatore";
		welcomeMsg2 += "visitatore";
	}
	%>

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<!-- Container wrapper -->
		<div class="container">
			<!-- Navbar brand -->
			<a class="navbar-brand me-3" > Tricky Tickets
			</a>

			<!-- Toggle button -->
			<button class="navbar-toggler" type="button"
				data-mdb-toggle="collapse" data-mdb-target="#navbarButtonsExample"
				aria-controls="navbarButtonsExample" aria-expanded="false"
				aria-label="Toggle navigation">
				<i class="fas fa-bars"></i>
			</button>

			<!-- Collapsible wrapper -->
			<div class="collapse navbar-collapse" id="navbarButtonsExample">
				<!-- Left links -->
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="<%=HTSView.WELCOME_CTL%>">Home</a></li>

					<%
					if (userLoggedIn) {
					%>

					<%
					if (userBean.getRoleId() == 1) {
					%>

					<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.USER_LIST_CTL%>">Elenco utenti</a></li>

					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-mdb-toggle="dropdown" aria-expanded="false">
							Categorie</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item"
								href="<%=HTSView.CATEGORY_CTL%>">Aggiungi categoria</a></li>
							<li><a class="dropdown-item"
								href="<%=HTSView.CATEGORY_LIST_CTL%>">Vedi categorie</a></li>
						</ul></li>
						
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.TICKET_LIST_CTL%>">Elenco dei ticket</a></li>
						
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.MY_PROFILE_CTL%>">Profilo utente</a></li>
					<%
					}
					%>

					<%
					if (userBean.getRoleId() == 2) {
					%>
				
						
						<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-mdb-toggle="dropdown" aria-expanded="false">Ticket</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item" href="<%=HTSView.TICKET_CTL%>">Apri
									Ticket</a></li>
							<li><a class="dropdown-item"
								href="<%=HTSView.TICKET_LIST_CTL%>">Vedi ticket</a></li>
						</ul></li>
					
						<li class="nav-item"><a class="nav-link"
						href="<%=HTSView.MY_PROFILE_CTL%>">Profilo utente</a></li>
					<%
					}
					}
					%>

					
				</ul>
				<!-- Left links -->

				<div class="d-flex align-items-center">
					<%
					if (!userLoggedIn) {
					%>
					<a href="<%=HTSView.LOGIN_CTL%>" class="btn btn-primary me-3">Accedi</a>
					<a href="<%=HTSView.USER_REGISTRATION_CTL%>"
						class="btn btn-primary me-3"><%=BaseCtl.OP_SIGN_UP%></a>						
					<a href="<%=HTSView.FORGET_PASSWORD_CTL%>"
						class="btn btn-primary me-3">Password dimenticata</a>
					<%
					} else {
					%>
					<%
					if (userLoggedIn) {
					%>
					<a href="<%=HTSView.LOGIN_CTL%>?operation=Logout"
						class="btn btn-primary me-3">Esci</a>


					<%
					}
					}
					%>

					<a> <%=welcomeMsg%>
					</a>
				</div>
			</div>
			<!-- Collapsible wrapper -->
		</div>
		<!-- Container wrapper -->
	</nav>
	<!-- Navbar -->

</body>
</html>