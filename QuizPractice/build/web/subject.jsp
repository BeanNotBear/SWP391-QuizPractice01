<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Subjects</title>

        <!-- Font Awesome CDN link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">

        <!-- Custom CSS file link -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/popup.css"/>

        <!-- Bootstrap file link -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <link rel="stylesheet" href="css/pagination.css"/>
    </head>
    <body>

        <%@include file="layout/header.jsp" %>
        <%@include file="layout/sidebar.jsp" %>
        <%@include file="layout/profile.jsp" %>
        <!-- Change password pop-up -->
        <%@include file="layout/changePassword.jsp" %>

        <section class="courses">
            <div>
                <h1 class="heading">Our subjects</h1>
                <div class="links">
                    <a href="subject?search=${param.search}&page=1&sort=asc"><span>Updated date ↑️</span></a>
                    <a href="subject?search=${param.search}&page=1&sort=desc"><span>Updated Date ↓</span></a>
                </div>
            </div>
            <div class="box-container">
                <c:forEach items="${listC}" var="c">
                    <div class="box">
                        <div class="tutor">
                            <img src="${c.creator.profileImg}" alt="">
                            <div class="info">
                                <h3>${c.creator.fullName}</h3>
                                <span>${c.create_at}</span>
                            </div>
                        </div>
                        <div class="thumb">
                            <img src="${c.img}" alt="">
                            <span>${c.numberOfLesson} lesson</span>
                        </div>
                        <c:if test="${!empty c.pricePackages}">
                            <h3 class="title">${c.name}</h3>
                            <h4>Sale price: ${c.pricePackages.get(0).salePrice}VND</h4>
                            <h4>Origin price: <span style="text-decoration: line-through">${c.pricePackages.get(0).price}VND</span></h4>
                        </c:if>
                        <c:if test="${!empty c.tags}">
                            <c:forEach var="i" items="${c.tags}">
                                <span class="card-text"><span class="badge badge-info">${i.tag}</span></span>
                                </c:forEach>
                            </c:if>
                        <br/>
                        <a href="subject-details?id=${c.id}" class="inline-btn">Details</a>
                        <c:if test="${c.registered}">
                            <a class="inline-btn">Registered</a>
                        </c:if>
                        <c:if test="${!c.registered}">
                            <a class="inline-btn registerNowBtn" data-subject-id="${c.id}" data-subject-name="${c.name}">Register</a>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </section>

        <!-- Modal Container -->
        <div id="registerSubjectPopup" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <!-- Content will be loaded here by AJAX -->
                </div>
            </div>
        </div>

        <nav class="mt-3" aria-label="Page navigation example">
            <ul class="pagination">
                <!-- Disable the "Previous" link if on the first page -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?category=${param.category}&amp;search=${param.search}&amp;sort=${param.sort}&amp;color=${param.color}&amp;size=${param.size}&amp;page=${currentPage - 1}">Previous</a>
                    </li>
                </c:if>

                <!-- Display the page numbers as links -->
                <c:forEach var="pageNum" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test='${pageNum == currentPage}'>active</c:if>">
                        <a class="page-link" href="?category=${param.category}&amp;search=${param.search}&amp;sort=${param.sort}&amp;color=${param.color}&amp;size=${param.size}&amp;page=${pageNum}">${pageNum}</a>
                    </li>
                </c:forEach>

                <!-- Disable the "Next" link if on the last page -->
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?category=${param.category}&amp;search=${param.search}&amp;sort=${param.sort}&amp;color=${param.color}&amp;size=${param.size}&amp;page=${currentPage + 1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>

        <%@include file="layout/footer.jsp" %>

        <!-- Custom JS file link -->
        <script src="js/popup.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="js/script.js"></script>

        <script>
            $(document).ready(function () {
                $(".registerNowBtn").on("click", function (event) {
                    event.preventDefault();
                    var subjectId = $(this).data('subject-id');
                    var subjectName = $(this).data('subject-name');
                    $.ajax({
                        url: "<%=contextPath%>/subjectRegister",
                        type: "GET",
                        data: {subjectId: subjectId, subjectName: subjectName},
                        success: function (data) {
                            $("#registerSubjectPopup .modal-content").html(data);
                            $("#registerSubjectPopup").modal("show");
                        }
                    });
                });
            });
        </script>
    </body>
</html>