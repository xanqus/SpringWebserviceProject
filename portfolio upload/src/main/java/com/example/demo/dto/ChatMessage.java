package com.example.demo.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private int id;
	private String regDate;
	private String body;
	private int roomId;
	private int memberId;
	private Map<String, Object> extra;
}