<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>App</title>

        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/popup.css">

        <!-- Bootstrap file link  -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <!-- Custom CSS to make the footer fixed -->
        <style>
            body {
                padding: 0;
                margin: 0;
            }

            .postDetail {
                margin-bottom: 100px;

            }
            nav {
                margin-top: -25px;
            }

            img{
                width: 100%;
            }
            button{
                width: 20%;
            }


            /* */
            .switch {
                position: relative;
                display: inline-block;
                width: 60px;
                height: 34px;
            }

            .switch input {
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                -webkit-transition: .4s;
                transition: .4s;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 26px;
                width: 26px;
                left: 4px;
                bottom: 4px;
                background-color: white;
                -webkit-transition: .4s;
                transition: .4s;
            }

            input:checked + .slider {
                background-color: #2196F3;
            }

            input:focus + .slider {
                box-shadow: 0 0 1px #2196F3;
            }

            input:checked + .slider:before {
                -webkit-transform: translateX(26px);
                -ms-transform: translateX(26px);
                transform: translateX(26px);
            }

            /* Rounded sliders */
            .slider.round {
                border-radius: 34px;
            }

            .slider.round:before {
                border-radius: 50%;
            }

            .swal2-styled {
                padding: 6.250px 25px 6.250px 11px !important;
            }

            .error {
                color: red;
                display: none;
            }
        </style>
    </head>

    <body>
        <%@include file="/layout/header.jsp"%>

        <section class="postDetail">
            <h1 class="heading text-center">New Subject</h1>

            <div class="container">
                <div class="row">
                    <div class="col-md-2"></div>
                    <div class="col-md-3">
                        <img src="./images/no_img.jpg" alt="Ảnh Subject" class="img-responsive" id="avatarImage">
                        <br>
                        <button type="button" class="btn btn-primary btn-sm" id="uploadButton">
                            <i class="fa fa-upload"></i> Upload Image
                        </button>
                        <!-- Input để chọn file ảnh, không hiển thị cho người dùng -->
                        <input accept="image/png, image/jpeg" type="file" id="fileInput" style="display: none;">
                    </div>
                    <div class="col-md-1"></div>

                    <div class="col-md-6">
                        <form id="newSubject" action="newSubject" method="POST">
                            <input type="hidden" id="img" name="img" value="./images/no_img.jpg">
                            <div class="form-group">
                                <label for="name">Name:</label>
                                <input onkeyup="validateSubjectName()" required type="text" id="name" name="name" class="form-control" placeholder="Enter subject name">
                                <span id="error-message" class="error">Please enter a valid subject name.</span>
                            </div>

                            <div class="form-group">
                                <label for="category">Category:</label>
                                <select required id="category" name="dimensionId" class="form-control">
                                    <option value="" disabled selected>Select category</option>
                                    <c:forEach var="dimension" items="${dimensions}">
                                        <option value="${dimension.id}">${dimension.name}</option>
                                    </c:forEach>
                                </select>
                                <span id="error-message-category" class="error">Please select a category.</span>
                            </div>

                            <div class="form-group">
                                <label>Feature Subject</label>
                                <br>
                                <label class="switch">
                                    <input id="feature" type="checkbox" name="feature">
                                    <span class="slider round"></span>
                                </label>
                            </div>

                            <div class="form-group">
                                <label for="experts">Owner:</label>
                                <select required id="experts" name="expertId" class="form-control">
                                    <option value="" disabled selected>Select owner</option>
                                    <c:forEach var="expert" items="${experts}">
                                        <option value="${expert.id}">${expert.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="statusSubject">Status:</label>
                                <select required id="statusSubject" name="statusId" class="form-control">
                                    <option value="" disabled selected>Select status</option>
                                    <option value="1">Published</option>
                                    <option value="2">Unpublished</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="description">Description: </label>
                                <textarea onkeyup="validateDescription()" required id="description" name="description" class="form-control" rows="5" placeholder="Enter description"></textarea>
                                <span id="error-message-description" class="error">Please enter a description.</span>
                            </div>
                            <button type="submit" class="btn btn-primary btn-sm col-md-2">Save</button>
                            <div class="col-md-1"></div>
                            <a href="/QuizPractice/subjectManager" class="btn btn-primary btn-sm col-md-2">Back to list</a>
                        </form>
                    </div>


                </div>
            </div>
        </section>
        <br/>

        <%@include file="/layout/footer.jsp" %>

        <%@include file="/layout/script.jsp" %>

        <script src="js/ChangeStatusOfSubject.js"></script>

        <!-- Script để xử lý upload ảnh -->
        <script>
                                    document.getElementById('uploadButton').addEventListener('click', function () {
                                        document.getElementById('fileInput').click();
                                    });

                                    document.getElementById('fileInput').addEventListener('change', function () {
                                        var file = this.files[0];
                                        var formData = new FormData();
                                        var allowedTypes = ['image/jpeg', 'image/png'];
                                        if (allowedTypes.indexOf(file.type) === -1) {
                                            Swal.fire({
                                                title: "Only allowed to upload image formats: JPEG, PNG, GIF",
                                                icon: "error"
                                            }).then(() => {
                                                // Reset giá trị của input file để người dùng có thể chọn tệp khác
                                                document.getElementById('fileInput').value = "";
                                            });
                                            return;
                                        }
                                        formData.append('file', this.files[0]);

                                        var xhr = new XMLHttpRequest();
                                        xhr.open('POST', 'upload', true);
                                        xhr.onload = function () {
                                            if (xhr.status === 200) {
                                                var response = JSON.parse(xhr.responseText);
                                                var fileName = response.fileName;
                                                var avatarImage = document.getElementById('avatarImage');
                                                avatarImage.src = 'images/' + fileName; // Thay đổi src của hình ảnh avatarImage
                                                console.log(fileName);

                                                // Cập nhật giá trị của hidden input để lưu vào database
                                                document.getElementById('img').value = 'images/' + fileName;
                                            }
                                        };
                                        xhr.send(formData);
                                    });
        </script>

        <script src="js/validateSubject.js"></script>
        <!-- Bootstrap Toggle JS and CSS (optional) -->
        <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
        <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
    </body>
</html>