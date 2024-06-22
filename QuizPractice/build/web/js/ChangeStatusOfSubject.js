function changeStatus(element, subjectId) {
    var currentStatus = $(element).html();
    if (currentStatus === "Published") {
        $(element).attr('class', 'btn btn-danger btn-sm');
        $(element).html('Unpublished');
        var formData = {
            subjectId: subjectId,
            statusId: 2,
        };
        $.ajax({
            type: "POST",
            url: "changeSubjectStatus",
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
                } else {
                    Swal.fire({
                        title: data.message,
                        icon: "error"
                    });
                }
            }
        });
    } else if (currentStatus === "Unpublished") {
        $(element).attr('class', 'btn btn-info btn-sm');
        $(element).html('Published');
        var formData = {
            subjectId: subjectId,
            statusId: 1,
        };
        $.ajax({
            type: "POST",
            url: "changeSubjectStatus",
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
                } else {
                    Swal.fire({
                        title: data.message,
                        icon: "error"
                    });
                }
            }
        });
    }
}

$("#newSubject").submit(function (e) {
    e.preventDefault();
    const nameInput = document.getElementById('name');
    const subjectName = nameInput.value;
    const descriptionTextarea = document.getElementById('description');
    const description = descriptionTextarea.value.trim();
    const regex = /^[A-Za-z0-9\s]{3,50}$/;
    if (!regex.test(subjectName.trim()) || description === "") {
        Swal.fire({
            title: "Hey bro please fill all field of subject!!!!",
            icon: "error"
        });
        return;
    }
    var formData = {
        img: $("#img").val(),
        name: $("#name").val().trim(),
        dimensionId: $("#category").val(),
        feature: $("#feature").val(),
        expertId: $("#experts").val(),
        statusId: $("#statusSubject").val(),
        description: $("#description").val()
    };
    console.log(formData);
    $.ajax({
        type: "POST",
        url: "newSubject",
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
            } else {
                Swal.fire({
                    title: data.message,
                    icon: "error"
                });
            }
        }
    });
});

$("#postUpdateForm").submit(function (e) {
    e.preventDefault();
    var formData = {
        id: $("#id").val(),
        thumbnail: $("#thumbnail").val(),
        title: $("#title").val(),
        briefinfo: $("#brief").val(),
        categoryName: $("#category").val(),
        content: $("#content").val(),
        status: $("#status").prop("checked") ? "on" : "off"  // Check the status of the checkbox
    };
    const forwardUrl = "blogdetail?pid=" + $("#id").val();
    $.ajax({
        type: 'POST',
        url: 'postdetail',
        data: formData,
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
        }
    });
});

function validateSubjectName() {
    const nameInput = document.getElementById('name');
    const errorMessage = document.getElementById('error-message');
    const subjectName = nameInput.value;

    // Regular expression to allow only letters and spaces (minimum 3 characters, maximum 50 characters)
    const regex = /^[A-Za-z0-9\s]{3,50}$/;

    if (regex.test(subjectName.trim())) {
        errorMessage.style.display = 'none';
        // Here you can proceed with form submission or further actions
    } else {
        errorMessage.style.display = 'block';
    }
}

function validateDescription() {
    const descriptionTextarea = document.getElementById('description');
    const errorMessage = document.getElementById('error-message-description');
    const description = descriptionTextarea.value.trim(); // Trim whitespace

    if (description === "") {
        errorMessage.style.display = 'block';
    } else {
        errorMessage.style.display = 'none';
    }
}

$(document).ready(function () {
    $("#userName").on('input', function () {
        validateUserName(this);
    });

    $("#registerSubjectForm").submit(function (e) {
        e.preventDefault();

        // Validate username one more time before submitting
        var userNameInput = $("#userName")[0];
        validateUserName(userNameInput);

        if (userNameInput.checkValidity()) {
            $.ajax({
                type: 'POST',
                url: 'subjectRegister',
                data: $(this).serialize(),
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
    });
});