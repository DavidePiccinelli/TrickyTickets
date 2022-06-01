<%@page import="polimi.provafinale.trickytickets.ctl.MyProfileCtl"%>
<%@page import="polimi.provafinale.trickytickets.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%> 
<%@page import="polimi.provafinale.trickytickets.util.DataUtility"%>
<%@page import="polimi.provafinale.trickytickets.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profilo utente</title>
</head>
<body>
	<%@ include file="header.jsp"%>
		<hr>
		<br>
		<div class="row">
			<div class="col-1"></div>
			<div class="col-6">
				<h3>Profilo utente</h3>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b> 
				<hr>
				<form method="post" action="<%=HTSView.MY_PROFILE_CTL%>"> 

					<jsp:useBean id="bean" class="polimi.provafinale.trickytickets.bean.UserBean"
						scope="request"></jsp:useBean>

					<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=bean.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

					<!-- Email input -->
					<div class="form-outline mb-4">
					 <label
							class="form-label" >Nome</label>
						<input type="text" 
							placeholder="Nome..." class="form-control bd" name="Nome"
							value="<%=DataUtility.getStringData(bean.getName())%>"> <font
							color="red"><%=ServletUtility.getErrorMessage("Nome", request)%></font>
					</div>
					
					
					
					

					<div class="row mb-4">
						<div class="col">
						
							<div class="form-outline">
							<label class="form-label" for="form3Example3">Username</label>
								<input type="text" id="form3Example3"
									placeholder="Username..." class="form-control bd"
									name="userName" 
									value="<%=DataUtility.getStringData(bean.getUserName())%>">
								
								<font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
							</div>
						</div>
						
					</div>

					<div class="row mb-4">
						<div class="col">
							<div class="form-outline">
							<label class="form-label" >Email</label> 
								<input type="text" 
									placeholder="Indirizzo email..." class="form-control bd"
									name="Email" 
									value="<%=DataUtility.getStringData(bean.getEmail())%>">
								<font
									color="red"><%=ServletUtility.getErrorMessage("Email", request)%></font>
							</div>
						</div>

						<div class="col">
							<div class="form-outline">
							<label class="form-label" >Telefono</label>
								<input type="text" 
									placeholder="Telefono..." class="form-control bd"
									name="Telefono" 
									value="<%=DataUtility.getStringData(bean.getContactNo())%>">
								
								<font color="red"><%=ServletUtility.getErrorMessage("Telefono", request)%></font>
							</div>
						</div>
					</div>
					
					<% 
						
						HashMap<String,String> businessAreaMap= new HashMap<String,String>();
									businessAreaMap.put("Commerciale","Commerciale");
									businessAreaMap.put("Amministrativo","Amministrativo");
									businessAreaMap.put("Logistico","Logistico");
									businessAreaMap.put("IT","IT");
					
					%>

					<div class="row mb-4">
						

						<div class="col">
							<div class="form-outline">
							<label class="form-label" >Settore aziendale</label> 
							<%=HTMLUtility.getList("Area",String.valueOf(bean.getBusinessArea()), businessAreaMap) %>
								<font
									color="red"><%=ServletUtility.getErrorMessage("Area", request)%></font>
							</div>
						</div>
					</div>

					

					<!-- Submit button -->
					<input type="submit" class="btn btn-primary  mb-4" name="operation"
						value="Aggiorna" >
						
						&nbsp;&nbsp; 
						
						<input
						type="submit" class="btn btn-primary  mb-4" name="operation"
						value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>">
				</form>
			</div>
			<div class="col-5"></div>
		</div>
	

	<%@ include file="footer.jsp"%>
</body>
</html>