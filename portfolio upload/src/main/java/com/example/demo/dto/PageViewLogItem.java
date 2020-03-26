package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageViewLogItem {
	private int id;
	private String regDate;
	private String path;
	private String queryStr;
	private String domain;
	private int port;
	private String url;
	private int loginedMemberId;
}
