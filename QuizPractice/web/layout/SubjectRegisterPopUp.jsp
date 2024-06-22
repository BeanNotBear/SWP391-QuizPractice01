<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="model.PricePackage" %>
<%@ page import="java.util.List" %>
<% List<PricePackage> listPricePackage = (List<PricePackage>) request.getAttribute("listPricePackage"); %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Register Subject Popup</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <!-- SweetAlert2 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>

        </style>
    </head>
    <body>
        <div id="registerSubjectPopup">
            <div class="modal-header">
                <h4 class="modal-title text-center">${subjectName}</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="registerSubjectForm">
                    <div class="form-group">
                        <label>Chọn gói giá</label>
                        <div>
                            <c:forEach var="pkg" items="${listPricePackage}">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="pricePackageId" value="${pkg.id}" required>
                                        ${pkg.name} - ${pkg.duration} month - ${pkg.price} VND
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <hr/>
                    <c:if test="${sessionScope.user == null}">
                        <input type="hidden" id="userId" name="userId" value="${sessionScope.user.userId}">
                        <h4 class="modal-title text-center">User Information</h4>
                        <!-- Error Message -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">${errorMessage}</div>
                        </c:if>

                        <div class="form-group">
                            <input type="text" class="form-control" id="userName" name="userName" placeholder="Enter Username" required oninput="validateUserName(this)">
                            <div id="userNameError" style="color: red;"></div>
                        </div>
                        <div class="form-group">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
                            <div id="emailError" style="color: red;"></div>
                        </div>
                        <div class="form-group">
                            <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Enter phone number" required oninput="validatePhoneNumber(this)">
                            <div id="phoneError" style="color: red;"></div>
                        </div>
                        <div class="form-group">
                            <select class="form-control" id="gender" name="gender" required>
                                <option value="" disabled selected>Gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                            </select>
                        </div>
                    </c:if>
                    <input type="hidden" name="subjectId" value="${subjectId}">
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div>
                </form>
            </div>
            <!-- Bootstrap JS and jQuery -->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
            <script>
                <c:if test="${sessionScope.user == null}">
            function validatePhoneNumber(input) {
                var phoneNumber = input.value.trim();
                var errorElement = document.getElementById('phoneError');
                const regex = /^(\+84|0)(1|2|3|4|5|6|7|8|9)\d{8}$/;

                // Kiểm tra nếu không phải là số
                if (!phoneNumber.match(regex)) {
                    errorElement.textContent = 'Phone number should only contain digits (0-9).';
                    input.setCustomValidity('Invalid input. Only digits allowed.');
                } else {
                    errorElement.textContent = '';
                    input.setCustomValidity('');
                }
            }

            function validateUserName(input) {
                var userName = input.value.trim();
                var errorElement = document.getElementById('userNameError');
                const regex = /^[A-Za-z\s]+$/;
                const multipleSpacesRegex = / {2,}/;

                if (!userName.match(regex)) {
                    errorElement.textContent = 'Username should contain only letters, spaces, and no special characters.';
                    input.setCustomValidity('Invalid input. Only letters and single spaces allowed.');
                } else if (multipleSpacesRegex.test(userName)) {
                    errorElement.textContent = 'Username should not contain multiple consecutive spaces.';
                    input.setCustomValidity('Invalid input. Multiple consecutive spaces are not allowed.');
                } else {
                    errorElement.textContent = '';
                    input.setCustomValidity('');
                }
            }
                </c:if>

            $(document).ready(function () {
                $("#userName").on('input', function () {
                    validateUserName(this);
                });

                $("#email").on('input', function () {
                    validateEmail(this);
                });

                function validateEmail(input) {
                    var email = input.value.trim();
                    var errorElement = document.getElementById('emailError');
                    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

                    if (!email.match(regex)) {
                        errorElement.textContent = 'Invalid email format.';
                        input.setCustomValidity('Invalid email format.');
                    } else {
                        errorElement.textContent = '';
                        input.setCustomValidity('');
                    }
                }

                $("#registerSubjectForm").submit(function (e) {
                    e.preventDefault();

                    // Validate the entire form using HTML5 built-in validation
                    if (this.checkValidity()) {
                        var email = $("#email").val();
                        // Check if email already exists
                        $.ajax({
                            type: 'POST',
                            url: 'checkEmail',
                            data: {email: email},
                            success: function (data) {
                                if (data.exists) {
                                    Swal.fire({
                                        title: "Email already exists!",
                                        icon: "error"
                                    });
                                } else {
                                    // Proceed with registration
                                    $.ajax({
                                        type: 'POST',
                                        url: 'subjectRegister',
                                        data: $("#registerSubjectForm").serialize(),
                                        success: function (data) {
                                            if (data.status === "success") {
                                                Swal.fire({
                                                    title: data.message,
                                                    icon: "success"
                                                }).then((result) => {
                                                    if (result.isConfirmed) {
                                                        window.location.reload();
                                                    }
                                                });
                                            } else {
                                                Swal.fire({
                                                    title: data.message,
                                                    icon: "error"
                                                });
                                            }
                                        },
                                        error: function (xhr, status, error) {
                                            Swal.fire({
                                                title: "An error occurred",
                                                text: xhr.responseText,
                                                icon: "error"
                                            });
                                        }
                                    });
                                }
                            },
                            error: function (xhr, status, error) {
                                Swal.fire({
                                    title: "An error occurred",
                                    text: xhr.responseText,
                                    icon: "error"
                                });
                            }
                        });
                    }
                });
            });

            </script>
        </div>
    </body>
</html>