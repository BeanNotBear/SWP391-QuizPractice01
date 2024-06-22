<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blogs</title>

        <!-- Font Awesome CDN link -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
        <!-- Custom CSS file link -->
        <link rel="stylesheet" href="css/style.css">

        <link rel="stylesheet" href="css/popup.css"/>

        <!-- Bootstrap file link  -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>

        <%@include file="layout/header.jsp" %>

        <%@include file="layout/sidebar.jsp" %>

        <%@include file="layout/sidebarright.jsp" %>

        <!--profile pop-up-->
        <%@include file="layout/profile.jsp" %>

        <!--change password pop-up-->
        <%@include file="layout/changePassword.jsp" %>

        <!-- Hiển thị tất cả blogs có trong database -->
        <section class="reviews">

            <h1 class="heading">Pots detail </h1>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>
            <div class="box-container">

                <div class="box">
                    <img src="${detail.getThumbnail()}" id="detailImg"/>
                    <div class="student">
                        <div>
                            <h3>${detail.getTitle()}</h3>

                            <div class="stars">
                                <p>Author: ${detail.blog_firstName}</p>
                                <c:if test="${sessionScope.user.roleId == 5}">
                                    <p>Brie_info: ${detail.brieinfo}</p>
                                    <p>Status:  
                                        <c:choose>
                                            <c:when test="${detail.status}">
                                                <span style="color: green;">Published</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: red;">Not published</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>


                                </c:if>
                                <p>Create date: ${detail.createdDate}</p>
                                <p>Category: ${detail.category.getCategory_Name()}</p>                                      
                            </div> 
                            <h2>Content</h2>
                            <p>${detail.content}</p>
                        </div>

                    </div>         
                </div>
            </div>
        </section>

        <div id="pagee">
            <c:forEach begin="1" end="${endPage}" var="i">
                <a class="${currentPage == i ? 'activee' : ''}" href="blog?search=${searchQuery}&index=${i}">${i}</a>
            </c:forEach>
        </div>
        <%@include file="layout/footer.jsp" %>
        <!--pop js-->
        <script src="js/popup.js"></script>

        <!--jquery-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

        <!--swal-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="js/script.js"></script>
        <script src="js/rightsidebar.js"></script>
    </body>
</html>


