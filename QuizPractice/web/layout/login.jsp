<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="login-popup">
    <section class="form-container">
        <i id="cancel-login" class="fa-solid fa-xmark" onclick="closePopUp()"></i>
        <form id="login-form" action="login" method="post">
            <h3>login now</h3>
            <p>Your email <span>*</span></p>
            <input id="email" type="email" name="email" placeholder="enter your email" required maxlength="50" class="box">
            <p>Your password <span>*</span></p>
            <input id="passowrd" type="password" name="password" placeholder="enter your password" required maxlength="50" class="box">
            <div class="danger_msg"></div>
            <a id="forget_password" href="reset-password">Forget Password?</a>
            <input type="submit" value="login now" name="submit" class="option-btn">
        </form>
    </section>
</div>
