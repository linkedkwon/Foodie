function checkFormBeforeLogin(){

    var email = document.getElementById("input-email");
    var pswd = document.getElementById("input-pswd");
    var email_flag = false;
    var pswd_flag = false;

    if(email.value.length == 0) { $('#email-msg').text("아이디를 입력해주세요"); email_flag = true;}
    if(pswd.value.length == 0) { $('#pswd-msg').text('비밀번호를 입력해주세요'); pswd_flag = true;}
    if(email_flag || pswd_flag)
        return;

    const form = document.getElementById("sign-in-form");
    form.submit();
}

function oninputEmail(){$('#email-msg').text('');}
function oninputPswd(){$('#pswd-msg').text('');}
