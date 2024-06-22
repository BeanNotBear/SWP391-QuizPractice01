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
            <h3 class="name">${sessionScope.user.fullName}</h3>
            <p class="role">student</p>
            <a onclick="openPopUp2()" class="option-btn">view profile</a>
        </div>
    </c:if>
    <br/>

    <form action="<%=request.getContextPath()%>/myRegister" method="post" class="search-form">
        <input type="text" name="subjectName" required placeholder="Subject" maxlength="100">
        <button type="submit" class="fas fa-search"></button>
    </form>
    <br/>

    <form action="search" method="GET" class="form-inline" id="searchByCategory">
        <div class="form-group">
            <select id="category" name="category" class="form-control">
                <option value="" disabled selected>Tìm kiếm theo category</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>

    </form>
    <br/>

    <form action="<%=request.getContextPath()%>/subject" method="post" class="search-form">
        <input type="text" name="search_box" required placeholder="Static content" maxlength="100">
        <button type="submit" class="fas fa-search"></button>
    </form>
    <br/>
</div>