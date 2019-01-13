
function showTip(tip, message, color) {
    tip.css({
        'display': 'block',
        'font-size': '1.5rem',
        'color': color ? color : '#EF7865',
        'position': 'fixed',
        'top': '8rem',
        'right': '4rem'
    });
    tip.text(message);

    setTimeout(function () {
        tip.css('display', 'none');
    }, 2000);
}

function parseQueryString(url) {
    const params = url.split("&");
    const map = {};
    for (let i = 0; i < params.length; ++i) {
        const pair = params[i].split("=");
        map[pair[0]] = pair[1];
    }
    return map;
}