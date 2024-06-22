<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Navbar</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>
            #bar{
                display: flex;
                justify-content: space-between;
                align-items: center;
                background-color: #fff;
                padding: 10px;
                position: relative;
            }
            .navbar a.logo {
                color: #333;
                text-decoration: none;
                font-size: 20px;
                padding: 10px;
            }
            .navbar .menu {
                display: flex;
                list-style: none;
                margin: 0;
                padding: 0;
            }
            .navbar .menu li {
                margin: 0 10px;
                border-right: 1px solid #ccc; /* Thêm dấu gạch giữa các mục */
                padding-right: 10px; /* Thêm khoảng cách giữa mỗi mục */
            }
            .navbar .menu li:last-child {
                border-right: none; /* Loại bỏ dấu gạch ở mục cuối cùng */
            }
            .navbar .menu li a {
                color: #333;
                text-decoration: none;
                padding: 10px;
                transition: background-color 0.3s;
            }
            .navbar .menu li a:hover {
                background-color: #e0e0e0;
                border-radius: 5px;
            }
            .icons div, .profile div {
                margin-left: 15px;
                cursor: pointer;
                color: #333;
            }
            .profile .name, .profile .role {
                color: #333;
                margin: 0 10px;
            }
            .profile img {
                width: 30px;
                height: 30px;
                border-radius: 50%;
                margin-right: 10px;
            }
            .option-btn {
                background-color: #007bff;
                color: #fff;
                border: none;
                padding: 5px 10px;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .option-btn:hover {
                background-color: #0056b3;
            }
            .flex-btn a {
                margin-left: 10px;
            }
            .profile-menu {
                display: none;
                flex-direction: column;
                position: absolute;
                top: 50px;
                right: 12%;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            .profile-menu a {
                color: #333;
                text-decoration: none;
                padding: 10px;
                border-bottom: 2px solid #ddd;
                padding-right: 10%;
            }
            .profile-menu a:last-child {
                border-bottom: none;
            }
            .profile-menu a:hover {
                background-color: #e0e0e0;
            }
            .navbar .menu-icon {
                display: none;
                cursor: pointer;
                color: #333;
            }
            @media (max-width: 768px) {
                .navbar .menu {
                    display: none;
                    flex-direction: column;
                    position: absolute;
                    top: 50px;
                    right: 10px;
                    background-color: #fff;
                    border-radius: 5px;
                    width: 200px;
                    box-shadow: 0 0 10px rgba(0,0,0,0.1);
                }
                .navbar .menu li {
                    margin: 0;
                    border-right: none; /* Loại bỏ dấu gạch khi màn hình nhỏ */
                    padding-right: 0; /* Loại bỏ khoảng cách phải khi màn hình nhỏ */
                }
                .navbar .menu-icon {
                    display: block;
                }
            }
        </style>
    </head>
    <body>
        <header class="header">
            <nav class="navbar" id="bar">
                <div class="logo-section">
                    <a href="<%=request.getContextPath()%>/home" class="logo">QuizPractice</a>
                </div>
                <ul class="menu" id="menu">
                    <c:choose>
                    
                        <c:when test="${sessionScope.user.roleId == 1}">
                            <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                            <li><a href="<%=request.getContextPath()%>/blog">Blog</a></li>
                            <li><a href="<%=request.getContextPath()%>/subject">Subject</a></li>
                            <li><a href="<%=request.getContextPath()%>/myRegister">My Register</a></li>
                            <li><a href="<%=request.getContextPath()%>/myregister">Register Subject</a></li>
                            <li><a href="<%=request.getContextPath()%>/myregister">Simulate Exam</a></li> 
                            <li><a href="<%=request.getContextPath()%>/praticeList">Practice List</a></li>
                            </c:when>
                            
                            <c:when test="${sessionScope.user.roleId == 2}">
                                <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                                <li><a href="<%=request.getContextPath()%>/newSubject">New Subject</a></li>
                                <li><a href="<%=request.getContextPath()%>/subjectManager">Subject Manager</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu3">User List</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu3">Dashboard</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu3">Question Manager</a></li>   
                                <li><a href="<%=request.getContextPath()%>/newmenu3">Quiz Manager</a></li>   
                           
                            </c:when>
                                <c:when test="${sessionScope.user.roleId == 4}">
                                <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu2">Dashboard</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu3">Register List</a></li>
                            </c:when>
                                <c:when test="${sessionScope.user.roleId == 5}">
                                <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu2">Dashboard</a></li>
                                <li><a href="<%=request.getContextPath()%>/blog">Blog Manager</a></li>
                                <li><a href="<%=request.getContextPath()%>/sliderManager">Slider Manager</a></li>
                            </c:when>
                                <c:when test="${sessionScope.user.roleId == 3 }">
                                <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu2">Question Manager</a></li>
                                <li><a href="<%=request.getContextPath()%>/subjectManager">Subject Manager</a></li>
                                <li><a href="<%=request.getContextPath()%>/newmenu3">Quiz Manager</a></li>
                                
                           
                            </c:when>
                            <c:otherwise>
                            <!-- Nếu không có vai trò nào được xác định -->
                        </c:otherwise>
                    </c:choose>

                </ul>
                <!--            <div class="menu-icon" id="menu-icon">
                                <i class="fas fa-sun"></i>
                            </div>-->
                <div class="icons">
                    <div class="menu-icon" id="menu-icon">
                        <i class="fas fa-sun"></i>
                    </div>
                    <div id="menu-btn" class="fas fa-bars"></div>
                    <div id="search-btn" class="fas fa-search"></div>
                    <div id="user-btn" class="fas fa-user"></div>
                </div>
            </nav>
            <div class="profile-menu" id="profile-menu">
                <c:if test="${sessionScope.user == null}">
                    <a onclick="openPopUp()">Login</a>
                    <a onclick="openPopUp1()">Register</a>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <a onclick="logout()">Log Out</a>
                </c:if>
            </div>
        </header>
        <script>
            document.getElementById('user-btn').addEventListener('click', function () {
                var profileMenu = document.getElementById('profile-menu');
                if (profileMenu.style.display === 'none' || profileMenu.style.display === '') {
                    profileMenu.style.display = 'flex';
                } else {
                    profileMenu.style.display = 'none';
                }
            });

            document.getElementById('menu-icon').addEventListener('click', function () {
                var menu = document.getElementById('menu');
                if (menu.style.display === 'none' || menu.style.display === '') {
                    menu.style.display = 'flex';
                } else {
                    menu.style.display = 'none';
                }
            });
        </script>
    </body>
</html>