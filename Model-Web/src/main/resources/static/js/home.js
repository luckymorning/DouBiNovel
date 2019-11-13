
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
$('body').addClass(theme);