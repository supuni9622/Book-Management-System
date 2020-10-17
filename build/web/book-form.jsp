<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Books CRUD</title>
        <link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
    </head>
    <body>
        <header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: lightseagreen">
			<div>
				<a href="#" class="navbar-brand"> Book Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Books</a></li>
			</ul>
		</nav>
	</header>                               
       <br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${books != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${books == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${books != null}">
                                                    Edit Book
                                                </c:if>
						<c:if test="${books == null}">
                                                    Add New Book
                                                </c:if>
					</h2>
				</caption>

				<c:if test="${books != null}">
					<input type="hidden" name="id" value="<c:out value='${books.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Book Name</label> <input type="text"
						value="<c:out value='${books.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Book Price</label> <input type="text"
						value="<c:out value='${books.price}' />" class="form-control"
						name="price">
				</fieldset>

				<fieldset class="form-group">
					<label>Book Auhor</label> <input type="text"
						value="<c:out value='${books.author}' />" class="form-control"
						name="author">
				</fieldset>

				<button type="submit" class="btn btn-info">Save</button>
				</form>
			</div>
		</div>
	</div>                                 
    </body>
</html>
