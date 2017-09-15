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
    <div class="col-md-6">
        <div class="well col-md-10">
            <h3>注册</h3>
            <div class="input-group input-group-md">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
                <input type="text" id="username" class="form-control" placeholder="用户名">
            </div>
            <div class="input-group input-group-md">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" id="pwd" class="form-control" placeholder="密码">
            </div>
            <div class="input-group input-group-md">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="text" id="birthday" class="form-control" placeholder="出生日期"/>
            </div>
            <div class="input-group input-group-md">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="radio" name="sex" value="0"> 男
                <input type="radio" name="sex" value="1"> 女
            </div>
            <div class="input-group input-group-md">
                <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="text" id="email" class="form-control" placeholder="邮箱"/>
            </div>
            <button type="button" class="btn btn-success btn-block" id="btnReg">
                注册
            </button>
        </div>
    </div>
</div>
<script type="text/javascript">
    ;(function ($) {
        $.Register={
            BindEvent:function () {
                $("#btnReg").off("click").on("click",function () {
                   $.Register.Save();
                });
            },
            GetPostData:function () {
                return {
                    username:$("#username").val(),
                    pwd:$("#pwd").val(),
                    birthday:$("#birthday").val(),
                    sex:$("input[type='radio']:checked").val(),
                    email:$("#email").val(),
                };
            },
            Save:function () {
                var postData=$.Register.GetPostData();
                console.log(postData)
                $.ajax({
                    type: 'POST',
                    url: "/register",
                    data: {"name","66666"},
                    success: function (backdata) {
                        console.log(backdata)
                    }
                    //dataType: "json"
                });
            }
        };
        $(function () {
            $.Register.BindEvent();
        });
    })(jQuery);

</script>
<#include "footer.ftl">
