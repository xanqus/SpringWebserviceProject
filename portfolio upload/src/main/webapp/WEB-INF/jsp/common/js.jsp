<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
var joinStatus = '${joinStatus}';
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