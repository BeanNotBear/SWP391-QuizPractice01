<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%String contextPath = request.getContextPath();%>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>404 Not Found</title>

        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="<%=contextPath%>/css/style.css">
    </head>
    <body>
        
        <%@include file="/layout/header.jsp" %>

        <%@include file="layout/sidebar.jsp" %>
        <section class="form-container">
            <form action="home" method="get">
                <h3>404</h3>
                <p>Oops! The page you are looking for does not exist.</p>
                <a href="<%=contextPath%>/home" class="btn">Back to the Home page</a>
            </form>
        </section>

        <script src="<%=contextPath%>/js/script.js"></script>
    </body>
</html>
