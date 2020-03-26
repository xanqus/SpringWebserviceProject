<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${board.name}, 게시물 - ${article.title}" />
<c:set var="pageName" value="${board.name}, 게시물 수정" />
<%@ include file="../part/head.jspf"%>

<script>
	//필수변수들
	var articleId = parseInt('${article.id}');
	var loginedMemberId = parseInt('${loginedMemberId}');
	var loginedMemberName = '${loginedMember.name}';
	var isLogined = '${isLogined}' == 'true';

	var Article__lastReceivedReplyId = 0;

	function Article__loadNewReplies() {
		$.get(
			'./getRepliesFrom',
			{
				articleId:articleId,
				from:Article__lastReceivedReplyId + 1
			},
			function(data) {
				if ( data.messages.length > 0 ) {
					Article__lastReceivedReplyId = data.messages[data.messages.length - 1].id;
				}
				
				for ( var i = 0; i < data.messages.length; i++ ) {
					Article__drawReply(data.messages[i]);
				}

				setTimeout(Article__loadNewReplies, 1000);
			},
			'json'
		);
	}

	$(function() {
		Article__loadNewReplies();
	});

	function Article__writeReply(form) {
		if (isLogined == false) {
			alert('로그인 후 이용해주세요');
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			form.body.focus();

			alert('내용을 입력해주세요.');
			return;
		}

		var $form = $(form);

		var body = form.body.value;

		form.body.value = '';

		$form.find('input[type="submit"]').val('작성중..');
		$form.find('input[type="submit"]').prop('disabled', true);
		$form.find('input[type="reset"]').prop('disabled', true);

		$.post('./doWriteReply', {
			articleId : articleId,
			body : body
		}, function(data) {
			if (data.resultCode.substr(0, 2) == 'F-1') {
				alert(data.msg);
			} else {
				$form.find('input[type="submit"]').val('작성');
				$form.find('input[type="submit"]').prop('disabled', false);
				$form.find('input[type="reset"]').prop('disabled', false);
			}
		});
	}

	function Article__drawReply(reply) {
		var $tbody = $('.reply-list table > tbody');

		var templateHtml = $('.template-box-2 tbody').html();

		var 번호 = reply.id;
		var 작성자 = reply.extra.writerName;
		var 내용 = reply.body;
		var 날짜 = reply.regDate.substr(2, 14);
		var 작성자번호 = reply.memberId;

		var trHtml = templateHtml;

		trHtml = replaceAll(trHtml, "{$번호}", 번호);
		trHtml = replaceAll(trHtml, "{$작성자}", 작성자);
		trHtml = replaceAll(trHtml, "{$내용}", 내용);
		trHtml = replaceAll(trHtml, "{$날짜}", 날짜);
		trHtml = replaceAll(trHtml, "{$작성자번호}", 작성자번호);

		$tbody.prepend(trHtml);
	}

	function Article__modifyReply(form) {
		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			form.body.focus();

			alert('내용을 입력해주세요.');
			return;
		}

		var $tr = $(form).closest('tr');

		$tr.attr('data-modify-mode', 'N');

		var newBody = form.body.value;
		var id = parseInt(form.id.value);

		// 실제 서버에서 실행
		$tr.find(' > .reply-body-td > .modify-mode-invisible').empty().append(
				'변경중..');

		$.post('./doModifyReply', {
			id : id,
			body : newBody
		}, function(data) {
			if (data.resultCode.substr(0, 2) == 'S-') {
				$tr.find(' > .reply-body-td > .modify-mode-invisible').empty()
						.append(newBody);
			} else {
				alert(data.msg);
			}
		}, 'json');
	}

	function Article__deleteReply(el) {
		if (isLogined == false) {
			alert('권한이 없습니다.');
			return;
		}

		var $tr = $(el).closest('tr');

		var id = parseInt($tr.attr('data-reply-id'));
		var writerId = parseInt($tr.attr('data-reply-writer-id'));

		if (loginedMemberId != writerId) {
			alert('권한이 없습니다.');
			return;
		}

		if (confirm(id + '번 댓글을 삭제하시겠습니까?') == false) {
			return;
		}

		$.post('./doDeleteReply', {
			id : id
		}, function(data) {
			if (data.resultCode.substr(0, 2) == 'S-') {
				$tr.remove();
			} else {
				alert(data.msg);
			}
		});
	}

	function Article__turnOnModifyMode(el) {
		if (isLogined == false) {
			alert('권한이 없습니다.');
			return;
		}

		var $tr = $(el).closest('tr');

		var writerId = parseInt($tr.attr('data-reply-writer-id'));

		if (loginedMemberId != writerId) {
			alert('권한이 없습니다.');
			return;
		}

		var body = $tr.find(' > .reply-body-td > .modify-mode-invisible')
				.html().trim();

		$tr.find(' > .reply-body-td > .modify-mode-visible > form > textarea')
				.val(body);

		$tr.attr('data-modify-mode', 'Y');
		$tr.siblings('[data-modify-mode="Y"]').attr('data-modify-mode', 'N');
	}

	function Article__turnOffModifyMode(el) {
		var $tr = $(el).closest('tr');

		$tr.attr('data-modify-mode', 'N');
	}

	function replaceAll(str, searchStr, replaceStr) {
		return str.split(searchStr).join(replaceStr);
	}
