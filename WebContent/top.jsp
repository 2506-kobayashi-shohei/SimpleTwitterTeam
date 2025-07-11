<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <title>簡易Twitter</title>
    </head>
    <body>
        <div class="main-contents">
            <div class="header">
		    <c:if test="${ empty loginUser }">
		        <a href="login">ログイン</a>
		        <a href="signup">登録する</a>
		    </c:if>
		    <c:if test="${ not empty loginUser }">
		        <a href="./">ホーム</a>
		        <a href="setting">設定</a>
		        <a href="logout">ログアウト</a>
		    </c:if>
		</div>
		<c:if test="${ not empty loginUser }">
		    <div class="profile">
		        <div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
		        <div class="account">@<c:out value="${loginUser.account}" /></div>
		        <div class="description"><c:out value="${loginUser.description}" /></div>
		    </div>
		</c:if>
        </div>
		<c:if test="${ not empty errorMessages }">
		    <div class="errorMessages">
		        <ul>
		            <c:forEach items="${errorMessages}" var="errorMessage">
		                <li><c:out value="${errorMessage}" />
		            </c:forEach>
		        </ul>
		    </div>
		    <c:remove var="errorMessages" scope="session" />
		</c:if>

		<form action="./" method="get">
			日付：
			<input type="date" id="start" name="start" value="${start}">
			～
			<input type="date" id="end" name="end" value="${end}">
			<input type="submit" value="絞り込み">
		</form>
		<br />
		<div class="form-area">
		    <c:if test="${ isShowMessageForm }">
		        <form action="message" method="post">
		            いま、どうしてる？<br />
		            <textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
		            <br />
		            <input type="submit" value="つぶやく">（140文字まで）
		        </form>
		    </c:if>
		</div>

        <div class="messages">
		 	<c:forEach items="${messages}" var="message">
			 	<div class="message">
			 		<div class="account-name">
			 			<span class="account">
						    <a href="./?user_id=<c:out value="${message.userId}"/> ">
							<c:out value="${message.account}" />
						</a>
						</span>

			 			<span class="name"><c:out value="${message.name}" /></span>
			 		</div>
			 		<div class="text"><pre><c:out value="${message.text}" /></pre></div>
					<div class="date"><fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
	 			</div>

			 	<div>
				<c:if test="${ loginUser.id == message.userId }">
					<form action="edit" method="get" id="editId">
						<input type="hidden" id="id" name="id" value="${message.id}">
						<input type="submit" value="編集">
					</form>
					<form action="delete" method="post" id="deleteId">
						<input type="hidden" id="id" name="id" value="${message.id}">
						<input type="submit" value="削除">
					</form>
			 		</c:if>
  				 		<c:if test="${loginUser.id != null}">
  				 			<form action="comment" method="post" id="commentId">
  				 				<input type="hidden" id="id" name="id" value="${message.id}"><br />
  				 				<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea><br />
  				 				<input type="submit" value="返信">（140文字まで）
  				 				<br />
  				 			</form>
  				 		</c:if>
  				 		<c:forEach items="${comments}" var="comment">
  				 			<c:if test="${comment.messageId == message.id}">
  				 				<div class="account-name">
  				 					<span class="account">
  				 						<c:out value="${comment.account}" />
  				 					</span>
  				 					<span class="name"><c:out value="${comment.name}" /></span>
  				 				</div>
  				 				<div class="text"><pre><c:out value="${comment.text}" /></pre></div>
           					<div class="date"><fmt:formatDate value="${comment.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
  				 			</c:if>
  				 		</c:forEach>
  				 	</div>
  				 </c:forEach>
  			</div>

            <div class="copyright"> Copyright(c)YourName</div>
	</body>
</html>

