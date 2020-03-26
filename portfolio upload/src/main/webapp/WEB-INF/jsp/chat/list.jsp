<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="채팅방 리스트" />
<%@ include file="../part/head.jspf"%>

<style>
.room-list>table {
	width: 100%;
	border-collapse: collapse;
}

.room-list>table td, .room-list>table th {
	border: 1px solid black;
	padding: 10px;
}

.room-list>table>tbody>tr>td:first-child, .room-list>table>tbody>tr>td:nth-child(2)
	{
	text-align: center;
}

.room-list>.page-menu{
	text-align:center;
	padding:30px 0;
}
</style>
<div class="con room-list">
	<div>총 채팅방 수 : ${pagedListRs.totalCount}</div>
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
				<th>생성날짜</th>
				<th>방이름</th>
				<th>생성자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pagedListRs.list}" var="chatRoom">
				<tr>
					<td>${chatRoom.id}</td>
					<td>${chatRoom.regDate}</td>
					<td><a href="./room?id=${chatRoom.id}">${chatRoom.title}</a></td>
					<td>${chatRoom.extra.creatorName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="page-menu">
		<c:forEach var="page" begin="1" end="${pagedListRs.totalPage}" step="1" varStatus="status">
			<a href="./list?page=${page}">${page}</a>	
		</c:forEach>
	</div>
</div>

<%@ include file="../part/foot.jspf"%>