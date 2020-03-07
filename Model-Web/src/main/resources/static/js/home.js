
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

    $('#donate').click(function () {
        var html = '' +
            '<div style="position: relative;overflow: hidden;width: 650px;">' +
            '<img style="width: 300px;margin: 10px;" src="/imgs/donate_wechat.png" onerror="$(this).attr(\'src\', \'../../static/imgs/donate_wechat.png\')"/>'+
            '<img style="width: 300px;margin: 10px" src="/imgs/donate_alipay.png" onerror="$(this).attr(\'src\', \'../../static/imgs/donate_alipay.png\')"/>'+
            '</div>';
        layui.layer.open({
            type: 1,
            title:'捐赠二维码',
            content:html,
            area:['650px','500px'],
        })
    });
});


var showLoginDialog = function () {
    var w = $(window).width();
    if (w<500){
        w = w - 20;
    }else {
        w = 500;
    }
    layui.layer.open({
        type: 2,
        area: [w+'px', '550px'],
        fix: false, //不固定
        maxmin: false,
        shadeClose: true,
        shade: 0.4,
        title: '登录',
        content: '/login'
    });
}


/**
 * 删除数据
 * @param obj
 * @param id
 */
window.data_del = function (obj, id) {
    layer.confirm('确认要删除吗？', function (index) {
        //发异步删除数据
        $.ajax({
            url: 'delete?id=' + id,
            method: 'post',
            dataType: 'json',
            success: function (rst) {
                if (rst.success) {
                    reloadTable();
                    layer.msg('已删除!', {icon: 6, time: 1000});
                } else {
                    if (rst.message != null && rst.message != "" && rst.message != undefined) {
                        layer.msg('删除失败，' + rst.message + '!', {icon: 5, time: 1000});
                    } else {
                        layer.msg('删除失败，请稍后再试!', {icon: 5, time: 1000});
                    }
                }
            },
            error: function (err) {
                layer.alert('网络异常，请稍后再试');
            }
        })
    });
};

/**
 * 批量删除
 * @param argument
 */
window.data_delAll = function (data,idNick,nameNick,table) {
    if (data.length == 0) {
        layer.msg('请选择要删除的内容', {icon: 5, time: 1000});
        return;
    }
    if (!idNick){
        idNick = 'id';
    }
    if (!nameNick){
        nameNick = 'name';
    }
    var names = [];
    var idS = [];
    for (var index = 0; index < data.length; index++) {
        idS.push(data[index][idNick]);
        names.push(data[index][nameNick]);
    }
    layer.confirm('确认要删除【'+ names+'】吗？' , function (index) {
        //捉到所有被选中的，发异步进行删除
        $.ajax({
            url: 'deleteList?ids=' + idS,
            method: 'post',
            dataType: 'json',
            success: function (rst) {
                if (rst.success) {
                    layer.msg('删除成功', {
                        icon: 1
                    });
                    reloadTable();
                } else {
                    if (rst.message != null && rst.message != "" && rst.message != undefined) {
                        layer.msg('删除失败，' + rst.message + '!', {icon: 5, time: 1000});
                    } else {
                        layer.msg('删除失败，请稍后再试!', {icon: 5, time: 1000});
                    }
                }
            },
            error: function (err) {
                layer.alert('网络异常，请稍后再试');
            }
        })
    });
};