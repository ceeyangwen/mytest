<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>登录-当当网</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${pageContext.request.contextPath }/css/login.css" rel="stylesheet" type="text/css"/>
	<link href="${pageContext.request.contextPath }/css/page_bottom.css" rel="stylesheet" type="text/css"/>
	<script src="js/jquery-1.4.3.js"></script>
	<script>
		//邮箱检查
		var flag=false;
		$(function(){
			$("#txtUsername").blur(function(){
				var email=$("#txtUsername").val();
				if(email==""){
					$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>邮箱不能为空");
				}else{
					$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/window_loading.gif'>正在检测");
					$.ajax({
						url:"${pageContext.request.contextPath }/user/validEmail.action",
						type:"post",
						data:{"email":email},
						success:function(data){
							if(data.flag==true){
								$("#email\\.info").html("<img src='${pageContext.request.contextPath}/images/wrong.gif'>该邮箱不存在");
							}else if(data.verify==false){
								$("#email\\.info").html("<a href='user/verify.action?email="+email+"' style='color:red'>请验证后登录&gt;&gt;</a>");
							}else{
								$("#email\\.info").empty();
								flag=true;
							}
						}
					});
				}
			});
		});
	</script>
  </head>
  <body>
  	<br /><%@include file="../common/head1.jsp" %>
  	<div class="enter_part">
  		<%@include file="../common/introduce.jsp" %>
  		<div class="enter_in">
  			<div class="bj_top"></div>
  			<div class="center">
  				<div style="height:30px;padding:5px;color:red" id="divErrorMessage"></div>
  				<div class="main">
  					<h3>登录当当网</h3>
  					<form action="${pageContext.request.contextPath }/user/login.action" method="post" id="ct100">
  						<ul>
  							<li>
  							<span>邮箱：</span>
  							<input type="text" name="email" id="txtUsername" class="textbox"/>
  							<span id="email.info" style="color:red;width:80px;"></span>
  							</li>
  							<li>
  							<span>密码：</span>
  							<input type="password" name="password" id="txtPassword" class="textbox"/>
  							</li>
  							<li>
  							<input type="submit" id="btnSignCheck" class="button_enter" value="登录"/>
  							</li>
  						</ul>
  						<input type="hidden" name="uri" value="${uri }"/>
  					</form>
  				</div>
  				<div class="user_new">
  					<p>您还不是当当网用户?</p>
  					<p class="set_up"><a href="${pageContext.request.contextPath }/user/regist_form.jsp">创建一个新用户&gt;&gt;</a></p>
  				</div>
  			</div>
  		</div>
   	</div>
   	<%@include file="../common/foot1.jsp" %>
  </body>
 </html>