function closePopUp() {
    const loginPopUp = document.querySelector(".login-popup");
    if (loginPopUp.style.display !== "none") {
        loginPopUp.style.display = "none";
    }
}

function openPopUp() {
    const loginPopUp = document.querySelector(".login-popup");
    if (loginPopUp.style.display === "none" || loginPopUp.style.display === "") {
        loginPopUp.style.display = "flex";
    }
}

function closePopUp1() {
    const registerPopUp = document.querySelector("#register-popup");
    if (registerPopUp.style.display !== "none") {
        registerPopUp.style.display = "none";
    }
}

function openPopUp1() {
    const registerPopUp = document.querySelector("#register-popup");
    if (registerPopUp.style.display === "none" || registerPopUp.style.display === "") {
        registerPopUp.style.display = "block";
    }
}

function closePopUp2() {
    const profilePopUp = document.querySelector(".profile-popup");
    if (profilePopUp.style.display !== "none") {
        profilePopUp.style.display = "none";
    }
}

function openPopUp2() {
    const profilePopUp = document.querySelector(".profile-popup");
    if (profilePopUp.style.display === "none" || profilePopUp.style.display === "") {
        profilePopUp.style.display = "block";
    }
}

function closePopUp3() {
    const changePasswordPopUp = document.querySelector(".change-password-popup");
    if (changePasswordPopUp.style.display !== "none") {
        changePasswordPopUp.style.display = "none";
    }
}

function openPopUp3() {
    const changePasswordPopUp = document.querySelector(".change-password-popup");
    const profilePopUp = document.querySelector(".profile-popup");
    if (changePasswordPopUp.style.display === "none" || changePasswordPopUp.style.display === "") {
        changePasswordPopUp.style.display = "block";
        profilePopUp.style.display = "none";
    }
}

$("#login-form").submit(function (e) {
    e.preventDefault();
    const  email = $("#email").val();
    const  password = $(":password").val();
    const  formData = {
        email: email,
        password: password
    };
    const actionUrl = "login";
    $.ajax({
        type: "POST",
        url: actionUrl,
        data: formData,
        beforeSend: function () {
            $("#loading").css('display', 'flex');
        },
        complete: function () {
            $("#loading").css('display', 'none');
        },
        success: function (data) {
            console.log(data.status);
            if (data.status === "success") {
                Swal.fire({
                    title: data.message,
                    icon: "success"
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.reload();
                    }
                });
            } else if (data.status === "unactive") {
                Swal.fire({
                    title: "Please go to your email and verify your account",
                    icon: "info"
                });
            } else {
                Swal.fire({
                    title: data.message,
                    icon: "error"
                });
            }
        }
    });
});

$("#register-form").submit(function (e) {
    e.preventDefault();
    const fullName = $("#full-Name").val();
    const email = $("#email-rg").val();
    const phoneNumber = $("#phone").val();
    const gender = $("#gender").val();
    const password = $("#password-rg-1").val();
    const cfPassword = $("#cfPasssword-rg-1").val();
    const formData = {
        fullName: fullName,
        email: email,
        phone: phoneNumber,
        gender: gender,
        password: password,
        cfPassword: cfPassword
    };

    const actionUrl = "register";

    $.ajax({
        type: 'POST',
        url: actionUrl,
        data: formData,
        beforeSend: function () {
            $("#loading").css('display', 'flex');
        },
        complete: function () {
            $("#loading").css('display', 'none');
        },
        success: function (data) {
            console.log("ok");
            if (data.status === "success") {
                Swal.fire({
                    title: "Sign Up successfully!",
                    text: "Please go to your email to verify your account",
                    icon: "success"
                });
            } else {
                Swal.fire({
                    title: "Sign Up fail!",
                    text: data.err,
                    icon: "error"
                });
            }
        }
    });
});

$("#profile-form").submit(function (e) {
    e.preventDefault();
    const fullName = $("#full-name-pf").val();
    const email = $("#email-pf").val();
    const phoneNumber = $("#phone-pf").val();
    const gender = $("input[name=gender]").val();
    const formData = {
        fullName: fullName,
        email: email,
        phone: phoneNumber,
        gender: gender
    };

    const actionUrl = "profile";

    $.ajax({
        type: 'POST',
        url: actionUrl,
        data: formData,
        beforeSend: function () {
            $("#loading").css('display', 'flex');
        },
        complete: function () {
            $("#loading").css('display', 'none');
        },
        success: function (data) {
            if (data.status === "success") {
                console.log("ok");
                Swal.fire({
                    title: "Update profile successfully!",
                    icon: "success"
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.reload();
                    }
                });
            } else {
                Swal.fire({
                    title: "Update profile fail!",
                    icon: "error"
                });
            }
        }
    });
});

function changePassword() {
    const oldPassword = $(":old-password-cd").val();
    const newPassword = $(":new-password").val();
    const confirmPassword = $("#confirm-passsword").val();
    const formData = {
        oldPassword: oldPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
    };

    const actionUrl = "change-password";

    $.ajax({
        type: 'POST',
        url: actionUrl,
        data: formData,
        beforeSend: function () {
            $("#loading").css('display', 'flex');
        },
        complete: function () {
            $("#loading").css('display', 'none');
        },
        success: function (data) {
            console.log("ok");
            if (data.status === "success") {
                Swal.fire({
                    title: "Change password successfully!",
                    icon: "success"
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.reload();
                    }
                });
            } else {
                Swal.fire({
                    title: "Change password fail!",
                    icon: "error"
                });
            }
        }
    });
}


function logout() {
    $.ajax({
        type: 'GET',
        url: "logout",
        success: function () {
            Swal.fire({
                title: "Log Out Successfully!",
                icon: "success"
            }).then((result) => {
                window.location.href = "home";
            });
        }
    });
}