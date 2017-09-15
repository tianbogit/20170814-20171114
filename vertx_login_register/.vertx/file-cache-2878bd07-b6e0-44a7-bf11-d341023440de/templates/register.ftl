<#include "header.ftl">

    <div class="row" >
        <div class="col-md-6">
            <div class="well col-md-10">
                <h3>注册</h3>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
                    <input type="text" class="form-control" placeholder="用户名" aria-describedby="sizing-addon1">
                </div>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" class="form-control" placeholder="密码" aria-describedby="sizing-addon1">
                </div>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="text" name="birthday" class="form-control" placeholder="出生日期"/>
                </div>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="radio" name="sex"  value="0"> 男
                    <input type="radio" name="sex" value="1"> 女
                </div>
                <div class="input-group input-group-md">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="text" name="email" class="form-control" placeholder="邮箱"/>
                </div>
                <button type="submit" class="btn btn-success btn-block">
                    注册
                </button>
            </div>
        </div>
    </div>
<#include "footer.ftl">
