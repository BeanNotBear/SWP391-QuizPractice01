<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Registration</title>
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/popup.css">
        <link rel="stylesheet" href="css/myRegister.css">
        <!-- Bootstrap file link  -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            .footer {
                background: #f8f9fa;
                text-align: center;
                position: fixed;
                bottom: 0;
                width: 100%;
                height: 15%;
                padding-right: 12%;
                padding-top: -110px;
            }
        </style>
    </head>
    <body>
        <%@ include file="/layout/header.jsp" %>
        <%@ include file="/layout/searchMyRegister.jsp" %>

        <section class="my-register">
            <h1 class="heading text-center">Khóa học đã đăng ký</h1>
            <div class="container">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Subject</th>
                            <th>Register Time</th>
                            <th>Package</th>
                            <th>Total Cost</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${listRegister}">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.subjectName}</td>
                                <td>${item.registerTime}</td>
                                <td>${item.packageName}</td>
                                <td>${item.totalCost}</td>
                                <td>${item.status}</td>
                                <td>
                                    <c:if test="${item.status == 'pending'}">
                                        <a href="#" onclick="deleteRegister(${item.id})"><i class="fas fa-trash-alt text-danger"></i></a>
                                        </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${currentPage > 1}">
                                <li>
                                    <a href="myRegister?page=${currentPage - 1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:when>
                        </c:choose>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <c:choose>
                                <c:when test="${i == currentPage}">
                                    <li class="active"><a href="#">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li><a href="myRegister?page=${i}">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <li>
                                    <a href="myRegister?page=${currentPage + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:when>
                        </c:choose>
                    </ul>
                </nav>
            </div>
        </section>
        <br/>
        <%@ include file="/layout/footer.jsp" %>
        <!-- side bar có thể thu nhỏ khi màn hình nhỏ  -->
        <script src="js/script.js"></script>

        <script>
                                            // Function to handle delete action using Ajax
                                            function deleteRegister(id) {
                                                if (confirm("Are you sure you want to delete this registration?")) {
                                                    $.ajax({
                                                        type: "GET",
                                                        url: "deleteRegister?id=" + id,
                                                        success: function (data) {
                                                            window.location.href = "myRegister?page=${currentPage}";
                                                        },
                                                        error: function (xhr, status, error) {
                                                            console.error(xhr.responseText);
                                                            // Handle error
                                                        }
                                                    });
                                                }
                                            }
        </script>
    </body>
</html>