package com.example.demo.service;

import java.util.Map;

import com.example.demo.dto.Member;

public interface MemberService {
	public Map<String, Object> join(Map<String, Object> args);

	public Map<String, Object> login(Map<String, Object> args);

	public Map<String, Object> loginV2(Map<String, Object> args);

	public Member findMemberById(int id);

	public Map<String, Object> findLoginId(Map<String, Object> param);

	public Map<String, Object> findLoginPasswd(Map<String, Object> param);
}