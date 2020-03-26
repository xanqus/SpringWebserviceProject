<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="회원가입" />
<%@ include file="../part/head.jspf"%>

<script>
	function makeTestData() {
		var form = document.joinForm;
		form.loginId.value = 'user1';
		form.loginPasswd.value = 'user1';
		form.loginPasswdConfirm.value = 'user1';
		form.name.value = '홍길동';
		form.email.value = 'jangka512@gmail.com';
	}
	function submitJoinForm(form) {
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
		form.loginPasswdConfirm.value = form.loginPasswdConfirm.value.trim();
		if (form.loginPasswd.value != form.loginPasswdConfirm.value) {
			alert('비밀번호 확인이 일치하지 않습니다.');
			form.loginPasswdConfirm.focus();
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

	<form name="joinForm" onsubmit="submitJoinForm(this); return false;"
		action="./doJoin" method="POST">
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
			<span> 비밀번호 확인 </span>
			<div>
				<input autocomplete="off" type="password" name="loginPasswdConfirm"
					required="required" maxlength="30" placeholder="비밀번호 확인" />
			</div>
		</div>
		<div>
			<span>이름</span>
			<div>
				<input autocomplete="off" type="text" name="name"
					required="required" maxlength="30" placeholder="이름" />
			</div>
		</div>
		<div>
			<span>이메일</span>
			<div>
				<input autocomplete="off" type="email" name="email"
					required="required" maxlength="50" placeholder="이메일" />
			</div>
		</div>
		<div>
			<span>회원가입</span>
			<div>
				<input type="submit" value="회원가입" />
			</div>
		</div>
	</form>
</div>


<%@ include file="../part/foot.jspf"%>