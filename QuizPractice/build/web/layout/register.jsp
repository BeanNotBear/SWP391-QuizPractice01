<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="register-popup">
    <section class="form-container">
        <i id="cancel-register" class="fa-solid fa-xmark" onclick="closePopUp1()"></i>
        <form id="register-form" action="register" method="post">
            <h3>register now</h3>
            <p>Enter your full name <span>*</span></p>
            <input id="full-Name" onkeyup="validateFullName(this)" type="text" name="full-name" placeholder="Full name" required maxlength="50" class="box" value="${requestScope.fullName}">
            <span id="fullName-rg" class="danger_msg"></span>

            <p>Enter your email <span>*</span></p>
            <input id="email-rg" onkeyup="validateEmail(this)" type="email" name="email" placeholder="Email" required maxlength="50" class="box" value="${requestScope.email}">
            <span id="uEmail-rg" class="danger_msg"></span>


            <p>Enter your phone number <span>(not required)</span></p>
            <input id="phone" onkeyup="validatePhoneNumber(this)" type="text" name="phone" placeholder="Phone number" maxlength="50" class="box" value="${requestScope.phone}">
            <span id="phoneNumber-rg" class="danger_msg"></span>

            <p>Enter your gender <span>*</span></p>
            <select id="gender" class="box" name="gender">
                <option value="true">Male</option>
                <option value="false">Female</option>
            </select>
            <span class="danger_msg"></span>

            <div class="group-pass">
                <p>Enter your password <span>*</span></p>
                <input onkeyup="validatePassword(this)" id="password-rg-1" type="password" name="password" placeholder="Password" required maxlength="20" class="box password" value="${requestScope.password}">
                <span id="show-pass" class="show-password">Show password</span>
                <div id="passwordMsg-rg" class="danger_msg"></div>
            </div>

            <div class="group-cf-pass">
                <p>Enter your password again<span>*</span></p>
                <input onkeyup="checkPasswordAndCfPassword(this)" id="cfPasssword-rg-1" type="password" name="cf-password" placeholder="Confirm password" required maxlength="20" class="box cf-password" value="${requestScope.cfPassowrd}">
                <span id="show-cf-pass" class="show-password">Show password</span>
                <div id="cfPassowrdMsg-rg" class="danger_msg"></div>
            </div>
            <button onmouseover="isAllowSendData(this)" type="submit" name="submit" class="option-btn">register new</button>
        </form>
    </section>
</div>