$(function () {

    $("#registerForm").submit(function () {
        if(checkUsername() && checkPassword() ){
            $.post("user/register",$("#registerForm").serialize(),function (data) {
                if(data.flag){
                    location.href = "register_ok.html";
                }else{
                    $("#check_hint").html(data.errorMsg);
                }
            });
        }
        return false;
    })

    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkTelephone);
    $("#birthday").blur(checkBirthday);
    $("#check").blur(checkCheck);
})

function checkUsername() {
    var username = $("#username").val();
    var reg_username = /^\w{8,20}$/;
    var flag = reg_username.test(username);
    var username_hint = $("#username_hint")[0];




    if(flag){
        username_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
        return true;
    }
    if(!flag){
        username_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
        return false;
    }
    return flag;
}

function checkPassword() {
    var password = $("#password").val();
    var reg_password = /^\w{8,20}$/;
    var flag = reg_password.test(password);
    var password_hint = $("#password_hint").get(0);
    if(flag){
        password_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
    }
    if(!flag){
        password_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
    }
    return flag;
}

function checkEmail() {
    var email = $("#email").val();
    var reg_email = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    var flag = reg_email.test(email);
    var email_hint = document.getElementById("email_hint");
    if(flag){
        email_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
        return false;
    }
    if(!flag){
        email_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
        return false;
    }
    return flag;
}

function checkName() {
    var name = $("#name").val();
    var reg_name = /^[\u2E80-\u9FFF]+$/;
    var flag = reg_name.test(name);
    var name_hint = document.getElementById("name_hint");
    if(flag){
        name_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
    }
    if(!flag){
        name_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
    }
    return flag;
}

function checkTelephone() {
    var telephone = $("#telephone").val();
    var reg_telephone = /^1[3456789]\d{9}$/;
    var flag = reg_telephone.test(telephone);
    var telephone_hint = $("#telephone_hint")[0];
    if(flag){
        telephone_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
    }
    if(!flag){
        telephone_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
    }
    return flag;
}

function checkBirthday() {
    var birthday_hint = $("#birthday_hint").get(0);
    var date1=$("#birthday").val();
    var y1=date1.split("-")[0];
    var m1=date1.split("-")[1];
    var d1=date1.split("-")[2];
    if(y1!=null&&m1!=null&&d1!=null){
        birthday_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
    }else {
        birthday_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
    }

}

function checkCheck() {
    var check = $("#check").val();
    var check_hint = $("#check_hint").get(0);
    if (check != null){
        check_hint.innerHTML = "<img src='img/right.PNG' width='20px' height='20px'>"
        return false;
    }
    if (check == null){
        check_hint.innerHTML = "<img src='img/error.PNG' width='20px' height='20px'>"
        return true
    }
}

