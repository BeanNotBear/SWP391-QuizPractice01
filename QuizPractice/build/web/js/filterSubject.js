$("#filter").on('click', function() {
    const categories = $('#mutipleSelect').val().toString();
    const status = $('#status-search').val().toString();
    $.ajax({
        type: 'POST',
        url: "subjectManager",
        data: {
            categories: categories,
            status: status
        }, success: function (data) {
            $('#content').html(data);
            document.querySelector('#mutipleSelect').setEnabledOptions($('#mutipleSelect').val());
        }
    });
});