</script>

<style>
.table {
	width: 100%;
	border-collapse: collapse;
}

.table td, .table th {
	border: 1px solid black;
	padding: 10px;
}

/* 추가 CSS */
.reply-list>table {
	width: 100%;
	border-collapse: collapse;
}

.reply-list>table th, .reply-list>table td {
	border: 1px solid black;
	text-align: center;
	padding: 5px 0;
}

.reply-list .reply-body-td {
	text-align: left;
	padding-left: 5px;
	padding-right: 5px;
}

.reply-list>table>tbody>tr[data-modify-mode="N"] .modify-mode-visible {
	display: none;
}

.reply-list>table>tbody>tr[data-modify-mode="Y"] .modify-mode-invisible
	{
	display: none;
}

.reply-list>table>tbody>tr>.reply-body-td>.modify-mode-visible>form {
	width: 100%;
	display: block;
}

.reply-list>table>tbody>tr>.reply-body-td>.modify-mode-visible>form>textarea
	{
	width: 100%;
	height: 100px;
	box-sizing: border-box;
	display: block;
}

.reply-write>form {
	display: block;
	width: 100%;
}

.reply-write>form>table {
	width: 100%;
	border-collapse: collapse;
}

.reply-write>form>table th, .reply-write>form>table td {
	border: 1px solid black;
	padding: 10px;
}

.reply-write>form>table textarea {
	width: 100%;
	display: block;
	box-sizing: border-box;
	height: 100px;
}

.template-box {
	display: none;
}
</style>

<div class="detail con">
	<table class="table">
		<colgroup>
			<col width="100">
		</colgroup>
		<tbody>
			<tr>
				<th>번호</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${article.title}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${article.extra.writerName}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${article.body}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${article.hit}</td>
			</tr>
			<tr>
				<th>비고</th>
				<td>
					<ul class="row">
						<c:if test="${isLogined}">
							<c:if test="${loginedMemberId == article.memberId}">
								<li class="cell"><a class="btn block"
									href="/article/modify?id=${article.id}">수정</a></li>
								<li class="cell"><a class="btn block"
									onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) { return false; }"
									href="/article/doDelete?id=${article.id}">삭제</a></li>
							</c:if>
						</c:if>
						<li class="cell"><a class="btn block"
							href="/article/list?boardId=${article.boardId}">리스트</a></li>
					</ul>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<h1 class="reply-write-title con">댓글작성</h1>

<div class="reply-write con">
	<form action="" onsubmit="Article__writeReply(this); return false;">
		<table>
			<colgroup>
				<col width="100">
			</colgroup>
			<tbody>
				<tr>
					<th>내용</th>
					<td>
						<div>
							<textarea placeholder="내용을 입력해주세요." name="body" maxlength="300"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<th>작성</th>
					<td><input type="submit" value="작성"> <input
						type="reset" value="취소"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<h1 class="reply-list-title con">댓글목록</h1>

<div class="reply-list con">
	<table>
		<colgroup>
			<col width="50">
			<col width="150">
			<col width="200">
			<col>
			<col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>글쓴이</th>
				<th>내용</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>
</div>

<div class="template-box template-box-2">
	<table border="1">
		<tbody>
			<tr data-modify-mode="N" data-reply-id="{$번호}"
				data-reply-writer-id="{$작성자번호}">
				<td>{$번호}</td>
				<td>{$날짜}</td>
				<td>{$작성자}</td>
				<td class="reply-body-td">
					<div class="modify-mode-invisible">{$내용}</div>
					<div class="modify-mode-visible">
						<form action=""
							onsubmit="Article__modifyReply(this); return false;">
							<input type="hidden" name="id" value="{$번호}">
							<textarea maxlength="300" name="body"></textarea>
							<input type="submit" value="수정완료"> <a href="javascript:;"
								onclick="Article__turnOffModifyMode(this);">수정취소</a>
						</form>
					</div>
				</td>
				<td><a href="javascript:;"
					onclick="Article__deleteReply(this);">삭제</a> <a
					class="modify-mode-invisible" href="javascript:;"
					onclick="Article__turnOnModifyMode(this);">수정</a> <a
					class="modify-mode-visible" href="javascript:;"
					onclick="Article__turnOffModifyMode(this);">수정취소</a></td>
			</tr>
		</tbody>
	</table>
</div>

<%@ include file="../part/foot.jspf"%>