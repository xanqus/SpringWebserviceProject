package com.example.demo.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ChatDao;
import com.example.demo.dto.ChatMessage;
import com.example.demo.dto.ChatRoom;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatDao chatDao;

	@Override
	public Map<String, Object> doAdd(Map<String, Object> param) {
		chatDao.add(param);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", "채팅방이 생성되었습니다.");
		int id = ((BigInteger) param.get("id")).intValue();
		rs.put("id", id);

		return rs;
	}

	@Override
	public List<ChatRoom> getList() {
		return chatDao.getList();
	}

	@Override
	public ChatRoom findById(int id) {
		return chatDao.findById(id);
	}

	@Override
	public Map<String, Object> addMessage(Map<String, Object> param) {
		chatDao.addMessage(param);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", "채팅 메세지가 생성되었습니다.");
		int id = ((BigInteger) param.get("id")).intValue();
		rs.put("id", id);

		return rs;
	}

	@Override
	public List<ChatMessage> getMessages(int roomId, int from) {
		return chatDao.getMessages(roomId, from);
	}

	@Override
	public Map<String,Object> getPagedList(int page, int itemsCountInAPage) {
		int totalCount = chatDao.getCount();
		int limitFrom = (page -1) * itemsCountInAPage;
		int totalPage = (int)Math.ceil((double)totalCount / itemsCountInAPage);
		int limitTake = itemsCountInAPage;
		
		Map<String,Object> rs = new HashMap();
		
		rs.put("page", page);
		rs.put("itemsCountInAPage", itemsCountInAPage);
		rs.put("totalCount", totalCount);
		rs.put("totalPage", totalPage);
		rs.put("list", chatDao.getPagedList(Maps.of("limitFrom", limitFrom, "limitTake", limitTake)));
		
		return rs;
	}
}
