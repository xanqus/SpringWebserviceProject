package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.PageViewLogItem;

@Mapper
public interface PageViewLogItemDao {

	public void addLog(PageViewLogItem pageViewLogItem);

}
