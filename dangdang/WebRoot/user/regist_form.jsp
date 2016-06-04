<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>用户注册-当当网</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<link href="${pageContext.request.contextPath }/css/login.css" rel="stylesheet" type="text/css"/>
		<link href="${pageContext.request.contextPath }/css/page_bottom.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.3.js"></script>
		<script type="text/javascript">
			//验证码是否通过检查的标识位
			var codeFlag=false;
			$(function(){
				//点击看不清按钮,切换图片
				$("#changeImage").click(function(){
					$("#imgVcode").attr("src","${pageContext.request.contextPath }/user/image.action?"+new Date());
					return false;
				});
				//验证码检查
				$("#txtVerifyCode").blur(function(){
					codeFlag=false;
					var txtCode=$("#txtVerifyCode").val();
					if(txtCode==""){
						$("#number\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>验证码不能为空");
					}else{
						$("#number\\.info").html("<img src='${pageContext.request.contextPath}/images/window_loading.gif'>正在检测");
						$.ajax({
							url:"${pageContext.request.contextPath }/user/validCode.action",
							type:"post",
							data:{"code":txtCode},
							success:function(data){
								if(data==true){
									codeFlag=true;
									$("#number\\.info").html("<img src='${pageContext.request.contextPath}/images/right.gif'>验证码正确!");
								}else{
									$("#number\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>验证码错误");
								}
							}
						});
					}
				});
			});
			//邮箱检查
			$(function(){
				$("#txtEmail").blur(function(){
					var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
					var email=$("#txtEmail").val();
					if(email==""){
						$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>邮箱不能为空");
					}else if(!reg.test(email)){
						$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>邮箱格式不正确");
					}else{
						$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/window_loading.gif'>正在检测");
						$.ajax({
							url:"${pageContext.request.contextPath }/user/validEmail.action",
							type:"post",
							data:{"email":email},
							success:function(data){
								if(data.flag==true){
									codeFlag=true;
									$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/right.gif'>该邮箱可用!");
								}else{
									$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>该邮箱不可用");
								}
							}
						});
					}
				});
				$("#txtNickName").blur(function(){
					var reg=/^\w{4,20}$/;
					var name=$("#txtNickName").val();
					if(name==""){
						$("#name\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>昵称不能为空");
						codeFlag=false;
					}else if(!reg.test(name)){
						$("#name\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>昵称格式不正确");
						codeFlag=false;
					}else{
						$("#name\\.info").html("<img src='${pageContext.request.contextPath}/images/right.gif'>该昵称可用");
						codeFlag=true;
					}
				});
				$("#txtPassword").blur(function(){
					var reg=/^\w{6,20}$/;
					var pwd=$("#txtPassword").val();
					if(pwd==""){
						$("#password\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>密码不能为空");
						codeFlag=false;
					}else if(!reg.test(pwd)){
						$("#password\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>密码格式不正确");
						codeFlag=false;
					}else{
						$("#password\\.info").html("<img src='${pageContext.request.contextPath}/images/right.gif'>该密码可用");
						codeFlag=true;
					}
				});
				$("#txtRepeatPass").blur(function(){
					var pwd=$("#txtPassword").val();
					var pwd1=$("#txtRepeatPass").val();
					if(pwd1!=pwd){
						$("#password1\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>两次输入的密码不一致");
						codeFlag=false;
					}else{
						codeFlag=true;
					}
				});
			});
			//点注册时候检查各个表单项是否通过检查
			$(function(){
				$("#f").submit(function(){
					$("txtVerifyCode").blur();
					$("#txtEmail").blur();
					$("#txtNickName").blur();
					$("#txtPassword").blur();
					$("#txtRepeatPass").blur();
					if(codeFlag==false){
						alert("表单输入有误,请检查");
						return false;
					}else{
						return true;
					}
				});
			});
		</script>
	</head>
	<body><%@include file="../common/head1.jsp" %>
		<div class="login_step">
			注册步骤:
			<span class="red_bold">1.填写信息</span> > 2.验证邮箱 > 3.注册成功
		</div>
		<div class="fill_message">
			<form action="${pageContext.request.contextPath }/user/register.action" name="ct100" method="post" id="f">
				<h2>以下均为必填项</h2>
				<table class="tab_login">
					<tr>
						<td valign="top" class="w1">请填写您的Email地址:</td>
						<td><input name="user.email" type="text" id="txtEmail" class="text_input" />
							<div class="text_left" id="emailValidMsg">
							<p>请填写有效的Email地址,在下一步中您将用此邮箱接收验证邮件。</p>
							<span id="email.info" style="color:red"></span>
							</div>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="w1">设置您在当当网的昵称:</td>
						<td><input name="user.nickname" type="text" id="txtNickName" class="text_input" />
							<div class="text_left" id="nickNameValidMsg">
							<p>您的昵称可以由小写字母、中文、数字组成，</p>
							<p>长度为4-20个字符，一个汉字为两个字符</p>
							<span id="name.info" style="color:red"></span>
							</div>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="w1">设置密码:</td>
						<td><input name="user.password" type="password" id="txtPassword" class="text_input" />
							<div class="text_left" id="passwordValidMsg">
							<p>您的密码可以由大小写英文字母、数字组成，长度6-20位</p>
							<span id="password.info" style="color:red"></span>
							</div>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="w1">再次输入您设置的密码:</td>
						<td><input name="password1" type="password" id="txtRepeatPass" class="text_input" />
							<div class="text_left" id="repeatPassValidMsg">
							<span id="password1.info" style="color:red"></span>
							</div>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="w1">验证码:</td>
						<td><img class="yzm_img" id="imgVcode" src="${pageContext.request.contextPath }/user/image.action"/>
							<input name="number" type="text" id="txtVerifyCode" class="yzm_input"/>
							<div class="text_left t1" >
							<p class="t1">
							<span id="vcodeValidMsg">请输入图片中的四个字母。</span>
							<a href="#" id="changeImage">看不清楚?换个图片</a>
							</p>
							<span id="number.info" style="color:red"></span>
							</div>
						</td>
					</tr>
				</table>
				<div class="login_in">
					<input id="btnClientRegister" class="button_1" name="submit" type="submit" value="注册">
				</div>
			</form>
		</div>
		<%@include  file="../common/foot1.jsp"%>
	</body>
</html>