<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý blog</title>

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
            .slider.round {
                border-radius: 34px;
            }
            .slider.round:before {
                border-radius: 50%;
            }
            .swal2-styled {
                padding: 6.250px 25px 6.250px 11px !important;
            }
        </style>
    </head>

    <body>
        <%@include file="/layout/header.jsp"%>


        <section class="postDetail">
            <h1 class="heading text-center">Blog Detail Update</h1>
            <div class="container">
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-3">
                        <img src="<c:out value='${blog.thumbnail}'/>" alt="Ảnh Blog" class="img-responsive" id="avatarImage">
                        <br>
                        <button type="button" class="btn btn-primary btn-sm" id="uploadButton">
                            <i class="fa fa-upload"></i> Upload image
                        </button>
                        <!-- Input để chọn file ảnh, không hiển thị cho người dùng -->
                        <input accept="image/png, image/jpeg" type="file" id="fileInput" style="display: none;">
                    </div>
                        
                    <div class="col-md-2"></div>
                    
                    <div class="col-md-6">
                        <form action="postdetail" method="post" id="postUpdateForm">
                            <input type="hidden" id="id" name="id" value="<c:out value='${blog.id}'/>">
                            <input type="hidden" id="thumbnail" name="thumbnail" value="<c:out value='${blog.thumbnail}'/>">
                            <div class="form-group">
                                <label for="title">Title:</label>
                                <input type="text" id="title" required name="title" class="form-control" value="<c:out value='${blog.title}'/>" placeholder="Nhập tiêu đề">
                            </div>

                            <div class="form-group">
                                <label for="brief">Brie Info:</label>
                                <textarea id="brief" name="briefinfo" required class="form-control" rows="3" placeholder="Nhập thông tin ngắn gọn"><c:out value='${blog.briefinfo}'/></textarea>
                            </div>

                            <div class="form-group">
                                <label for="category">Category:</label>
                                <select id="category" name="categoryName" class="form-control">
                                    <option disabled>Choose category</option>
                                    <c:forEach var="category" items="${listCategory}">
                                        <option value="${category.category_Name}" <c:if test="${blog.categoryName == category.category_Name}">selected</c:if>>${category.category_Name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="content">Content:</label>
                                <textarea id="content" name="content" required class="form-control" rows="5" placeholder="Nhập nội dung"><c:out value='${blog.content}'/></textarea>
                            </div>

                            <div class="form-group">
                                <label for="status">Status:</label>
                                <br/>
                                <label class="switch">
                                    <input type="checkbox" id="status" name="status" <c:if test="${blog.status}">checked</c:if>>
                                        <span class="slider round"></span>
                                    </label>
                                </div>
                                <button type="submit" class="btn btn-primary btn-sms">Save</button>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
                                        
            <br/>

        <%@include file="/layout/footer.jsp" %>

        <!-- Script để xử lý upload ảnh -->
        <script>
            document.getElementById('uploadButton').addEventListener('click', function () {
                document.getElementById('fileInput').click();
            });

            document.getElementById('fileInput').addEventListener('change', function () {
                var file = this.files[0];
                var formData = new FormData();

                // Kiểm tra định dạng tệp
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

                formData.append('file', file);

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
                        document.getElementById('thumbnail').value = 'images/' + fileName;

                        // Thông báo cập nhật thành công
                        Swal.fire({
                            title: "Image uploaded successfully",
                            icon: "success"
                        });
                    } else {
                        // Thông báo lỗi khi upload thất bại
                        Swal.fire({
                            title: "Image upload failed",
                            icon: "error"
                        });
                    }
                };
                xhr.send(formData);
            });
        </script>

        <%@include file="layout/script.jsp" %>
        <script src="js/ChangeStatusOfSubject.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.js" integrity="sha512-+k1pnlgt4F1H8L7t3z95o3/KO+o78INEcXTbnoJQ/F2VqDVhWoaiVml/OEHv9HsVgxUaVW+IbiZPUJQfF/YxZw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>
</html>