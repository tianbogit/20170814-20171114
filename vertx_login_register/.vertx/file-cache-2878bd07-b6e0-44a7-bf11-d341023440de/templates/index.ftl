<#include "header.ftl">

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
    $(function () {
        alert(22);
    });
</script>

<#include "footer.ftl">
