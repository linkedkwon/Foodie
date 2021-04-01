//email inquiry
function onClickInquiryEmail(){
    var name = document.getElementById("user-name").value;
    var phone_num = document.getElementById("user-phone").value.replace(/(^02.{0}|^01.{1}|[0-9]{3)([0-9]+)([0-9]{4})/,"$1-$2-$3");

    var name_flag = false;
    var phone_flag = false;

    if(name.length == 0){ $('#error-name-msg').text("이름을 입력해주세요"); name_flag = true;}
    if(phone_num.length == 0 ){ $('#error-phone-msg').text("휴대전화 번호를 입력해주세요"); phone_flag = true;}
    if(name_flag || phone_flag) return;


    $.ajax({
        url: '/help/inquiry/id',
        type: 'POST',
        data : {
            name:name,
            phoneNum:phone_num
        },
        success: function(data){
            if(data == 1){
                $('#error-inquiry-msg').text("이름과 휴대전화 번호가 일치하지 않습니다");
            }
            else{
                document.getElementById("default-section1").style.display = "none";
                document.getElementById("default-section2").style.display = "none";
                document.getElementById("success-section").style.display = "block";
                document.getElementById("success-text").innerText=data;
            }
        },
        error: function(status, error){
            console.log(status, error);
        }
    });
}


//pswd inquiry
function onClickInquiryPswd(){
    var email = document.getElementById("user-email").value;
    var phone_num = document.getElementById("user-phone").value.replace(/(^02.{0}|^01.{1}|[0-9]{3)([0-9]+)([0-9]{4})/,"$1-$2-$3");

    var error_msg = document.getElementById("error-inquiry-msg");
    var send_msg = document.getElementById("send-msg")

    var email_flag = false;
    var phone_flag = false;

    if(email.length == 0){ $('#error-email-msg').text("이메일을 입력해주세요"); email_flag = true;}
    if(phone_num.length == 0 ){ $('#error-phone-msg').text("휴대전화 번호를 입력해주세요"); phone_flag = true;}
    if(email_flag || phone_flag) return;

    $.ajax({
        url: '/help/inquiry/pw',
        type: 'POST',
        data : {
            email:email,
            phoneNum:phone_num
        },
        success: function(data){
            if(data == 1){
                error_msg.style.color = "#FF0000";
                error_msg.innerText="이메일과 휴대전화 번호가 일치하지 않습니다.";
            }
            else{
                document.getElementById("success-section").style.display = "block";
                document.getElementById("section-860").style.height = 470+"px";
                document.getElementById("user-email").disabled=true;
                document.getElementById("user-phone").disabled=true;
                document.getElementById("success-section").style.display = "block";
                send_msg.style.color = "#440fd3";
                send_msg.innerText = "입력하신 메일로 6자리 인증코드가 발송되었습니다. \(유효시간 10분\)";
            }
        },
        error: function(status, error){
            console.log(status, error);
        }
    });
}

function submitCode(){
    var code = document.getElementById("receive-code").value;
    var msg = document.getElementById("error-code-msg");

    if(code.length !=6){
        document.getElementById("section-860").style.height = 490+"px";
        document.getElementById("error-code-box").style.display="block";
        msg.innerText ="인증코드를 다시 확인해주세요.";
        return;
    }

    var email = document.getElementById("user-email").value;
    var code = document.getElementById("receive-code").value;

    $.ajax({
        url: '/help/inquiry/code',
        type: 'POST',
        data : {
            email: email,
            code: code
        },
        success: function(data){
            if(data == 1){
                console.log("실패");
                document.getElementById("section-860").style.height = 490+"px";
                document.getElementById("error-code-box").style.display="block";
                msg.innerText="인증코드가 일치하지 않습니다.";
            }
            else{
                console.log("성공");
                window.location.href = "/help/reset?email="+data;
            }
        },
        error: function(status, error){
            console.log(status, error);
        }
    });
}

function checkPassword(){

    var reg = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{4,15}$/;
    var pswd1 = document.getElementById("user-password1").value;
    var pswd1_msg = document.getElementById("error-password1-msg");

    if(pswd1.length == 0){ pswd1_msg.innerText = "필수 정보입니다."; return false;}
    if(reg.test(pswd1) == false){ pswd1_msg.innerText = "4~15자의 영문자, 숫자조합을 사용하세요."; return false;}
    pswd1_msg.innerText = "";

    //change with pswd-match msg
    var pswd2 =  document.getElementById("user-password2").value;
    var pswd2_msg = document.getElementById("error-password2-msg");

    if(pswd1 == pswd2 || pswd2.length == 0) {pswd2_msg.innerText = "";}
    else{ pswd2_msg.innerText = "비밀번호가 일치하지 않습니다.";}

    return true;
}

function matchPassword() {

    var pswd1 = document.getElementById("user-password1").value;
    var pswd2 = document.getElementById("user-password2").value;
    var pswd2_msg = document.getElementById("error-password2-msg");

    if(pswd1 != pswd2){ pswd2_msg.innerText = "비밀번호가 일치하지 않습니다."; return false;}

    pswd2_msg.innerText = "";
    return true;
}

function submitPassword(){

    var list = [checkPassword(), matchPassword()];
    if(list.includes(false)) return;

    console.log("성공");
    const urlParams = new URLSearchParams(window.location.search);
    const data = urlParams.get('email');
    document.getElementById("qs").value = data;

    const form = document.getElementById("password-reset-form");
    form.submit();
}

function oninputName(){$('#error-name-msg').text("");$('#error-inquiry-msg').text("");}
function oninputEmail(){$('#error-email-msg').text("");$('#error-inquiry-msg').text("");}
function oninputPhone(){$('#error-phone-msg').text("");$('#error-inquiry-msg').text("");}
function oninputCode(){
    $('#error-code-msg').text("");
    document.getElementById("error-code-box").style.display="none";
    document.getElementById("section-860").style.height = 470+"px";
}
function oninputPassword1(){$('#error-password1-msg').text("");}
function oninputPassword2(){$('#error-password2-msg').text("");}