//email validation checked value, user type
var validationEmailChecked = false;
var validationPhoneChecked = false;

document.getElementById("user-type1").value = "0";
document.getElementById("user-type2").value = "1";

//check name regular expression
function checkName(){

    var reg = /^[가-힣a-zA-Z]{2,15}$/;
    var name = document.getElementById("user-name").value;
    var msg = document.getElementById("name-msg");

    if(name.length == 0) {msg.innerText = "필수 정보입니다."; return false;}
    if(reg.test(name) == false){msg.innerText = "2~15자 이내의 한글 또는 영문을 사용하세요. (특수기호, 공백, 숫자 사용 불가)"; return false;}

    msg.innerText = "";
    return true;
}

//check email regular expression and validation
function checkMail(){

    var email = document.getElementById("user-email").value;

    if(email.length == 0){document.getElementById("email-msg").innerText = "필수 정보입니다."; return false;}

    return true;
}

function checkMailValidation(){

    var reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var email = document.getElementById("user-email");
    var msg = document.getElementById("email-msg");

    if(reg.test(email.value) == false) {
        msg.style.color = "#FF0000";
        msg.innerText = "이메일 주소를 다시 확인해주세요.";
        return;
    }

    //asynchronous send
    $.ajax({
        url: '/auth/check/email/'+email.value,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data == 1){
                msg.style.color = "#FF0000";
                msg.innerText = "이미 사용 중인 메일입니다.";
            }
            else{
                msg.style.color = "#440fd3";
                msg.innerText = "사용 가능한 주소입니다.";
                validationEmailChecked = true;
                console.log(data);
            }
        },
        error: function (status, error) {
            console.log(status, error);
        }
    });
}

//check pswd1 regular expression
function checkPassword(){

    var reg = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{4,15}$/;
    var pswd1 = document.getElementById("user-pswd1").value;
    var pswd1_msg = document.getElementById("pswd-msg1");

    if(pswd1.length == 0){ pswd1_msg.innerText = "필수 정보입니다."; return false;}
    if(reg.test(pswd1) == false){ pswd1_msg.innerText = "4~15자의 영문자, 숫자조합을 사용하세요."; return false;}
    pswd1_msg.innerText = "";

    //change with pswd-match msg
    var pswd2 =  document.getElementById("user-pswd2").value;
    var pswd2_msg = document.getElementById("pswd-msg2");

    if(pswd1 == pswd2 || pswd2.length == 0) {pswd2_msg.innerText = "";}
    else pswd2_msg.innerText = "비밀번호가 일치하지 않습니다.";

    return true;
}

//check pswd2 regular expression and matcher to pswd1
function matchPassword(){

    var pswd1 = document.getElementById("user-pswd1").value;
    var pswd2 = document.getElementById("user-pswd2").value;
    var pswd2_msg = document.getElementById("pswd-msg2");

    if(pswd1 != pswd2){ pswd2_msg.innerText = "비밀번호가 일치하지 않습니다."; return false;}

    pswd2_msg.innerText = "";
    return true;
}

//check telephone regular expression
function checkTel(){

    //replace hyphen to space
    var tel = document.getElementById("user-tel").value.replace(/\-/g,'');
    var reg = /^\d{2,3}\d{3,4}\d{4}$/;
    var msg = document.getElementById("tel-msg");

    if(tel.length == 0){ msg.innerText = ""; return true;}
    if(reg.test(tel) ==  false){ msg.innerText = "형식에 맞지 않는 번호입니다."; return false;}
    msg.innerText = "";

    //Adding hyphen to input value
    document.getElementById("user-tel").value = tel.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
    return true;
}

//check cellphone regular expression
function checkPhone(){

    //replace hyphen to space
    var phone = document.getElementById("user-phone").value.replace(/\-/g,'');
    var reg = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
    var msg = document.getElementById("phone-msg");

    if(phone.length == 0){return false;}
    if(reg.test(phone) ==  false){ msg.innerText ="형식에 맞지 않는 번호입니다."; return false;}


    //Adding hyphen to value
    document.getElementById("user-phone").value = phone.replace(/(^02.{0}|^01.{1}|[0-9]{3)([0-9]+)([0-9]{4})/,"$1-$2-$3");
    return true;
}

function checkPhoneValidation(){

    if(checkPhone() == false)
        return;

    var phoneNum = document.getElementById("user-phone")
        .value.replace(/(^02.{0}|^01.{1}|[0-9]{3)([0-9]+)([0-9]{4})/,"$1-$2-$3");
    var msg = document.getElementById("phone-msg");

    $.ajax({
        url: '/auth/check/phone/'+phoneNum,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data == 1){
                msg.style.color = "#FF0000";
                msg.innerText = "이미 사용 중인 번호입니다.";
            }
            else{
                msg.style.color = "#440fd3";
                msg.innerText = "사용 가능한 번호입니다.";
                validationPhoneChecked = true;
                console.log(data);
            }
        },
        error: function (status, error) {
            console.log(status, error);
        }
    });
}

