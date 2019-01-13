function logout() {
    $.ajax({
        url: '/logout',
        type: 'post',
        success: function (result) {
            if (result.success) {
                $.session.remove('userId');
                $.session.remove('username');
                $.session.remove('mapId');
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