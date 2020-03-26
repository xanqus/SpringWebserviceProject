<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="채팅방 생성" />
<%@ include file="../part/head.jspf"%>

<script>
	function submitAddForm(form) {
		form.title.value = form.title.value.trim();

		if ( form.title.value.length == 0 ) {
			alert('채팅방 이름을 입력해주세요.');
			form.title.focus();

			return false;
		}

		form.passwd.value = form.passwd.value.trim();

		if ( form.passwd.value.length == 0 ) {
			alert('비밀번호를 입력해주세요.');
			form.passwd.focus();

			return false;
		}

		form.passwdConfirm.value = form.passwdConfirm.value.trim();

		if ( form.passwd.value != form.passwdConfirm.value ) {
			alert('비밀번호확인이 일치하지 않습니다.');
			form.passwdConfirm.focus();

			return false;
		}

		form.submit();
	}
</script>

<div class="con">

	<form name="addForm" onsubmit="submitAddForm(this); return false;"
		action="./doAdd" method="POST">
		<div>
			<span>채팅방 이름</span>
			<div>
				<input autocomplete="off" type="text" name="title"
					required="required" autofocus="autofocus" maxlength="30"
					placeholder="채팅방 이름을 적어주세요" />
			</div>
		</div>
		<div>
			<span> 비밀번호 </span>
			<div>
				<input autocomplete="off" type="password" name="passwd"
					required="required" maxlength="30" placeholder="비밀번호" />
			</div>
		</div>
		<div>
			<span> 비밀번호 확인 </span>
			<div>
				<input autocomplete="off" type="password" name="passwdConfirm"
					required="required" maxlength="30" placeholder="비밀번호 확인" />
			</div>
		</div>
		<div>
			<span>생성</span>
			<div>
				<input type="submit" value="생성" />
			</div>
		</div>
	</form>
</div>

<%@ include file="../part/foot.jspf"%>