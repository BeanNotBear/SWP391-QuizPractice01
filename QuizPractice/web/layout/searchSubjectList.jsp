<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String contextPath = request.getContextPath();%>
<div class="side-bar">
    <br/>
    <br/>

    <c:if test="${sessionScope.user != null}">
        <div class="profile">
            <img src="<%=contextPath%>/${sessionScope.user.profileImg}" class="image" alt="">
            <h3 class="name">${sessionScope.user.fullName}</h3>
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
            <a onclick="openPopUp2()" class="option-btn">view profile</a>
        </div>
    </c:if>
    <br/>

    <form action="<%=request.getContextPath()%>/subjectManager" method="post" class="form-inline" id="searchByDimension">
        <div class="form-group">
            <label>Category</label>
            <select id="mutipleSelect" multiple name="native-select" placeholder="Select Category" data-search="true" data-silent-initial-value-set="true">
                <c:forEach var="dimension" items="${dimensions}">
                    <option value="${dimension.id}">${dimension.name}</option>
                </c:forEach>
            </select>
        </div>
        <div style="margin-top: 15px" class="form-group">
            <label>Status</label>
            <select id="status-search" multiple name="native-select" placeholder="Select status" data-search="true" data-silent-initial-value-set="true">
                <option value="1">Published</option>
                <option value="2">Unpublished</option>
            </select>
        </div>
        <input type="button" class="btn btn-primary" id="filter" value="Filter">
    </form>
    <br/>
</div>

<script>
    document.getElementById('dimension').addEventListener('change', function () {
        document.getElementById('searchByDimension').submit();
    });

    document.getElementById('status').addEventListener('change', function () {
        document.getElementById('searchByStatus').submit();
    });
</script>