//Access Daum post-code api
function execDaumPostcode() {

    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById("post-code").value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("detail-address").disabled = false;
            document.getElementById("detail-address").focus();
            document.getElementById("address-msg").innerText = "";
        }
    }).open();
}

//check address inserted
function checkAddress(){

    var msg = document.getElementById("address-msg");

    if(document.getElementById("post-code").value.length == 0
        || document.getElementById("address").value.length == 0)
        {msg.innerText = "우편번호 찾기를 완료해주세요"; return false;}
    if(document.getElementById("detail-address").value.length == 0)
        {msg.innerText = "상세주소를 입력해주세요"; return false;}

    return true;
}

//check receive information clicked
function checkInfoReceived(){

    var email_radio = document.getElementsByName("email_receiving");
    var sns_radio = document.getElementsByName("sns_receiving");
    var msg1 = document.getElementById("mail-receiving-msg");
    var msg2 = document.getElementById("sns-receiving-msg");
    var email_flag = false;
    var sns_flag = false;

    var emailChecked = $('input[name=email_receiving]').is(':checked');

    if(emailChecked) email_flag = true;

    if(email_flag) msg1.innerText = "";
    else msg1.innerText = "수신 여부를 체크해주세요.";

    var snsChecked = $('input[name=sns_receiving]').is(':checked');
    if(snsChecked) sns_flag = true;

    if(sns_flag) msg2.innerText = "";
    else msg2.innerText = "수신 여부를 체크해주세요.";

    return email_flag && sns_flag;
}

//compose address distributed 3 parts
function composeAddress(){
    document.getElementById("detail-address").value =
        document.getElementById("post-code").value + "+" +
        document.getElementById("address").value + "+" +
        document.getElementById("detail-address").value;
}

//convert radio value to string
function convertRadioToVO(){
    document.getElementById("email-receiving-value").value = $('input[name=email_receiving]:checked').val();
    document.getElementById("sns-receiving-value").value = $('input[name=sns_receiving]:checked').val();
}

/**
 * form data invalidate check
 * Radio button handled on this
 */
function checkFormBeforeSubmit(){
    var list = [checkName(), checkMail(), checkPassword(),
                matchPassword(), checkTel(), checkPhone(),
                checkAddress(), checkInfoReceived()];
    var emailMsg = document.getElementById("email-msg");
    var phoneMsg = document.getElementById("phone-msg");

    //email phone validation checked
    var flagCnt = 0;

    if(validationEmailChecked == false){
        emailMsg.style.color = "#FF0000";
        emailMsg.innerText = "이메일 중복을 확인해주세요";
        flagCnt++;
    }

    if(validationPhoneChecked == false){
        phoneMsg.style.color = "#FF0000";
        phoneMsg.innerText = "휴대전화 중복을 확인해주세요";
        flagCnt++;
    }

    if(flagCnt > 0)
        return;

    //data checked
    for(var i=0; i<list.length;i++){
        if(list[i] == false)
            return;
    }
    //compose address[post-code + address + detail address] and convert radio value to string
    composeAddress();
    convertRadioToVO();

    //submit data to handler
    const form = document.getElementById("sign-up-form");
    form.submit();
}

//oninput methods to delete error-msg on typing
function oninputName(){$('#name-msg').text('');}
function oninputEmail(){$('#email-msg').text(''); validationEmailChecked = false;}
function oninputPswd1(){$('#pswd-msg1').text('');}
function oninputPswd2(){$('#pswd-msg2').text('');}
function oninputTel(){$('#tel-msg').text('');}
function oninputPhone(){$('#phone-msg').text(''); validationPhoneChecked = false;}
function oninputAddress(){$('#address-msg').text('');}
//radio button click event
function clickMailBtn(){document.getElementById("mail-receiving-msg").innerText = "";}
function clickSNSBtn(){document.getElementById("sns-receiving-msg").innerText = "";}