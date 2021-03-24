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
        url: '/auth/inquiry/id',
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
        url: '/auth/inquiry/pw',
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
                document.getElementById("section-860").style.height = 450+"px";
                document.getElementById("user-email").disabled=true;
                document.getElementById("user-phone").disabled=true;
                document.getElementById("success-section").style.display = "block";
                send_msg.style.color = "#440fd3";
                send_msg.innerText = "입력하신 메일로 6자리 인증번호가 발송되었습니다.";
            }
        },
        error: function(status, error){
            console.log(status, error);
        }
    });
}

function oninputName(){$('#error-name-msg').text("");$('#error-inquiry-msg').text("");}
function oninputEmail(){$('#error-email-msg').text("");$('#error-inquiry-msg').text("");}
function oninputPhone(){$('#error-phone-msg').text("");$('#error-inquiry-msg').text("");}