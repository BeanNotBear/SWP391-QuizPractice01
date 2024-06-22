<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lesson Manager</title>

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

        <style>
            body {
                padding: 0;
                margin: 0;
            }
            .footer {
                background: #f8f9fa;
                text-align: center;
                position: fixed;
                bottom: 0;
                width: 100%;
                height: 10%;
                z-index: 10; /* Giá trị z-index cao để đè lên sidebar */
                padding-left: 2%;
            }


        </style>

        <script>
            $(document).ready(function () {
                // Update results on form submit
                $("#searchForm").submit(function (event) {
                    event.preventDefault(); // Prevent the default form submit
                    updateLessons();
                });

                // Detect 'Enter' key press in search name input
                $('#searchName').keypress(function (event) {
                    var keycode = (event.keyCode ? event.keyCode : event.which);
                    if (keycode == '13') { // '13' is the key code for Enter
                        event.preventDefault(); // Prevent the default action
                        updateLessons();
                    }
                });

                // Update results on select change
                $("#searchType, #searchStatus").change(function () {
                    updateLessons();
                });
            });

            function updateLessons(page = 1) {
                const name = $('#searchName').val();
                const type = $('#searchType').val();
                const status = $('#searchStatus').val();

//                $.get("lessonManager?subjectId=${subjectId}", {
//                    searchName: name,
//                    searchType: type,
//                    searchStatus: status,
//                    page: page
//                }, function (data) {
//                    $('section.subjectList').html(data);
//                });

                // Xây dựng URL
                const href = "lessonManager?subjectId=${subjectId}&searchName=" + name + "&searchType=" + type + "&searchStatus=" + status + "&page=" + page;
                window.location.href = href;  // Chuyển hướng người dùng đến URL
            }
        </script>

    </head>
    <body>
        <%@include file="/layout/header.jsp"%>

        <section class="subjectList">
            <div class="container">
                <h1 class="heading text-center">Lesson Manager</h1>

                <!-- Search and add lesson row -->
                <div class="row">
                    <div class="col-md-6">
                        <input type="text" class="form-control" id="searchName" value="${searchName}" placeholder="Search by name" name="searchName" form="searchForm">
                    </div>
                    <div class="col-md-2">
                        <select class="form-control" id="searchType" name="searchType" form="searchForm">
                            <option value="">All Types</option>
                            <option value="content" ${searchType == 'content' ? 'selected' : ''}>Content</option>
                            <option value="quiz" ${searchType == 'quiz' ? 'selected' : ''}>Quiz</option>
                        </select>

                    </div>
                    <div class="col-md-2">
                        <select class="form-control" id="searchStatus" name="searchStatus" form="searchForm">
                            <option value="">All Statuses</option>
                            <option value="1" ${searchStatus == '1' ? 'selected' : ''}>Active</option>
                            <option value="0" ${searchStatus == '0' ? 'selected' : ''}>Inactive</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <!--                        <a href="addLesson" class="btn btn-success">Add Lesson</a>-->
                        <!-- Button to Open the Modal -->
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#addLessonModal">
                            Add New Lesson
                        </button>

                    </div>
                </div>

                <!-- Form for searching -->
                <form id="searchForm" action="lessonManager" method="GET"></form>

                <!-- Lessons Table -->
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Lesson</th>
                            <th>Order</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="lesson" items="${listLesson}">
                            <tr>
                                <td>${lesson.id}</td>
                                <td>${lesson.name}</td>
                                <td>${lesson.lessonIndex}</td>
                                <td>${lesson.type}</td>
                                <td>${lesson.status == 1 ? 'Active' : 'Inactive'}</td>
                                <td>
                                    <a href="javascript:void(0);" class="btn btn-primary col-md-5" data-toggle="modal" data-target="#editLessonModal" onclick="editLesson(${lesson.id})">Edit</a>
                                    <span class="col-md-1"></span>
                                    <form action="lessonManager" method="post" class="col-md-5">
                                        <input type="hidden" name="id" value="${lesson.id}">
                                        <input type="hidden" name="subjectId" value="${subjectId}">
                                        <input type="hidden" name="status" value="${lesson.status == 1 ? '0' : '1'}">
                                        <input type="hidden" name="formType" value="toggleStatus">
                                        <button type="submit" class="btn btn-warning">${lesson.status == 1 ? 'Deactivate' : 'Activate'}</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li>
                                <a href="javascript:updateLessons(${currentPage - 1});" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="${currentPage == i ? 'active' : ''}">
                                <a href="javascript:updateLessons(${i});">${i}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <li>
                                <a href="javascript:updateLessons(${currentPage + 1});" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

            <!-- The Modal -->
            <div class="modal fade" id="addLessonModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalLabel">Add New Lesson</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="addLessonForm" action="lessonManager" method="post">
                                <div class="form-group">
                                    <label for="name">Name:</label>
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>
                                <div class="form-group">
                                    <label for="content">Content:</label>
                                    <textarea class="form-control" id="content" name="content" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="media">Media:</label>
                                    <input type="text" class="form-control" id="media" name="media">
                                </div>
                                <div class="form-group">
                                    <label for="lessonIndex">Lesson Index:</label>
                                    <input type="number" class="form-control" id="lessonIndex" name="lessonIndex" required>
                                </div>
                                <div class="form-group">
                                    <label for="type">Type:</label>
                                    <select class="form-control" id="type" name="type">
                                        <option value="content">Content</option>
                                        <option value="quiz">Quiz</option>
                                    </select>
                                </div>
                                <input type="hidden" name="formType" value="addForm">
                                <input type="hidden" name="subjectId" value="${subjectId}">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- The Edit Modal -->
            <div class="modal fade" id="editLessonModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editModalLabel">Edit Lesson</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="editLessonForm" action="lessonManager" method="post">
                                <div class="form-group">
                                    <label for="editName">Name:</label>
                                    <input type="text" class="form-control" id="editName" name="name" required>
                                </div>
                                <div class="form-group">
                                    <label for="editContent">Content:</label>
                                    <textarea class="form-control" id="editContent" name="content" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="editMedia">Media:</label>
                                    <input type="text" class="form-control" id="editMedia" name="media">
                                </div>
                                <div class="form-group">
                                    <label for="editLessonIndex">Lesson Index:</label>
                                    <input type="number" class="form-control" id="editLessonIndex" name="lessonIndex" required>
                                </div>
                                <div class="form-group">
                                    <label for="editType">Type:</label>
                                    <select class="form-control" id="editType" name="type">
                                        <option value="content">Content</option>
                                        <option value="quiz">Quiz</option>
                                    </select>
                                </div>

                                <input type="hidden" id="editId" name="id">
                                <input type="hidden" name="formType" value="editForm">
                                <input type="hidden" name="subjectId" value="${subjectId}">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="/layout/footer.jsp"%>
        <script>
            function editLesson(id) {
                $.ajax({
                    url: 'getLessonById', // URL to the controller method
                    type: 'GET',
                    data: {id: id},
                    success: function (data) {
                        // Assuming the response data is a JSON object with lesson details
                        $('#editLessonForm #editId').val(data.id);
                        $('#editLessonForm #editName').val(data.name);
                        $('#editLessonForm #editContent').val(data.content);
                        $('#editLessonForm #editMedia').val(data.media);
                        $('#editLessonForm #editLessonIndex').val(data.lessonIndex);
                        $('#editLessonForm #editType').val(data.type);

                        // Show the modal
                        $('#editLessonModal').modal('show');
                    }
                });
            }
        </script>
    </body>
</html>