//check name regular expression
function checkName(){

    var reg = /^[가-힣]{2,15}$/;
    var name = document.getElementById("user-name").value;
    var msg = document.getElementById("name-msg");

    if(name.length == 0) {msg.innerText = "필수 정보입니다."; return false;}
    if(reg.test(name) == false){msg.innerText = "2~15자 이내의 한글을 사용하세요. (영문, 특수기호, 공백, 숫자 사용 불가)"; return false;}

    msg.innerText = "";
    return true;
}

//check pswd1 regular expression
function checkPassword(){

    var reg = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{4,15}$/;
    var pswd1 = document.getElementById("user-pswd1").value;
    var pswd1_msg = document.getElementById("pswd-msg1");

    if(pswd1.length == 0){ return true;}
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
    var phone = document.getElementById("user-phone").value.replace(/\-/g,'');;
    var reg = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
    var msg = document.getElementById("phone-msg");

    if(phone.length == 0){ msg.innerText ="필수 정보입니다."; return false;}
    if(reg.test(phone) ==  false){ msg.innerText ="형식에 맞지 않는 번호입니다."; return false;}
    msg.innerText = "";

    //Adding hyphen to value
    document.getElementById("user-phone").value = phone.replace(/(^02.{0}|^01.{1}|[0-9]{3)([0-9]+)([0-9]{4})/,"$1-$2-$3");
    return true;
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
function composeAddress(){
    var address = document.getElementById("composed-address");
    var composed = document.getElementById("post-code").value + "+"
        + document.getElementById("address").value+ "+"
        + document.getElementById("detail-address").value;

    address.value = composed;
}
function setReceivingInfo(){
    var emailValue = document.getElementById("email-receiving-value");
    var snsValue = document.getElementById("sns-receiving-value");

    if($('input:checkbox[id="email-receiving"]').is(":checked"))
        emailValue.value ="1";
    else
        emailValue.value = "0";

    if($('input:checkbox[id="sns-receiving"]').is(":checked"))
        snsValue.value = "1";
    else
        snsValue.value = "0";
}

function setEmail(){
    var email = document.getElementById("user-email");
    email.value = document.getElementById("email").value;
}

function checkFormBeforeEdit(){
    //1. 체크박스 값 히든에 박기
    //2. 주소 합성해서 넣기

    var list = [checkName(), checkPassword(),
        matchPassword(), checkTel(), checkPhone(),
        checkAddress()];

    if(list.includes(false)) return;

    composeAddress();
    setReceivingInfo();
    setEmail();

    const form = document.getElementById("form-table");
    form.submit();
}


function oninputName(){$('#name-msg').text('');}
function oninputPswd1(){$('#pswd-msg1').text('');}
function oninputPswd2(){$('#pswd-msg2').text('');}
function oninputTel(){$('#tel-msg').text('');}
function oninputPhone(){$('#phone-msg').text('');}
function oninputAddress(){$('#address-msg').text('');}
