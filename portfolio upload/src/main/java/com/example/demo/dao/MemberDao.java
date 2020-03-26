package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.Member;

@Mapper
public interface MemberDao {
	public Member test1();

	public Member findByLoginId(@Param("loginId") String loginId);

	public Member findByLoginIdAndLoginPasswd(@Param("loginId") String loginId,
			@Param("loginPasswd") String loginPasswd);

	public void add(Member member);

	public Member findById(@Param("id") int id);

	public Member findByNameAndEmail(@Param("name") String name, @Param("email") String email);

	public Member findByLoginIdAndNameAndEmail(@Param("loginId") String loginId, @Param("name") String name, @Param("email") String email);

	public void updateLoginPasswd(@Param("id") int id, @Param("loginPasswd") String loginPasswd);

	public void update(Member member);
}