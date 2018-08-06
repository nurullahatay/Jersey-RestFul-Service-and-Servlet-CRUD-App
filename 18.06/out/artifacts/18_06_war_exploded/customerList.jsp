<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="dto.CustomerDTO"%>
<%@page import="dto.CustomerPhoneDTO"%>
<%@page import="service.ServiceFacede"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Show Customer</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta charset="UTF-8">



</head>
<body>
	<jsp:include page="/navbar.html" />



	<div class="container">
		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Modal Header</h4>
					</div>
					<div class="modal-body">
						<div class="container ">
							<div class="col-lg-4">
								<div class="page-header">
									<h2>Customer Registration</h2>
								</div>
								<form class="form-top">
									<div class="form-group">
										<label for="text">Name:</label> <input type="text"
											class="form-control" placeholder="Enter Name" value=""
											name="name" id="name">
									</div>
									<div class="form-group">
										<label for="text">Surname:</label> <input type="text"
											class="form-control" placeholder="Enter username"
											name="surname" id="surname">
									</div>
									<div id='TextBoxesGroup'>
										<div id="phones">
											<div class="form-group">
												<label for="text">Phone Number 1:</label> <input type="text"
													class="form-control" placeholder="Enter phone number 1"
													name="phoneNumber[1]" id="textbox1">
											</div>

										</div>
									</div>
									<br> <input type='button' class="btn btn-info btn-xs"
										value='Add Phone' id='addButton'> <input type='button'
										class="btn btn-info btn-xs" value='Remove Phone'
										id='removeButton'> <br> <br>
									<button type="submit" class="btn btn-success btn-md"
										id="postCustomer" data-toggle="modal" data-target="#myModal">Submit</button>
								</form>

							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>

	</div>




	<div class="container">

		<%
			List<CustomerDTO> customerList = new ArrayList();

			try {
				List<CustomerDTO> customerDTOs = ServiceFacede.getInstance().getCustomers();
				customerList = customerDTOs;
			} catch (Exception e) {
				e.printStackTrace();
			}
		%>
		<div class="container">
			<h2>Customer Table</h2>
			<table class="table table-striped">
				<TR>
					<TH>Id</TH>
					<TH>Name</TH>
					<TH>Surname</TH>
					<TH>Phone 1</TH>
					<TH>Phone 2</TH>
					<TH>Update</TH>
					<TH>Delete</TH>

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
					<td><button type=button class="btn btn-info update"
							value=update name=update id="update" data-toggle="modal"
							data-target="#myModal">Update</button></td>
					<td><a class="btn btn-danger"
						href="/18.06/delete?customerId=<%=customer.getId()%>">Delete</a></td>

				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
</body>
</html>