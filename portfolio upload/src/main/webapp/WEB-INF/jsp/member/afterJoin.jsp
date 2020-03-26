<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="회원가입" />
<%@ include file="../part/head.jspf"%>

<script>
var alertMsg = '${alertMsg}';
alertMsg = alertMsg.trim();
var historyBack = '${historyBack}' == 'true';

var redirectUrl = '${redirectUrl}';

if ( alertMsg ) {
	alert(alertMsg);
}

if ( historyBack ) {
	history.back();
}

if ( redirectUrl ) {
	location.replace(redirectUrl);
}
</script>

<%@ include file="../part/foot.jspf"%>