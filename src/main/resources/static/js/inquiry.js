function checkInquiryFormBeforeSubmit(){

    var title = $('#input-title').val();
    var content = $('#input-content').val();
    var flag = true;

    if(title.length < 4 || title.length > 30 || title == '제목을 입력해주세요.' || title == '제목을 다시 확인해주세요.'){
        $('#input-title').css('color','#FF0000')
        $('#input-title').val('제목을 다시 확인해주세요.');
        flag = false;
    }
    if(content.length < 10 || content.length > 400 ||
        content == '문의할 내용을 입력해주세요.' || content == '내용을 다시 확인해주세요.'){
        $('#input-content').css('color','#FF0000')
        $('#input-content').val('내용을 다시 확인해주세요.');
        flag = false;
    }
    if(flag == false) return;

    const form = document.getElementById("inquiry-form");
    form.submit();
    alert('문의를 작성하였습니다.');
}

function deleteInquiry(inquiryId){
    $.ajax({
        url: '/user/inquiry/item/'+inquiryId,
        type: 'DELETE',
        dataType: 'json',
        success: function (data) {
            if(data == 1){
                alert('정상적으로 삭제되었습니다.');
                window.location.href = "/user/inquiry"
            }
        },
        error: function (status, error) {
            console.log(status, error);
        }
    });
}

function deleteAll(){
    $.ajax({
        url: "/user/inquiry/item",
        type: 'DELETE',
        dataType: 'json',
        success: function (data) {
            if(data == 1){
                alert('정상적으로 삭제되었습니다.');
                window.location.href = "/user/inquiry";
            }
        },
        error: function (status, error) {
            console.log(status, error);
        }
    });
}

function displayDeleteAllPopUp(){ document.getElementById("all-delete-modal").style.display = "block";}
function hideDeleteAllPopUp(){ document.getElementById("all-delete-modal").style.display = "none";}

function oninputTitle(){$('#input-title').val(''); $('#input-title').css('color','#898989');}
function oninputContent(){$('#input-content').val(''); $('#input-content').css('color','#898989');}

