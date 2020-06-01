function getParameter(name) {  //获取get请求封装的参数值
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}

$(function () {

    $.get("user/findOne",{},function (data) {
        var msg = "欢迎回来 , "+data.name;
        $("#span_username").html(msg)
    })

    $.get("category/findAll",{},function (data) {
        var lis = '<li class="nav-active"><a href="index.html">首页</a></li>';

        for (var i = 0; i < data.length; i++) {
            var li = '<li><a href="route_list.html?cid='+data[i].cid+'">'+data[i].cname+'</a></li>';
            lis += li;
        }

        lis += '<li><a href="favoriterank.html">收藏排行榜</a></li>';
        $("#category").html(lis);
    })

    $("#search-button").click(function () {  //搜索功能
        var rname = $("#search_input").val();
        var cid = getParameter("cid");
        location.href = "http://localhost/travel/route_list.html?cid="+cid+"&rname="+rname;

    })


})