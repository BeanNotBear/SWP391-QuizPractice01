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
            .practiceList {
                margin-bottom: 10%;
            }
            nav {
                margin-top: -25px;
            }
        </style>

        <script>
            $(document).ready(function () {
                var allSubjects = ${listSubject};

                $('#dimension').change(function () {
                    var dimension = $(this).val();
                    if (dimension) {
                        $.ajax({
                            url: 'subjectServlet', // Servlet to get subjects by dimension
                            type: 'GET',
                            data: {dimension: dimension},
                            success: function (data) {
                                $('#subject').empty().append('<option selected disabled>Choose Subject</option>');
                                data.forEach(function (subject) {
                                    $('#subject').append('<option value="' + subject + '">' + subject + '</option>');
                                });
                            }
                        });
                    }
                });

                // Display the initial list of subjects
                $('#subject').empty().append('<option selected disabled>Choose Subject</option>');
                allSubjects.forEach(function (subject) {
                    $('#subject').append('<option value="' + subject + '">' + subject + '</option>');
                });
            });

            $(document).ready(function () {
                $('#subject').change(function () {
                    var selectedSubject = $(this).val();
                    if (selectedSubject) {
                        $.ajax({
                            url: 'lessonServlet', // Đường dẫn tới servlet hoặc endpoint xử lý yêu cầu
                            type: 'GET',
                            data: {subject: selectedSubject},
                            success: function (data) {
                                $('#lesson').empty().append('<option selected disabled>Choose Lesson</option>');
                                data.forEach(function (lesson) {
                                    $('#lesson').append('<option value="' + lesson + '">' + lesson + '</option>');
                                });
                            }
                        });
                    }
                });
            });
        </script>

    </head>

    <body>
        <%@include file="/layout/header.jsp"%>

        <section class="practiceList">
            <h1 class="heading text-center">New Practice</h1>

            <div class="container">
                <form>
                    <div class="form-group">
                        <label for="dimension">Dimension:</label>
                        <select id="dimension" class="form-control">
                            <option selected disabled>Choose Dimension</option>
                            <c:forEach var="dimension" items="${listDimension}">
                                <option value="${dimension}">${dimension}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="subject">Subject:</label>
                        <select id="subject" class="form-control">
                            <option selected disabled>Choose Subject</option>
                            <c:forEach var="subject" items="${listSubject}">
                                <option value="${subject}">${subject}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="questions">Number of Questions:</label>
                        <input type="number" id="questions" class="form-control" placeholder="Enter number of questions">
                    </div>

                    <div class="form-group">
                        <label for="lesson">Lesson:</label>
                        <select id="lesson" class="form-control">
                            <option selected disabled>Choose lesson</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="duration">Duration:</label>
                        <select id="duration" class="form-control">
                            <option selected disabled>Choose Duration</option>
                            <option value="15">15 minutes</option>
                            <option value="30">30 minutes</option>
                            <option value="45">45 minutes</option>
                            <option value="60">1 hour</option>
                            <option value="90">1 hour 30 minutes</option>
                            <option value="120">2 hours</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">Start Practice</button>
                </form>
            </div>
        </section>
        <br/>

        <%@include file="/layout/footer.jsp" %>

        <!-- side bar có thể thu nhỏ khi màn hình nhỏ  -->
        <script src="js/script.js"></script>
    </body>
</html>