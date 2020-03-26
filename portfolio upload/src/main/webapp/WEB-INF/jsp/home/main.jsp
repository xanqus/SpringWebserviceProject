<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="메인" />
<%@ include file="../part/head.jspf"%>

<div class="con">
	<h1>Study Hard</h1>
	
	${loginedMember.name}님 환영합니다.
</div>

<%@ include file="../part/foot.jspf"%>