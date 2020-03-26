<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="비밀번호 찾기" />
<%@ include file="../part/head.jspf"%>

<script>
	function makeTestData() {
		var form = document.findLoginPasswdForm;
		form.loginId.value = 'user1';
		form.name.value = '홍길동';
		form.email.value = 'jangka512@gmail.com';
	}
	function submitFindLoginPasswdForm(form) {
		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return false;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return false;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return false;
		}

		form.submit();
	}
</script>



<div class="join-box con">

	<form name="findLoginPasswdForm"
		onsubmit="submitFindLoginPasswdForm(this); return false;"
		action="./doFindLoginPasswd" method="POST">
		<div>
			<span>로그인 아이디</span>
			<div>
				<input autocomplete="off" type="text" name="loginId"
					required="required" autofocus="autofocus" maxlength="30"
					placeholder="로그인 아이디" />
			</div>
		</div>

		<div>
			<span>이름</span>
			<div>
				<input autocomplete="off" type="text" name="name"
					required="required" autofocus="autofocus" maxlength="30"
					placeholder="이름" />
			</div>
		</div>
		<div>
			<span> 이메일 </span>
			<div>
				<input autocomplete="off" type="email" name="email"
					required="required" maxlength="30" placeholder="이메일" />
			</div>
		</div>
		<div>
			<span>로그인 비밀번호 찾기</span>
			<div>
				<input type="submit" value="로그인 비밀번호 찾기" />
			</div>
		</div>
	</form>
</div>



<%@ include file="../part/foot.jspf"%>