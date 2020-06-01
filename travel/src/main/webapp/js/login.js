$(function () {

    $("#btn_sub").click(function () {
        $.post("user/login",$("#loginForm").serialize(),function (data) {
            if(data.flag){
                location.href = "index.html";
            }else {
                alert(data.flag)
                alert(data.errorMsg)
                $("#errorMsg").html(data.errorMsg);
            }
        })
    })

})