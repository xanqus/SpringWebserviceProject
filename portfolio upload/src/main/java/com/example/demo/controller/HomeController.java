package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Member;
import com.example.demo.service.MemberService;
import com.example.demo.service.PageViewLogItemService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/")
	public String showMainRedirect() {
		return "redirect:/home/main";
	}
	
	@RequestMapping("/home/main")
	public String showMain(Model model) {
		return "home/main";
	}
	
	@RequestMapping("/home/testRequestInfo")
	@ResponseBody
	public String testRequestInfo(HttpServletRequest req, HttpSession session) {
		String info = "";
		info += "path : " + req.getServletPath();
		info += "<br>";
		info += "queryStr : " + req.getQueryString();
		info += "<br>";
		info += "domain : " + req.getServerName();
		info += "<br>";
		info += "port : " + req.getServerPort();
		info += "<br>";
		info += "url : " + req.getRequestURL();
		info += "<br>";
		info += "loginedMemberId : " + session.getAttribute("loginedMemberId");
		info += "<br>";
		
		return info;
	}
}

