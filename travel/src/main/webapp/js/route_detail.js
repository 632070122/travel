function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = location.search.substr(1).match(reg);
    if (r != null) return (r[2]);
    return null;
} //根据传递过来的参数name获取对应的值

$(document).ready(function () {
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function () {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click', function () {
        var num = $('.little_img').length;
        if ((nextindex + 1) <= num) {
            $('.little_img:eq(' + picindex + ')').hide();
            $('.little_img:eq(' + nextindex + ')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click', function () {
        var num = $('.little_img').length;
        if (picindex > 0) {
            $('.little_img:eq(' + (nextindex - 1) + ')').hide();
            $('.little_img:eq(' + (picindex - 1) + ')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
    //自动播放
    // var timer = setInterval("auto_play()", 5000);
}); //点击图片切换图片

function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
} //自动轮播方法

$(function () {
    var rid = getParameter("rid");
    $.get("route/findOne", {"rid": rid}, function (route) {

        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#price").html(route.price);
        $("#rname").html(route.rname);
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);
        $("#favoriteCount").html(route.count);


        var ddStr = '<a class="up_img up_img_disable"></a>'; //图片小开头

        for (var i = 0; i < route.routeImgList.length; i++) {

            if(i ==1){
                $("#big_img_header").html('<img alt="" class="big_img" id="big_img" src=" '+route.routeImgList[1].bigPic+'    ">')
            }

            var aStr ;
            if (i >= 4){
                aStr = '<a title="" class="little_img" data-bigpic="'+route.routeImgList[i].bigPic+' " style="display: none" ">\n' +
                    '            <img src="'+route.routeImgList[i].smallPic+'" >\n' +
                    '       </a>';

            }else {
                aStr = '<a title="" class="little_img" data-bigpic="'+route.routeImgList[i].bigPic+' " >\n' +
                    '            <img src="'+route.routeImgList[i].smallPic+'">\n' +
                    '       </a>';

            }
            

            ddStr += aStr;
        }

        ddStr += '<a class="down_img down_img_disable" style="margin-bottom: 0;"></a>'; //末尾拼接

        $("#dd").html(ddStr)

        //图片展示和切换调用
        goImage()
    })
})

function goImage() {
    var rid = getParameter("rid");
    $.get("route/findOne", {"rid": rid}, function (route) {

        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#price").html(route.price);
        $("#rname").html(route.rname);
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);

        var ddStr = '<a class="up_img up_img_disable"></a>'; //图片小开头

        for (var i = 0; i < route.routeImgList.length; i++) {
            var aStr = '<a title="" class="little_img" data-bigpic="'+route.routeImgList[i].bigPic+'">\n' +
                '            <img src="'+route.routeImgList[i].smallPic+'">\n' +
                '       </a>';

            ddStr += aStr;
        }

        ddStr += '<a class="down_img down_img_disable" style="margin-bottom: 0;"></a>'; //末尾拼接

        $("#dd").html(ddStr)


        //图片展示和切换调用

    })
}

$(function () {
    var rid = getParameter("rid");
    $.get("route/isFavorite",{rid:rid},function (flag) {  //页面加载完查询用户是否收藏过该线路
        if(flag){
            $("#favorite").addClass("already");
            $("#favorite").attr("disabled","disabled");
            //删除按钮点击事件
            $("#favorite").removeAttr("onclick");
        }else{

        }
    })
})

//点击收藏按钮触发方法
function addFavorite() {




    var rid = getParameter("rid");
    //判断用户是否登陆
    $.get("user/findOne",{},function (user) {
        if(user){
            $.get("route/addfavorite",{rid:rid},function () {
                 //刷新
                //location.reload();

            })
        }else{
            alert("用户未登陆")
            location.href = "http://localhost/travel/login.html"
        }
    })
}