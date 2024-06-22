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

        <!--Bootstrap Toggle CSS and JS--> 
        <link rel="stylesheet" href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css">
        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>

        <!-- Custom CSS to make the footer fixed -->
        <style>
            body {
                padding: 0;
                margin: 0;
            }
            .subjectList {
                margin-bottom: 50px;
            }
            #searchByDimension, #searchByStatus {
                margin-left: 5%;
                padding: 5% 10%;
            }
            .heading {
                text-align: center;
            }
            .side-bar {
                padding-top: 6%;
                z-index: 1; /* Giá trị z-index thấp hơn để bị đè */
            }
            nav {
                margin-top: -25px;
            }

            #newSubject {
                text-decoration: underline;
                margin-bottom: 20px;
            }





            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }
            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }
            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                -webkit-transition: .4s;
                transition: .4s;
            }
            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                -webkit-transition: .4s;
                transition: .4s;
            }
            input:checked + .slider {
                background-color: #2196F3;
            }
            input:focus + .slider {
                box-shadow: 0 0 1px #2196F3;
            }
            input:checked + .slider:before {
                -webkit-transform: translateX(26px);
                -ms-transform: translateX(26px);
                transform: translateX(26px);
            }
            .slider.round {
                border-radius: 34px;
            }
            .slider.round:before {
                border-radius: 50%;
            }
        </style>
    </head>

    <body>
        <%@include file="/layout/header.jsp"%>

        <section class="sliderList">
            <h1 class="heading text-center">Slider List</h1>
            <div class="container">
                <a type="button" data-toggle="modal" data-target="#myModal" id="newSubject">New Slider</a>
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Image</th>
                            <th>BackLink</th>                         
                            <th>Status</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${listSlider}">
                            <tr>
                                <td>${item.getID()}</td>
                                <td>${item.getTitle()}</td>
                                <td><img width="30%" src="${item.getImage()}" alt="preview"/></td>
                                <td>${item.getLinkUrl()}</td>
                                <td>
                                    <label class="switch">  
                                        <input type="checkbox" id="status" name="status" <c:if test="${item.getStatus() == 1}">checked</c:if> onchange="toggleStatus(${item.getID()})"/>
                                            <span class="slider round"></span>
                                        </label>
                                    </td>


                                    <td><a href="editSlider?id=${item.getID()}" class="btn btn-info btn-sm">Detail</a></td>
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
                                    <a href="sliderManager?page=${currentPage - 1}" aria-label="Previous">
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
                                    <li><a href="sliderManager?page=${i}">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <li>
                                    <a href="sliderManager?page=${currentPage + 1}" aria-label="Next">
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

        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add New Slider</h4>
                    </div>
                    <div class="modal-body">
                        <form id="addSliderForm">

                            <div class="form-group">
                                <label for="title">Title:</label>
                                <input type="text" class="form-control" id="title" name="title">
                            </div>
                            <div class="form-group">
                                <label for="subTitle">Sub Title:</label>
                                <input type="text" class="form-control" id="subTitle" name="subTitle">
                            </div>
                            <div class="form-group">
                                <label for="content">Content:</label>
                                <textarea class="form-control" id="content" name="content"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="image">Image:</label>
                                <input type="text" class="form-control" id="image" name="image">
                            </div>
                            <div class="form-group">
                                <label for="linkUrl">Link URL:</label>
                                <input type="text" class="form-control" id="linkUrl" name="linkUrl">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="addSlider()">Add</button>
                    </div>
                </div>

            </div>
        </div>


        <%@include file="/layout/footer.jsp"%>

        <!-- side bar có thể thu nhỏ khi màn hình nhỏ  -->
        <script src="js/script.js"></script>
        <script>
                            function toggleStatus(id) {
                                $.ajax({
                                    url: 'changeSliderStatus',
                                    type: 'POST',
                                    data: {id: id},
                                    success: function (response) {
                                        // Handle success response
                                        alert('Status changed successfully' + id);
                                    },
                                    error: function (error) {
                                        // Handle error response
                                        alert('Failed to change status' + id);
                                    }
                                });
                            }

                            function confirmDelete(id) {
                                if (confirm('Are you sure you want to delete this slider?')) {
                                    // Uncomment the next line to actually delete
                                    // window.location.href = 'deleteSlider?id=' + id;
                                }
                            }

                            $(document).ready(function () {
                                $('.status-toggle').bootstrapToggle();
                            });


                            function addSlider() {

                                var title = $('#title').val();
                                var subTitle = $('#subTitle').val();
                                var content = $('#content').val();
                                var image = $('#image').val();
                                var linkUrl = $('#linkUrl').val();

                                // Perform AJAX POST request to servlet
                                $.ajax({
                                    url: 'addSlider', // Đổi thành URL của servlet xử lý thêm slider
                                    type: 'POST',
                                    data: {

                                        title: title,
                                        subTitle: subTitle,
                                        content: content,
                                        image: image,
                                        linkUrl: linkUrl
                                    },
                                    success: function (response) {
                                        // Handle success response
                                        alert('Slider added successfully');
                                        $('#myModal').modal('hide');  // Đóng modal sau khi thêm thành công
                                        // Nạp lại danh sách slider hoặc làm gì đó để cập nhật giao diện
                                    },
                                    error: function (error) {
                                        // Handle error response
                                        alert('Failed to add slider');
                                        console.error(error);
                                    }
                                });
                            }

        </script>
    </body>
</html>