
function submit() {
    var form = document.getElementById('form');
    var name = document.getElementById('name').value;
    if (name!=null && name!='' && name!=undefined){
        //再次修改input内容
        form.submit();
    }else {
        layui.layer.alert('请填写书籍名称');
    }
}

//加载主题
var theme = localStorage.getItem('theme');
if (theme == null || theme == undefined || theme == ''){
    theme = 'theme-0';
}
if (!$('body').hasClass(theme)){
    $('body').addClass(theme);
}

$(document).ready(function () {
    var footer = $('.novel-footer');
    if (footer.offset() != undefined){
        var height = footer.height() + 30;
        var top = footer.offset().top;
        var screenHeight = $(window).height();
        var marginTop = screenHeight - height - top;
        if (marginTop>0){
            footer.css('margin-top',marginTop+'px');
        }
    }
});