function logout() {
    $.ajax({
        url: '/logout',
        type: 'post',
        success: function (result) {
            if (result.success) {
                window.location= '/login';
            } else {
                showTip($('#tip'), result.message, 'white');
            }
        },
        error: function (message) {
            showTip($('#tip'), message, 'white');
        }
    });
}