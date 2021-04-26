
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

    var starRating = document.getElementById("start-rating").value;
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
function clickStarRating(idx){

    document.getElementById("start-rating").value = idx;
    /**
     * cahnge img of each rating to be adding
     */
}

function checkBeforeAddReview(){
    var starRating = document.getElementById("start-rating").value;
    var comment = document.getElementById("comment").value;

    var starFlag = true;
    var commentFlag = true;

    if(starRating == 0) starFlag = false;
    if(comment.length < 5) commentFlag = false;

    return starFlag && commentFlag;
}