package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ChatMessage;
import com.example.demo.dto.ChatRoom;
import com.example.demo.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {
	@Autowired
	private ChatService chatService;

	@RequestMapping("/chat/list")
	public String showList(Model model, @RequestParam Map<String, Object> param) {
		int itemsCountInAPage = 15;
		int page = 1;

		if (param.containsKey("page")) {
			String pageStr = (String) param.get("page");
			pageStr = pageStr.trim();

			if (pageStr.length() == 0) {
				pageStr = "1";
			}

			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				page = 1;
			}

			page = Integer.parseInt(pageStr);
		}

		model.addAttribute("page", page);
		model.addAttribute("itemsCountInAPage", itemsCountInAPage);

		Map<String, Object> pagedListRs = chatService.getPagedList(page, itemsCountInAPage);

		model.addAttribute("pagedListRs", pagedListRs);

		return "chat/list";
	}

	@RequestMapping("/chat/room")
	public String showList(int id, Model model, HttpSession session) {
		ChatRoom chatRoom = chatService.findById(id);

		model.addAttribute("id", id);
		model.addAttribute("chatRoom", chatRoom);

		return "chat/room";
	}

	@RequestMapping("/chat/add")
	public String showAdd() {
		return "chat/add";
	}

	@RequestMapping("/chat/doAdd")
	@ResponseBody
	public String doAdd(@RequestParam Map<String, Object> param, HttpSession session) {

		String loginedMemberIdStr = (String) session.getAttribute("loginedMemberId");

		if (loginedMemberIdStr == null) {
			return "<script> alert('로그인 후 이용해주세요.'); history.back(); </script>";
		}

		int loginedMemberId = Integer.parseInt(loginedMemberIdStr);

		param.put("memberId", loginedMemberId);

		Map<String, Object> rs = chatService.doAdd(param);
		int id = (int) rs.get("id");

		String msg = id + "번 채팅방이 생성되었습니다.";

		String rsStr = "";

		rsStr += "<script>";
		rsStr += "alert('" + msg + "');";
		rsStr += "location.replace('./list');";
		rsStr += "</script>";

		return rsStr;
	}

	@RequestMapping("/chat/doAddMessage")
	@ResponseBody
	public Map doAddMessage(@RequestParam Map<String, Object> param, HttpSession session) {
		String loginedMemberIdStr = (String) session.getAttribute("loginedMemberId");

		if (loginedMemberIdStr == null) {
			return Maps.of("resultCode", "F-1", "msg", "로그인 후 이용해주세요.");
		}

		int loginedMemberId = Integer.parseInt(loginedMemberIdStr);

		param.put("memberId", loginedMemberId);
		Map<String, Object> rs = chatService.addMessage(param);

		return rs;
	}

	@RequestMapping("/chat/getNewMessagesFrom")
	@ResponseBody
	public Map getNewMessagesFrom(int roomId, int from) {

		List<ChatMessage> messages = chatService.getMessages(roomId, from);

		int messagesLen = messages.size();

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", "메세지를 " + messagesLen + "개 가져왔습니다.");
		rs.put("messages", messages);

		return rs;
	}
}
