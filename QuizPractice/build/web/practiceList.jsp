<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>App</title>

        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/popup.css">

        <!-- Bootstrap file link  -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <!-- Custom CSS to make the footer fixed -->
        <style>
            body {
                padding: 0;
                margin: 0;
            }
            #topTable {
                margin-bottom: 0.5%;
            }
            .practiceList {
                margin-bottom: 10%;
            }
            nav {
                margin-top: -25px;
            }
        </style>
        
        
        <!-- JavaScript to submit form on combobox change -->
        <script>
            $(document).ready(function() {
                $('#subjectSelect').change(function() {
                    $('#subjectForm').submit();
                });
            });
        </script>


    </head>

    <body>
        <%@include file="/layout/header.jsp"%>

        <section class="practiceList">
            <h1 class="heading text-center">Practice List</h1>

            <div id="topTable" class="container">
                <div class="row">
                    <div class="col-md-3">
                        <form id="subjectForm" method="post" action="practiceList">
                            <select id="subjectSelect" name="subject" class="form-control">
                                <option selected disabled>Choose Subject</option>
                                <c:forEach var="subject" items="${subjects}">
                                    <option value="${subject.toLowerCase()}">${subject}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </div>
                    <div class="col-md-5"></div>
                    <div class="col-md-2">
                        <a href="newPractice" class="btn btn-primary">New Practice</a>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary">Simulation Exam</button>
                    </div>
                </div>
            </div>

            <div class="container">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Subject Name</th>
                            <th>Date Taken</th>
                            <th>Question</th>
                            <th>Correct (%)</th>
                            <th>Duration</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Assume that 'practices' is a list of practice objects passed from the back-end -->
                        <c:forEach var="practice" items="${practices}">
                            <tr>
                                <td>${practice.subjectName}</td>
                                <td>${practice.dateTaken}</td>
                                <td>${practice.numberQuestion}</td>
                                <td>${practice.numberCorrect/practice.numberQuestion*100}%</td>
                                <td>${practice.duration}:00</td>
                                <td><a href="#">View Detail</a></td>
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
                                    <a href="practiceList?page=${currentPage - 1}" aria-label="Previous">
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
                                    <li><a href="praticeList?page=${i}">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <li>
                                    <a href="praticeList?page=${currentPage + 1}" aria-label="Next">
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

        <%@include file="/layout/footer.jsp" %>

        <!-- side bar có thể thu nhỏ khi màn hình nhỏ  -->
        <script src="js/script.js"></script>
    </body>
</html>