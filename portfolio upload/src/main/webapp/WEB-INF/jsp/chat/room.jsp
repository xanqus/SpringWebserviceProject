<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageName" value="채팅방" />
<%@ include file="../part/head.jspf"%>

<script>
	var Chat__roomId = parseInt('${param.id}');
	var Chat__lastLoadedMessageId = 0;
</script>

<script>
	function Chat__loadNewMessages() {
		$.get('/chat/getNewMessagesFrom', {
			roomId : Chat__roomId,
			from : Chat__lastLoadedMessageId + 1
		}, function(data) {
			for ( var i = 0; i < data.messages.length; i++ ) {
				Chat__drawMessage(data.messages[i]);
				Chat__lastLoadedMessageId = data.messages[i].id;
			}

			setTimeout(Chat__loadNewMessages, 1000);
		}, 'json')
	}

	function Chat__drawMessage(message) {
		if ( typeof message.writer == 'undefined' ) {
			message.writer = message.extra.memberName;
		}
		
		var html = message.writer + ' : ' + message.body;
		html = "<div>" + html + "</div>";
		
		$('.chat-messages-box').prepend(html);
	}

	function Chat__sendMessage(body) {
		$.post('/chat/doAddMessage', {
			roomId : Chat__roomId,
			body : body
		}, function(data) {
		}, 'json');
	}

	function submitChatMessageForm(form) {
		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			form.body.focus();

			return false;
		}

		var body = form.body.value;

		form.body.value = '';
		form.body.focus();

		Chat__sendMessage(body);
		
	}

	$(function() {
		Chat__loadNewMessages();
	});
</script>

<div class="chat-add-message-box con">
	<form onsubmit="submitChatMessageForm(this); return false;">
		<input autocomplete="off" type="text" name="body"
			placeholder="내용을 입력해주세요." /> <input type="submit" value="작성" />
	</form>
</div>

<div class="chat-messages-box con">
</div>

<%@ include file="../part/foot.jspf"%>