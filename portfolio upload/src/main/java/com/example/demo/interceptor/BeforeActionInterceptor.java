package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.dto.Member;
import com.example.demo.service.MemberService;
import com.example.demo.service.PageViewLogItemService;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
public class BeforeActionInterceptor implements HandlerInterceptor {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PageViewLogItemService pageViewLogItemService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;
		
		HttpSession session = request.getSession();
		
		if ( session.getAttribute("loginedMemberId") != null ) {
			isLogined = true;
			loginedMemberId = Integer.parseInt((String)session.getAttribute("loginedMemberId"));
			loginedMember = memberService.findMemberById(loginedMemberId);
		}
		
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("loginedMember", loginedMember);
		
		pageViewLogItemService.addLog(request);
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
