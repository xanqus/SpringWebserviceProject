package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDao;
import com.example.demo.dto.Member;
import com.example.demo.util.CUtil;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao memberDao;

	@Autowired
	MailService mailService;

	public Map<String, Object> login(Map<String, Object> args) {
		Map<String, Object> rs = new HashMap<>();

		String loginId = (String) args.get("loginId");
		String loginPasswd = (String) args.get("loginPasswd");

		Member member = memberDao.findByLoginId(loginId);

		if (member == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "일치하는 회원이 없습니다.");

			return rs;
		} else if (member.getLoginPasswd().equals(loginPasswd) == false) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "비밀번호가 일치하지 않습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", member.getName() + "님 환영합니다.");
		rs.put("member", member);

		return rs;
	}

	@Override
	public Map<String, Object> loginV2(Map<String, Object> args) {
		Map<String, Object> rs = new HashMap<>();

		String loginId = (String) args.get("loginId");
		String loginPasswd = (String) args.get("loginPasswd");

		Member member = memberDao.findByLoginIdAndLoginPasswd(loginId, loginPasswd);

		if (member == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "일치하는 회원이 없습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", member.getName() + "님 환영합니다.");
		rs.put("member", member);

		return rs;
	}

	public Map<String, Object> join(Map<String, Object> args) {
		Map<String, Object> rs = new HashMap<>();

		String loginId = (String) args.get("loginId");

		Member oldMember = memberDao.findByLoginId(loginId);

		if (oldMember != null) {
			if (oldMember.isOutStatus()) {
				rs.put("resultCode", "F-2");
				rs.put("msg", loginId + "은(는) 탈퇴한 회원의 로그인 아이디 입니다.");
			} else {
				rs.put("resultCode", "F-1");
				rs.put("msg", loginId + "은(는) 사용중인 로그인 아이디 입니다.");
			}

			return rs;
		}

		String regDate = CUtil.getNowDateStr();
		String loginPasswd = (String) args.get("loginPasswd");
		String name = (String) args.get("name");
		String email = (String) args.get("email");

		Member newMember = new Member(0, regDate, loginId, loginPasswd, name, email, false);

		memberDao.add(newMember);

		rs.put("resultCode", "S-1");
		rs.put("msg", name + "님 가입을 축하합니다.");

		return rs;
	}

	@Override
	public Member findMemberById(int id) {
		return memberDao.findById(id);
	}

	@Override
	public Map<String, Object> findLoginId(Map<String, Object> param) {
		String name = (String) param.get("name");
		String email = (String) param.get("email");

		Member member = memberDao.findByNameAndEmail(name, email);

		if (member == null) {
			return Maps.of("resultCode", "F-1", "msg", "일치하는 회원이 없습니다.");
		}

		return Maps.of("resultCode", "S-1", "msg", "당신의 로그인 아이디는 " + member.getLoginId() + " 입니다.");
	}

	@Override
	public Map<String, Object> findLoginPasswd(Map<String, Object> param) {
		String loginId = (String) param.get("loginId");
		String name = (String) param.get("name");
		String email = (String) param.get("email");

		Member member = memberDao.findByLoginIdAndNameAndEmail(loginId, name, email);

		if (member == null) {
			return Maps.of("resultCode", "F-1", "msg", "일치하는 회원이 없습니다.");
		}

		String tempLoginPasswd = Math.random() * 1000 + "";
		member.setLoginPasswd(tempLoginPasswd);

		memberDao.update(member);
		

		String mailTitle = name + "님, 당신의 계정(" + loginId + ")의 임시 패스워드 입니다.";
		String mailBody = "임시 패스워드 : " + tempLoginPasswd;
		mailService.send(email, mailTitle, mailBody);

		return Maps.of("resultCode", "S-1", "msg", "입력하신 메일로 임시 패스워드가 발송되었습니다.");
	}
}
