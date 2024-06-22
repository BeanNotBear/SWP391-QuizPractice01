<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String contextPath = request.getContextPath();%>
<div class="side-bar">
    <div id="close-btn">
        <i class="fas fa-times"></i>
    </div>

    <c:if test="${sessionScope.user != null}">
        <div class="profile">
            <img src="<%=contextPath%>/${sessionScope.user.profileImg}" class="image" alt="">
            <br>
            <h3 class="name">${sessionScope.user.fullName}</h3>
            <br>
            <c:if test="${sessionScope.user.roleId == 1}">
                <p class="role">Customer</p>
            </c:if>
            <c:if test="${sessionScope.user.roleId == 2}">
                <p class="role">Admin</p>
            </c:if>
            <c:if test="${sessionScope.user.roleId == 3}">
                <p class="role">Expert</p>
            </c:if>
            <c:if test="${sessionScope.user.roleId == 4}">
                <p class="role">Sale</p>
            </c:if>
            <c:if test="${sessionScope.user.roleId == 4}">
                <p class="role">Marketing</p>
            </c:if>
            <br>
            <a onclick="openPopUp2()" class="option-btn">view profile</a>
        </div>
    </c:if>

    <form action="<%=request.getContextPath()%>/subject" method="post" class="search-form">
        <input type="text" name="search_box" required placeholder="search subject..." maxlength="100">
        <button type="submit" class="fas fa-search"></button>
    </form>

    <nav class="navbar">
        <div class="feature-subject"><i class="fa-solid fa-star"></i><span>Feature subject</span></div>
                <c:forEach var="i" items="${sessionScope.top3Subject}">
            <a class="subject-f" href="subject-details?id=${i.id}"><span>${i.name}</span></a>
                </c:forEach>
    </nav>

    <nav class="navbar">
        <a href="<%=contextPath%>/home"><i class="fas fa-home"></i><span>Home</span></a>
        <a href="<%=contextPath%>/subject"><i class="fas fa-graduation-cap"></i><span>Subjects</span></a>
        <a href="<%=contextPath%>/blog"><i class="fas fa-newspaper"></i><span>Posts</span></a>
    </nav>


</div>