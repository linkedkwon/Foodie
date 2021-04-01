function checkFormBeforeLogin(){

    var checked = $('input:checkbox[id="remember-me"]').is(":checked")

    var email = document.getElementById("input-email");
    var pswd = document.getElementById("input-pswd");
    var email_flag = false;
    var pswd_flag = false;

    if(email.value.length == 0) { $('#email-msg').text("아이디를 입력해주세요"); email_flag = true;}
    if(pswd.value.length == 0) { $('#pswd-msg').text('비밀번호를 입력해주세요'); pswd_flag = true;}
    if(email_flag || pswd_flag)
        return;

    $.ajax({
        url: '/login',
        type: 'POST',
        data : {
            email:email.value,
            password:pswd.value,
            remember:checked
        },
        success: function(data){
            if(data == 1){
                $('#login-error-msg').text("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
            else{
                window.location.href = data;
            }
        },
        error: function(status, error){
            console.log(status, error);
        }
    });
}

function oninputEmail(){$('#email-msg').text(''); $('#login-error-msg').text('');}
function oninputPswd(){$('#pswd-msg').text(''); $('#login-error-msg').text('');}