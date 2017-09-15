<#include "header.ftl">
<style type="text/css">

    .input-group {
        margin: 10px 0px;
    }

    h3 {
        padding: 5px;
        border-bottom: 1px solid #ddd;
    }

    li {
        list-style-type: square;
        margin: 10px 0;
    }

    em {
        color: #c7254e;
        font-style: inherit;
        background-color: #f9f2f4;
    }

</style>
<div class="row">
    <div class="col-md-12 mt-1">
        <div class="float-xs-right">
            <a href="/register" class="btn btn-primary">去注册>></a>
        </div>
    </div>
    <div class="col-md-6">
        <div class="well col-md-10">
            <h3>用户登录</h3>
            <div class="input-group input-group-md">
                <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user"
                                                                      aria-hidden="true"></i></span>
                <input type="text" class="form-control" placeholder="用户名" aria-describedby="sizing-addon1">
            </div>
            <div class="input-group input-group-md">
                <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" class="form-control" placeholder="密码" aria-describedby="sizing-addon1">
            </div>
            <button type="button" class="btn btn-success" id="btnLogin">
                登录
            </button>

        </div>
    </div>

</div>

<script type="text/javascript">
   ;(function ($) {
       $.Login={
           BindEvent: function () {
               $("#btnLogin").off("click").on("click", function () {
                   $.Login.Save();
               });
           },
           GetPostData: function () {
               return {
                   username: $("#username").val(),
                   pwd: $("#pwd").val()
               };
           },
           Save: function () {
               var postData = $.Login.GetPostData();
               //不可以直接传json，不然后台解析为&拼接的字符串
               $.ajax({
                   type: 'POST',
                   url: "/login",
                   data: JSON.stringify(postData),
                   success: function (backdata) {
                       if (backdata === "ok") {
                           alert("登陆成功了！");
                       }
                   }
               });
           }
       };
       $(function () {
           $.Login.BindEvent();
       });
   })(jQuery);
</script>

<#include "footer.ftl">
