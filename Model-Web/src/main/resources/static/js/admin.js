
/*
        参数解释：
        title   标题
        url     请求的url
        id      需要操作的数据id
        w       弹出层宽度（缺省调默认值）
        h       弹出层高度（缺省调默认值）
    */
window.WeAdminShow = function(title, url, w, h) {
    if(title == null || title == '') {
        title = false;
    };
    if(url == null || url == '') {
        url = "404.html";
    };
    if(w == null || w == '') {
        w = ($(window).width() * 0.9);
    };
    if(h == null || h == '') {
        h = ($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade: 0.4,
        title: title,
        content: url
    });
};


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