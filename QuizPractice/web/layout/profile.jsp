<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="profile-popup">
    <section class="form-container">
        <i id="cancel-profile" class="fa-solid fa-xmark" onclick="closePopUp2()"></i>
        <form id="profile-form" action="profile" method="post" enctype="multipart/form-data">
            <h3>User Profile</h3>

            <!-- Avatar Upload -->
            <div style="text-align: center;">
                <img src="<%=request.getContextPath()%>/${sessionScope.user.profileImg}" class="image" alt="Profile Picture">

            </div>

            <p>Full Name:</p>
            <input id="full-name-pf" onkeyup="validateFullName(this)" type="text" name="full-name" placeholder="Full name" required maxlength="50" class="box" value="${user.fullName}">
            <span id="fullName" class="danger_msg"></span>

            <p>Email:</p>
            <input id="email-pf" onkeyup="validateEmail(this)" type="email" name="email" placeholder="Email" required maxlength="50" class="box" readonly value="${user.email}">
            <span id="uEmail" class="danger_msg"></span>

            <div>
                <c:if test="${user.gender == true}">
                    <label>
                        <input type="radio" name="gender" value="true" checked > Male
                    </label>
                    <label>
                        <input type="radio" name="gender" value="false" > Female
                    </label>
                </c:if>
                <c:if test="${user.gender == false}">
                    <label>
                        <input type="radio" name="gender" value="true"  > Male
                    </label>
                    <label>
                        <input type="radio" name="gender" value="false" checked > Female
                    </label>
                </c:if>
            </div>

            <p>Phone Number: <span>(not required)</span></p>
            <input id="phone-pf" onkeyup="validatePhoneNumber(this)" type="text" name="phone-number" placeholder="Phone number" maxlength="50" class="box" value="${user.phoneNumber}">
            <span id="phoneNumber" class="danger_msg"></span>


            <span class="danger_msg"></span>
            <a onclick="openPopUp3()" class="links" style="margin-left: 20px; font-size: 18px;">Change password?</a>
            <div style="display: flex; justify-content: space-between;">
                <button type="reset" name="reset" class="option-btn" style="margin-right: 10px;">Reset</button>
                <button type="submit" name="submit" class="option-btn">Save changes</button>
            </div>
        </form>
    </section>
</div>