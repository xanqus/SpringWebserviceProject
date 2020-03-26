package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.ChatMessage;
import com.example.demo.dto.ChatRoom;

@Mapper
public interface ChatDao {
	public void add(Map<String, Object> param);

	public List<ChatRoom> getList();

	public ChatRoom findById(@Param("id") int id);

	public void addMessage(Map<String, Object> param);

	public List<ChatMessage> getMessages(@Param("roomId") int roomId, @Param("from") int from);

	public int getCount();

	public List<ChatRoom> getPagedList(Map<String, Object> param);
}
