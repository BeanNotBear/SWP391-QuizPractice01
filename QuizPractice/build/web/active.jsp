<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="css/style.css">
        <style type="text/css">
            .form-gap {
                padding-top: 70px;
            }
            .fa-check-circle {
                color: green;
            }
        </style>
    </head>

    <body>
        <%@include file="layout/header.jsp" %>
        <%@include file="layout/sidebar.jsp" %>

        <div class="form-gap"></div>
        <div class="container">
            <div style="margin-bottom: 300px" class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="text-center">
                                <h3>
                                    <i class="fa fa-check-circle fa-4x"></i>
                                </h3>
                                <h2 class="text-center">Please check your email to get via link!</h2>

                                <div class="panel-body">

                                    <form id="register-form" action="otp" role="form" autocomplete="off"
                                          class="form" method="post">
                                        <input type="hidden" class="hide" name="token" id="token" value="">
                                    </form>

                                    <!-- Gmail Button -->
                                    <div class="form-group text-center">
                                        <a href="https://mail.google.com" target="_blank" class="btn btn-lg btn-danger">
                                            <i class="fa fa-envelope"></i> Go to Gmail
                                        </a>
                                    </div>
                                    <!-- End of Gmail Button -->

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="layout/footer.jsp" %>
        <script src="js/script.js"></script>
    </body>
</html>