<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Subject Detail</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/popup.css"/>

        <!-- Bootstrap file link  -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>



    </head>
    <body>

        <%@include file="/layout/header.jsp"%>

        <%@include file="/layout/sidebar.jsp"%>
        <%@include file="layout/profile.jsp" %>
        <!--change password pop-up-->
        <%@include file="layout/changePassword.jsp" %>

        <section class="playlist-details">
            <c:set var="subject" value="${requestScope.subject}"/>
            <h1 class="heading">Subject details</h1>

            <div class="row">
                <div class="column">
                    <form action="" method="post" class="save-playlist">
                        <button type="submit"><i class="far fa-bookmark"></i> <span>save subject</span></button>
                    </form>

                    <div class="thumb">
                        <img src="<%=contextPath%>/${subject.img}" alt="">
                        <span>${subject.numberOfLesson} lesson</span>
                    </div>
                </div>
                <div class="column">
                    <div class="tutor">
                        <img src="<%=contextPath%>/${subject.creator.profileImg}" alt="">
                        <div>
                            <h3>${subject.creator.fullName}</h3>
                            <span>${subject.create_at}</span>
                        </div>
                    </div>

                    <div class="details">
                        <h3>${subject.name}</h3>
                        <p>${subject.description}</p>
                        <c:if test="${!empty subject.pricePackages}">
                            <h4>Sale price: <span>${subject.pricePackages.get(0).salePrice} VND</span></h4>
                            <h4>Origin price: <span style="text-decoration: line-through">${subject.pricePackages.get(0).price} VND</span></h4>
                        </c:if>
                        <c:forEach var="i" items="${subject.tags}">
                            <span class="card-text"><span class="badge badge-info">${i.tag}</span></span>
                            </c:forEach>
                        <br/>
                        <a href="<%=contextPath%>/teacher?id=${subject.creator.userId}" class="inline-btn">view profile</a>
                        <c:if test="${requestScope.status}"><a class="inline-btn">Registered</a></c:if>
                        <c:if test="${!requestScope.status}"><a href="#" id="registerNowBtn" class="inline-btn">Register Now</a></c:if>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Register Subject Popup Modal -->
            <div id="registerSubjectPopup" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <!-- Content will be loaded here by AJAX -->
                    </div>
                </div>
            </div>

        <%@include file="/layout/footer.jsp" %>

        <!-- Custom JS File Link -->
        <script src="js/script.js"></script>
        <script>
            $(document).ready(function () {
                $("#registerNowBtn").on("click", function (event) {
                    event.preventDefault();
                    var subjectId = '${subject.id}';
                    var subjectName = '${subject.name}';
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