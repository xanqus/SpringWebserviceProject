package com.example.demo.service;

import java.util.Map;

public interface MailService {
	public Map<String, Object> send(String email, String title, String body);
}