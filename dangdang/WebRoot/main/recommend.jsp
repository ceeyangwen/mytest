<%@page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<h2>
	编辑推荐
</h2>
<div id=__bianjituijian/danpin>
	<div class=second_c_02>
		<s:iterator value="recommend" var="book">
		<div class=second_c_02_b1>
			<div class=second_c_02_b1_1>
				<a href='#' target='_blank'><img src="${pageContext.request.contextPath }/productImages/${productPic }" width=70 border=0 /></a>
			</div>
			
			<div class=second_c_02_b1_2>
				<h3>
					<a href='#' target='_blank' title='输赢'>${productName }</a>
				</h3>
				<h4>
					作者：${author } 著
					<br />
					出版社：${publishing }&nbsp;&nbsp;&nbsp;&nbsp;出版时间：<s:property value="#book.getPublishDTime()"/>
				</h4>
				<h5>
					简介：${description }
				</h5>
				<h6>
					定价：￥${fixedPric }&nbsp;&nbsp;当当价：￥${dangPrice }
				</h6>
				<div class=line_xx></div>
			</div>
		</div>
		</s:iterator>
	</div>
</div>