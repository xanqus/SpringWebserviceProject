<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${board.name}, 게시물 - ${article.title}" />
<c:set var="pageName" value="${board.name}, 게시물 상세내용" />
<%@ include file="../part/head.jspf"%>

<style>
.detail>table {
	width: 100%;
	border-collapse: collapse;
}

.detail>table td, .detail>table th {
	border: 1px solid black;
	padding: 10px;
}
</style>

<form class="detail con" method="POST" action="./doModify">
	<input type="hidden" name="id" value="${article.id}" />
	<table>
		<colgroup>
			<col width="100">
		</colgroup>
		<tbody>
			<tr>
				<th>번호</th>
				<td>${article.id}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${article.extra.writerName}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${article.hit}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" maxlength="100" name="title"
					value="${article.title}"></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea maxlength="500" name="body">${article.title}</textarea>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td>
					<ul class="row">
						<c:if test="${isLogined}">
							<c:if test="${loginedMemberId == article.memberId}">
								<li class="cell"><input type="submit" value="수정" /></li>
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

	<%@ include file="../part/foot.jspf"%>