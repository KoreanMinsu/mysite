<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import ="com.javaex.vo.GuestbookVo" %>
<%
	int no = Integer.parseInt(request.getParameter("no"));
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Guestbook Delete Form</title>
<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		<!-- header -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="guestbook">
					<form action="/mysite/guest" method="post">
						<table id="guestDelete">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 25%;">
								<col style="width: 25%;">
							</colgroup>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="pw"></td>
								<td class="text-left"><button type="submit">삭제</button></td>
								<td><a href="/mysite/guest?action=addList">[방명록으로 돌아가기]</a></td>
							</tr>
						</table>
						<input type='hidden' name="no" value="<%=no%>">
						<input type='hidden' name="action" value="delete">
					</form>
					
				</div>
				<!-- //guestbook -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->
		
		<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
		<!-- footer -->

	</div>
	<!-- //wrap -->

</body>

</html>