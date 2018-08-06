<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="dto.CustomerDTO"%>
<%@page import="dto.CustomerPhoneDTO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Show Customer</title>
</head>
<body>
	<h1>Customer List</h1>
	<div class="cointeiner">
		<%
			List<CustomerDTO> customerList = (List<CustomerDTO>) request.getAttribute("customerList");
		%>

		<table style="width: 300px">
			<TR>
				<TH>Id</TH>
				<TH>Name</TH>
				<TH>Surname</TH>
				<TH>Phone 1</TH>
				<TH>Phone 2</TH>

			</TR>
			<%
				for (CustomerDTO customer : customerList) {
					//List<CustomerPhoneDTO> customerPhoneList = customer.getCustomerPhoneDTOList();
			%>
			<tr>
				<td><%=customer.getId()%></td>
				<td><%=customer.getName()%></td>
				<td><%=customer.getSurname()%></td>
				<td><%=customer.getCustomerPhoneDTOList().get(0).getPhoneNumber()%></td>
				<td><%=customer.getCustomerPhoneDTOList().get(1).getPhoneNumber()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>

	<div class="container">
		<a href="/18.06/logout" class="btn btn-danger" role="button">Logout</a>
	</div>
</body>
</html>