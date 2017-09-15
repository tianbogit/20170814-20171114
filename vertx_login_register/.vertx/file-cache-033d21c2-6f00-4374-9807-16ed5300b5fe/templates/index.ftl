<#include "header.ftl">

<style type="text/css">
    .input-group{
        margin:10px 0px;//输入框上下外边距为10px,左右为0px
    }
    h3{
        padding:5px;
        border-bottom:1px solid #ddd;//h3字体下边框
    }
    li{
        list-style-type:square;//列表项图标为小正方形
    margin:10px 0;//上下外边距是10px
    }
    em{//强调的样式
    color:#c7254e;
        font-style: inherit;
        background-color: #f9f2f4;
    }
</style>

    <div class="row" style="margin-top:30px;">

        <div class="col-md-12" style="border-right:1px solid #ddd;">
            <div class="well col-md-10">
                <h3>用户登录</h3>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
                    <input type="text" class="form-control" placeholder="用户名" aria-describedby="sizing-addon1">
                </div>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" class="form-control" placeholder="密码" aria-describedby="sizing-addon1">
                </div>
                <div class="well well-sm" style="text-align:center;">
                    <input type="radio" name="kind" value="tea"> 老师
                    <input type="radio" name="kind" value="stu"> 学生
                </div>
                <button type="submit" class="btn btn-success btn-block">
                    登录
                </button>

            </div>
        </div>

    </div>



<#include "footer.ftl">
