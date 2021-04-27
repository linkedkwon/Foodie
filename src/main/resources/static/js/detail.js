
/** 찜하기 기능
 * add FavoriteShop
 * ajax data 1 : success, 0 : fail(not authenticated)
 */
function addFavoriteShop(){
    //shop_id extracted on url
    const url = document.location.href;
    const shopId = url.substring(url.lastIndexOf('/') + 1);

    $.ajax({
        url: '/user/favorite/item/'+shopId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            if(data == 1){
                alert('목록에 추가하였습니다.');
            }
            else{
                alert('로그인 후에 찜해주세요.');
            }
        },
        error: function (status, error) {
            alert('로그인 후에 찜해주세요.');
        }
    });
}

function addReview(){
    if(checkBeforeAddReview()!=true) {
        alert("내용을 다시 한 번 확인해주세요.");
        return;
    }
    //shop_id extracted on url
    const url = document.location.href;
    const shopId = url.substring(url.lastIndexOf('/') + 1);

    var starRating = document.getElementById("star-rating").value;
    var content = document.getElementById("comment").value;

    $.ajax({
        url: '/user/review/item',
        type: 'POST',
        data: {
            shopId:shopId,
            starRating:starRating,
            content:content
        },
        success: function (data) {
            if(data == 1){
                alert('댓글을 추가하였습니다.');
                window.location.href = window.location.href;
            }
            else{
                alert('로그인 후에 댓글을 작성해주세요.');
            }
        },
        error: function (status, error) {
            alert('로그인 후에 댓글을 작성해주세요.');
        }
    });
}
function clickStarRating(value, idx){
    document.getElementById("star-rating").value = value;
    const classList = ['point5', 'point4_5', 'point4',
                  'point3', 'point2'];
    const list = $(".btn-score-area > a");

    for(var i=0; i< list.length; i++) {
        if(idx == i){
            list[i].className = classList[i]+"-click";
            continue;
        }
        list[i].className = classList[i];
    }
}

function checkBeforeAddReview(){
    var starRating = document.getElementById("star-rating").value;
    var comment = document.getElementById("comment").value;

    var starFlag = true;
    var commentFlag = true;

    if(starRating == 0) starFlag = false;
    if(comment.length < 5) commentFlag = false;

    return starFlag && commentFlag;
}