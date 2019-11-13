//加载主题
var theme = localStorage.getItem('theme');
if (theme == null || theme == undefined || theme == ''){
    theme = 'theme-0';
}
$('body').addClass(theme);

//加载字体大小
var fontSize = localStorage.getItem("fontSize");
if (fontSize == null || fontSize == undefined || fontSize == ''){
    if ($(window).width()<600){
        fontSize = 22;
    }else {
        fontSize = 30;
    }
}
fontSize = parseInt(fontSize);
$('.md-setting-font-size-view').text(fontSize);
$('.reader-content').css('font-size',fontSize+'px');
//加载字体
var fontFamily = localStorage.getItem('fontFamily');
if (fontFamily == null || fontFamily == undefined || fontFamily == ''){
    fontFamily = "'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif";
}
$('.reader-content').css('font-family',fontFamily);

//加载页面大小
var pageSize = localStorage.getItem('pageSize');
if (pageSize == null || pageSize == undefined || pageSize == ''){
    pageSize = 100;
}
pageSize = parseInt(pageSize);
$('.md-setting-page-size-view').text(pageSize+'%');
$('.content').css('width',pageSize+'%');


$(document).ready(function () {

    $(".layui-fixbar-top").click(function () {
        $('html, body').animate({scrollTop: 0}, 'slow');
    });

    $(window).scroll(function () {
        checkLayout();
    });

    checkLayout()

    var width = $('.layui-container').width();
    if (width<600){
        $('.reader-setting-md').width(width-40);
        $('.reader-content').css('margin','10px');
    }else {
        $('.reader-setting-md').width(width / 5 * 2);
        $('.reader-content').css('margin','50px');
    }

    //设置按钮
    $('.md-setting-open').click(function () {
        if ($(this).hasClass('panel-wrap')){
            $('.md-setting-close').click();
        }else {
            //设置中主题选项
            var theme = localStorage.getItem('theme');
            if (theme == null || theme == undefined || theme == ''){
                theme = 'theme-0';
            }
            $('.reader-theme .act').addClass('default').removeClass('act');
            $('.reader-theme .'+theme).removeClass('default').addClass('act');


            var fontFamily = localStorage.getItem('fontFamily');
            if (fontFamily == null || fontFamily == undefined || fontFamily == ''){
                fontFamily = "'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif";
            }
            if (fontFamily == "'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif"){
                $('.md-setting-font-family .act').addClass('default').removeClass('act');
                $('.md-setting-font-family-yahei').removeClass('default').addClass('act');
            }else if (fontFamily == "PingFangSC-Regular,'-apple-system',Simsun"){
                $('.md-setting-font-family .act').addClass('default').removeClass('act');
                $('.md-setting-font-family-song').removeClass('default').addClass('act');
            }else if (fontFamily == "Kaiti"){
                $('.md-setting-font-family .act').addClass('default').removeClass('act');
                $('.md-setting-font-family-kai').removeClass('default').addClass('act');
            }

            $('.reader-setting-md').show(100);
            $('.md-setting-open').addClass('panel-wrap');
            $('.md-setting-open').removeClass('content-wrap');
        }
    });

    $('.md-setting-close').click(function () {
        $('.reader-setting-md').hide(100);
        $('.md-setting-open').addClass('content-wrap');
        $('.md-setting-open').removeClass('panel-wrap');
    });

    bindingThemeBtn();

    bindingFontFamilyBtn()

    bindingFontSizeBtn();

    bindingPageSizeBtn()
});

