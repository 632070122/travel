
function getParameter(name) {  //获取get请求封装的参数值
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}



$(function () {
    var cid = getParameter("cid");

    var rname = getParameter("rname");

    if(rname != null){
        rname = window.decodeURIComponent(rname);  //url解码,将url解码成汉子信息
    }


    load(cid,null,rname);


});



function load(cid,currentPage,rname) {
    $.get("route/pageQuery", {cid: cid, currentPage:currentPage,rname:rname}, function (pb) {  //页面加载完成后发送ajax请求获取数据
        $("#totalPage").html(pb.totalPage); //展示总页码
        $("#totalCount").html(pb.totalCount);  //展示总记录数

        var fristPage = '<li onclick="javascript:load('+cid+',1,\''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';  //首页的显示

        var beforeNum = pb.currentPage - 1;  //计算上一页
        if(beforeNum < 1){
            beforeNum = 1
        }
        var beforePage = '<li onclick="javascript:load('+cid+','+beforeNum+',\''+rname+'\')" class="threeword"><a href="javascript:void(0)">上一页</a></li>'; //上一页的显示

        var lis = "";   //准备拼接页码
        lis += fristPage;
        lis += beforePage;

        var begin;//定义开始的位置
        var end; //定义结束的位置
        if(pb.totalPage <= 10){//要显示10个页码,如果不够10页,就有几页显示几页
            begin = 1;
            end = pb.totalPage
        }else{                  //如果超过10页
            begin = pb.currentPage - 5;  //第一个页码显示当前页码-5
            end = pb.currentPage + 4;    //最后一个页码显示当前也啊+4

            if(begin < 1){
                begin = 1; //如果前面不够5个,第一个页码就显示1
                end = 10;  //如果前面不够5个,最后一个页码就显示10
            }

            if(end > pb.totalPage){
                end = pb.totalPage;   //如果到了最大化页码,就让当前页码就是最大页码
                begin = end - 9;
            }
        }


        for (var i = begin; i <= end; i++) {
            var li;
            if(pb.currentPage == i){  //判断当前页码是否等于i
                li = '<li class="curPage" onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>'; //创建页码的 li
            }else{
                li = '<li onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>'; //创建页码的 li
            }
            lis += li; //拼接字符串
        }

        var endNum = pb.currentPage + 1;
        if(endNum > pb.totalPage){
            endNum = pb.totalPage;
        }
        var lastPage = ' <li onclick="load('+cid+','+endNum+')" class="threeword"><a href="javascript:void(0)">下一页</a></li>'; //下一页的显示
        var nextPage = '<li onclick="load('+cid+','+pb.totalPage+')" class="threeword"><a href="javascript:void(0)">末页</a></li>'; //尾页的显示

        lis += lastPage;  //拼接页码
        lis += nextPage;  //拼接页码

        $("#pageNum").html(lis);  //将lis的内容设置到ul中,页码的填充

        var route_lis = "";   //列表数据展示,准备拼接具体的内容
        for (var i = 0; i < pb.list.length; i++) {
            var route = pb.list[i]; //获取数据{rid:1,rname=dd}
            var li = '<li>\n' +
                ' <div class="img"><img style="width:299px" src="'+route.rimage+'" alt=""></div>\n' +
                '    <div class="text1">\n' +
                '        <p>'+route.rname+'</p>\n' +
                '        <br/>\n' +
                '        <p>'+route.routeIntroduce+'</p>\n' +
                '    </div>\n' +
                '    <div class="price">\n' +
                '        <p class="price_num">\n' +
                '             <span>&yen;</span>\n' +
                '             <span>'+route.price+'</span>\n' +
                '             <span>起</span>\n' +
                '        </p>\n' +
                '        <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>\n' +
                '    </div>\n' +
                ' </li>';
            route_lis += li;  //列表数据展示,准备拼接具体的内容
        }

        $("#route").html(route_lis); //列表数据展示
        window.scrollTo(0,0);           //定位到页面的顶部
    })
}


