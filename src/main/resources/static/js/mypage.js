//default setting
$("ul.nav-tabs2 li:first").addClass("active").show();
$(".inner:first").show();

//tab onclick function
$("ul.nav-tabs2 li").click(function(){

    if(this.className == "active")
        return false;

    $("ul.nav-tabs2 li").removeClass("active");
    $(this).addClass("active");
    $(".inner").hide();

    var activeTab = $(this).find("a").attr("href");

    $(activeTab).fadeIn();
    return false;
});