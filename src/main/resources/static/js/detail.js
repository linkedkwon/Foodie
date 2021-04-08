
/** 찜하기 기능
 * add FavoriteShop
 * ajax data 1 : success, 0 : fail(not authenticated)
 */
function addFavoriteShop(){
    //shop_id extracted on url
    const url = document.location.href;
    const shopId = url.substring(url.lastIndexOf('/') + 1);

    $.ajax({
        url: '/user/favorite/shop/'+shopId,
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