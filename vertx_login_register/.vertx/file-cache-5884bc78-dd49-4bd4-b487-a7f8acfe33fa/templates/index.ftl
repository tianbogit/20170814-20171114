<#include "header.ftl">

<style type="text/css">
    .input-group{
        margin:10px 0px;
    }
    h3{
        padding:5px;
        border-bottom:1px solid #ddd;
    }
    li{
        list-style-type:square;
    margin:10px 0;
    }
    em{
    color:#c7254e;
        font-style: inherit;
        background-color: #f9f2f4;
    }
</style>

    <div class="row" >
        <div class="col-md-12 mt-1">
            <div class="float-xs-right">
                <form class="form-inline" action="/register" method="post">
                    <button type="submit" class="btn btn-primary">去注册>></button>
                </form>
            </div>
        </div>
        <div class="col-md-6">
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
                <button type="submit" class="btn btn-success btn-block">
                    登录
                </button>
                <button type="submit" class="btn btn-success btn-block">
                    注册
                </button>

            </div>
        </div>

    </div>



<#include "footer.ftl">