function bindingThemeBtn() {

    //阅读主题按钮
    $('.reader-theme .theme-0').click(function () {
        if (!$(this).hasClass('act')){
            $('.reader-theme .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            $('body').removeClass('theme-1');
            $('body').removeClass('theme-2');
            $('body').removeClass('theme-3');
            $('body').removeClass('theme-4');
            $('body').addClass('theme-0');
            localStorage.setItem('theme','theme-0')
        }
    });
    $('div.theme-1').click(function () {
        if (!$(this).hasClass('act')){
            $('.reader-theme .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            $('body').removeClass('theme-0');
            $('body').removeClass('theme-2');
            $('body').removeClass('theme-3');
            $('body').removeClass('theme-4');
            $('body').addClass('theme-1');
            localStorage.setItem('theme','theme-1')
        }
    });
    $('div.theme-2').click(function () {
        if (!$(this).hasClass('act')){
            $('.reader-theme .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            $('body').removeClass('theme-0');
            $('body').removeClass('theme-1');
            $('body').removeClass('theme-3');
            $('body').removeClass('theme-4');
            $('body').addClass('theme-2');
            localStorage.setItem('theme','theme-2')
        }
    });
    $('div.theme-3').click(function () {
        if (!$(this).hasClass('act')){
            $('.reader-theme .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            $('body').removeClass('theme-0');
            $('body').removeClass('theme-1');
            $('body').removeClass('theme-2');
            $('body').removeClass('theme-4');
            $('body').addClass('theme-3');
            localStorage.setItem('theme','theme-3')
        }
    });
    $('div.theme-4').click(function () {
        if (!$(this).hasClass('act')){
            $('.reader-theme .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            $('body').removeClass('theme-0');
            $('body').removeClass('theme-1');
            $('body').removeClass('theme-2');
            $('body').removeClass('theme-3');
            $('body').addClass('theme-4');
            localStorage.setItem('theme','theme-4')
        }
    });
}

function bindingFontFamilyBtn() {
    $('.md-setting-font-family-yahei').click(function () {
        if (!$(this).hasClass('act')){
            $('.md-setting-font-family .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            var fontFamily = "'Microsoft YaHei', PingFangSC-Regular, HelveticaNeue-Light, 'Helvetica Neue Light', sans-serif";
            $('.reader-content').css('font-family',fontFamily);
            localStorage.setItem('fontFamily',fontFamily);
        }
    });

    $('.md-setting-font-family-song').click(function () {
        if (!$(this).hasClass('act')){
            $('.md-setting-font-family .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            var fontFamily = "PingFangSC-Regular,'-apple-system',Simsun";
            $('.reader-content').css('font-family',fontFamily);
            localStorage.setItem('fontFamily',fontFamily);
        }
    });

    $('.md-setting-font-family-kai').click(function () {
        if (!$(this).hasClass('act')){
            $('.md-setting-font-family .act').addClass('default').removeClass('act');
            $(this).addClass('act');
            var fontFamily = "Kaiti";
            $('.reader-content').css('font-family',fontFamily);
            localStorage.setItem('fontFamily',fontFamily);
        }
    });

}

function bindingFontSizeBtn() {
    $('.md-setting-font-size-smaller').click(function () {
        var size = localStorage.getItem("fontSize");
        if (size == null || size == undefined || size == ''){
            size = 30;
        }
        size = parseInt(size);
        if (size>12){
            size = size-2;
            $('.md-setting-font-size-view').text(size);
            localStorage.setItem('fontSize',size);
            $('.reader-content').css('font-size',size+'px');
        }

    });
    $('.md-setting-font-size-bigger').click(function () {
        var size = localStorage.getItem("fontSize");
        if (size == null || size == undefined || size == ''){
            size = 30;
        }
        size = parseInt(size);
        if (size<48){
            size = size+2;
            $('.md-setting-font-size-view').text(size);
            localStorage.setItem('fontSize',size);
            $('.reader-content').css('font-size',size+'px');
        }

    });
}

function bindingPageSizeBtn() {
    $('.md-setting-page-size-smaller').click(function () {
        var pageSize = localStorage.getItem('pageSize');
        if (pageSize == null || pageSize == undefined || pageSize == ''){
            pageSize = 100;
        }
        pageSize = parseInt(pageSize);
        if (pageSize>65){
            pageSize = pageSize-5;
            $('.md-setting-page-size-view').text(pageSize+'%');
            $('.content').css('width',pageSize+'%');
            localStorage.setItem('pageSize',pageSize);
        }

    });
    $('.md-setting-page-size-bigger').click(function () {
        var pageSize = localStorage.getItem('pageSize');
        if (pageSize == null || pageSize == undefined || pageSize == ''){
            pageSize = 100;
        }
        pageSize = parseInt(pageSize);
        if (pageSize<100){
            pageSize = pageSize+5;
            $('.md-setting-page-size-view').text(pageSize+'%');
            $('.content').css('width',pageSize+'%');
            localStorage.setItem('pageSize',pageSize);
        }

    });
}

function checkLayout() {
    //为了保证兼容性，这里取两个值，哪个有值取哪一个
    //scrollTop就是触发滚轮事件时滚轮的高度
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollTop == 0) {
        $('.layui-fixbar-top').hide();
    } else {
        $('.layui-fixbar-top').show();
    }

    var top = $('#container').offset().top - scrollTop;
    if (top < 0) {
        top = 0;
    }
    $('.layui-fixset').css('top', top + 'px');

    var left = $('#container').offset().left;
    if (left<100){
        $('.layui-fixbar').css('right',  '15px');
    }else {
        left = left - 46;
        if (left < 15) {
            left = 15;
        }
        $('.layui-fixset').css('left', left + 'px');

        left = left + $('#container').width() + 65;
        $('.layui-fixbar').css('left', left + 'px');
    }
    // console.info(scrollTop);
}
