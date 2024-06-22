function getPage(page, index) {
    console.log(index);
    $.ajax({
        type: 'POST',
        url: "subjectManager",
        data: {
            page: index
        }, success: function (data) {
            $('#content').html(data);
        }
    });
}