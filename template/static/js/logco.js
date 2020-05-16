var log_lib = {

    login: function (nick_name, pwd) {
        $.ajax({
            url: "/api/mms/user/auth",
            method: "POST",
            contentType: "application/json",
            data : JSON.stringify({
                "nickName": nick_name,
                "pwd": pwd
            }),
            success: function (rsp) {
                if (rsp.code == 0) {
                    alert("登录成功");
                    localStorage.setItem("auth_token", rsp.data.token);
                } else {
                    alert(rsp.msg);
                }
            },
            error: function () {
                alert("密码错误");
            }
        });
    },

    head_token: "mms-auth-token"
}