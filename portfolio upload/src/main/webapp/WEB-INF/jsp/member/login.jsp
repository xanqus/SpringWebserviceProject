<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="로그인" />
<%@ include file="../part/head.jspf"%>


<script>
	function makeTestData() {
		var form = document.loginForm;
		form.loginId.value = 'user1';
		form.loginPasswd.value = 'user1';
	}
	function submitLoginForm(form) {
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return false;
		}
		form.loginPasswd.value = form.loginPasswd.value.trim();
		if (form.loginPasswd.value.length == 0) {
			alert('로그인 비밀번호를 입력해주세요.');
			form.loginPasswd.focus();
			return false;
		}

		form.submit();
	}
</script>


<div class="join-box con">

	<form name="loginForm" onsubmit="submitLoginForm(this); return false;"
		action="./doLogin" method="POST">
		<div>
			<span>아이디</span>
			<div>
				<input autocomplete="off" type="text" name="loginId"
					required="required" autofocus="autofocus" maxlength="30"
					placeholder="아이디" />
			</div>
		</div>
		<div>
			<span> 비밀번호 </span>
			<div>
				<input autocomplete="off" type="password" name="loginPasswd"
					required="required" maxlength="30" placeholder="비밀번호" />
			</div>
		</div>
		<div>
			<span>로그인</span>
			<div>
				<input type="submit" value="로그인" />
			</div>
		</div>
	</form>
</div>

<%@ include file="../part/foot.jspf"%>