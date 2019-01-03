
function verifyLoginInfo(name, pwd) {
    if (!name || name === "") {
        return {
            success: false,
            message: "姓名不能为空",
            data: null
        };
    }

    if (!pwd || pwd === "") {
        return {
            success: false,
            message: "密码不能为空",
            data: null
        };
    }

    return {
        success: true,
        message: "输入信息符合规范",
        data: 'MANAGER'
    };
}