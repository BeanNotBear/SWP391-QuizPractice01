<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="change-password-popup">
    <section class="form-container">
        <i id="cancel-change-password" class="fa-solid fa-xmark" onclick="closePopUp3()"></i>
        <form id="change-password-form" action="change-password" method="post">
            <h3>Change Password</h3>
            <div class="group-pass">
                <p>Enter your old password <span>*</span></p>
                <input id="old-password-cd" type="password" name="old-password" placeholder="Old password" required maxlength="20" class="box password">
                <span id="" class="show-password">Show password</span>         
            </div>

            <div class="group-pass">
                <p>Enter your new password <span>*</span></p>
                <input onkeyup="validatePassword(this)" id="password-ch" type="password" name="new-password" placeholder="New password" required maxlength="20" class="box cf-password">
                <span id="show-pass" class="show-password">Show password</span>
                <div id="passwordMsg" class="danger_msg"></div>
            </div>

            <div class="group-cf-pass">
                <p>Confirm your new password<span>*</span></p>
                <input onkeyup="checkPasswordAndCfPassword(this)" id="cfPasssword-ch" type="password" name="confirm-password" placeholder="Confirm password" required maxlength="20" class="box cf-password">
                <span id="show-cf-pass" class="show-password">Show password</span>
                <div id="cfPassowrdMsg" class="danger_msg"></div>
            </div>
                <button onclick="changePassword()" name="submit" class="option-btn">Change password</button>
        </form>
    </section>
</div>