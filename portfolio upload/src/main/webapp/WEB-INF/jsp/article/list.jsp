<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="${board.name} 게시물 리스트" />
<%@ include file="../part/head.jspf"%>

<style>
.article-list>table {
	width: 100%;
	border-collapse: collapse;
}

.article-list>table td, .article-list>table th {
	border: 1px solid black;
	padding: 10px;
}

.article-list>table>tbody>tr>td:first-child, .article-list>table>tbody>tr>td:nth-child(2)
	{
	text-align: center;
}
.article-list>.page-menu{
	text-align:center;
	padding:30px 0;
}
</style>
<div class="con article-list">
	<div>총 글 수 : ${pagedListRs.totalCount}</div>
	<table>
		<colgroup>
			<col width="100">
			<col width="200">
			<col>
			<col width="200">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>작성날짜</th>
				<th>제목</th>
				<th>작성자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pagedListRs.list}" var="article">
				<tr>
					<td>${article.id}</td>
					<td>${article.regDate}</td>
					<td><a href="./detail?id=${article.id}">${article.title}</a></td>
					<td>${article.extra.writerName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="page-menu">
		<c:forEach var="page" begin="1" end="${pagedListRs.totalPage}" step="1" varStatus="status">
			<a href="./list?boardId=${pagedListRs.boardId}&page=${page}">${page}</a>	
		</c:forEach>
	</div>

</div>

<%@ include file="../part/foot.jspf"%>