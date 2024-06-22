let isValidate = false;

function validateFullName (fName) {
    let fullName = document.querySelector("#fullName-rg");
    if(fName.value.trim() === "") {
        isValidate = false;
        fullName.innerHTML = "You must fill first name";
    } else {
        isValidate = true;
        fullName.innerHTML = "";
    }
}

function validateEmail(email) {
    let uEmail = document.querySelector("#uEmail-rg");
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!emailPattern.test(email.value)) {
        isValidate = false;
        uEmail.innerHTML = "Email is wrong format";
    } else {
        isValidate = true;
        uEmail.innerHTML = "";
    }
}

function validatePhoneNumber(phone) {
    let phoneNumber = document.querySelector("#phoneNumber-rg");
    const phonePattern = /^(\+?\d{1,4}?[-.\s]?)?(\(?\d{3}\)?[-.\s]?)?\d{3}[-.\s]?\d{4}$/;
    if(phone.value.trim() !== "" && !phonePattern.test(phone.value)) {
        isValidate = false;
        phoneNumber.innerHTML = "Phone is wrong format";
    } else {
        isValidate = true;
        phoneNumber.innerHTML = "";
    }
}

function validatePassword(password) {
    let passwordMsg = document.querySelector("#passwordMsg-rg");
    const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,20}$/;
    if(!passwordPattern.test(password.value)) {
        isValidate = false;
        passwordMsg.innerHTML = "Password is wrong format";
    } else {
        isValidate = true;
        passwordMsg.innerHTML = "";
    }
}

function checkPasswordAndCfPassword(cfPassword) {
    let cfPasswordMsg = document.querySelector("#cfPassowrdMsg-rg");
    let password = document.querySelector("#password-rg-1");
    if(cfPassword.value !== password.value) {
        isValidate = false;
        cfPasswordMsg.innerHTML = "Password and Confirm password do not match";
    } else {
        isValidate = true;
        cfPasswordMsg.innerHTML = "";
    }
}

function isAllowSendData(submit) {
    if(!isValidate) {
        submit.disabled = true;
        submit.style.cursor = "not-allowed";
    } else {
        submit.disabled = false;
        submit.style.cursor = "pointer";
    }
}
