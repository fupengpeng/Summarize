<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>网易帐号中心</title>

</head>
<body>
<!-- 登录弹层  s -->
<!-- 登录弹框  s -->
<div class="pop-box pop-login" id="login1">
    <form action="https://www.guazi.com/passport/login" method="post" onsubmit="return window.web_sso_login_check()" target="guazi_login">
        <div class="pop-close" id="closeLogin1"></div>
        <p class="pop-tit js-logintitle">山东九点连线信息技术有限公司</p>
        <ul class="phone-login">
            <li>
                <p class="phone-login-tit">手机号码</p>
                <input name="phone" class="phone-login-input js-phoneNum1" placeholder="请输入您的手机号码"/>
            </li>
            <li>
                <p class="phone-login-tit">&emsp;验证码</p>
                <input name="code" class="phone-login-input phone-login-code js-code1" placeholder="请输入验证码"/>
                <button class="get-code">获取验证码</button>
            </li>
        </ul>
        <p class="p-error" id="loginError1"></p>
        <button class="sub-btn  js-checkcode" type="submit" >登录</button>
        <p class="free-phone">免费咨询400-060-7011</p>

        <input type="hidden" name="source" value="2" />
        <input type="hidden" name="staticPage" value="https://www.guazi.com/ssoJump.php" />
        <input type="hidden" name="callBack" value="parent.web_login_callback" />
    </form>
</div>

</body>
</html>
