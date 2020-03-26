package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleReply;
import com.example.demo.dto.Board;
import com.example.demo.service.BoardService;
import com.example.demo.service.MemberService;

@Controller
public class ArticleController {
	@Autowired
	private BoardService boardService;

	@Autowired
	private MemberService memberService;

	@RequestMapping("/article/write")
	public String showWrite(@RequestParam Map<String, Object> param, Model model) {

		String boardIdStr = (String) param.get("boardId");

		int boardId = 0;

		if (boardIdStr == null) {
			model.addAttribute("alertMsg", "게시판 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		} else {
			boardId = Integer.parseInt(boardIdStr);
		}

		Board board = boardService.findById(boardId);

		model.addAttribute("board", board);

		return "article/write";
	}

	@RequestMapping("/article/doModify")
	public String doM(@RequestParam Map<String, Object> param, Model model, HttpServletRequest req) {

		String idStr = (String) param.get("id");

		if (idStr == null) {
			model.addAttribute("alertMsg", "게시물 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		}

		Map<String, Object> rs = boardService.modify(param);

		model.addAttribute("alertMsg", rs.get("msg"));
		model.addAttribute("redirectUrl", "/article/detail?id=" + idStr);

		return "common/js";
	}

	@RequestMapping("/article/doWrite")
	public String doWrite(@RequestParam Map<String, Object> param, Model model, HttpServletRequest req) {
		String boardIdStr = (String) param.get("boardId");

		if (boardIdStr == null) {
			model.addAttribute("alertMsg", "게시판 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		param.put("memberId", loginedMemberId);

		Map<String, Object> rs = boardService.write(param);

		model.addAttribute("alertMsg", rs.get("msg"));
		model.addAttribute("redirectUrl", "/article/detail?id=" + rs.get("id"));

		return "common/js";
	}

	@RequestMapping("/article/doDelete")
	public String doDelete(int id, Model model) {
		Article article = boardService.findArticleById(id);

		if (article == null) {
			model.addAttribute("alertMsg", "해당 게시물은 존재하지 않거나 이미 삭제되었습니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		}

		int boardId = article.getBoardId();

		Map<String, Object> rs = boardService.deleteArticle(id);

		model.addAttribute("alertMsg", rs.get("msg"));
		model.addAttribute("redirectUrl", "/article/list?boardId=" + boardId);

		return "common/js";
	}

	@RequestMapping("/article/detail")
	public String showDetail(@RequestParam Map<String, Object> param, Model model) {
		String idStr = (String) param.get("id");
		int id = 0;

		if (idStr == null) {
			model.addAttribute("alertMsg", "게시물 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		} else {
			id = Integer.parseInt(idStr);
		}

		Article article = boardService.findArticleById(id);
		Board board = boardService.findById(article.getBoardId());

		boardService.increaseArticleHit(id);

		model.addAttribute("board", board);
		model.addAttribute("article", article);

		return "article/detail";
	}

	@RequestMapping("/article/modify")
	public String showModify(@RequestParam Map<String, Object> param, Model model) {
		String idStr = (String) param.get("id");
		int id = 0;

		if (idStr == null) {
			model.addAttribute("alertMsg", "게시물 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		} else {
			id = Integer.parseInt(idStr);
		}

		Article article = boardService.findArticleById(id);
		Board board = boardService.findById(article.getBoardId());

		model.addAttribute("board", board);
		model.addAttribute("article", article);

		return "article/modify";
	}

	@RequestMapping("/article/list")
	public String showList(@RequestParam Map<String, Object> param, Model model) {

		String boardIdStr = (String) param.get("boardId");
		int boardId = 0;
		int itemsCountInAPage = 15;
		int page = 1;

		if (boardIdStr == null) {
			model.addAttribute("alertMsg", "게시판 번호 정보가 필요합니다.");
			model.addAttribute("historyBack", true);

			return "common/js";
		} else {
			boardId = Integer.parseInt(boardIdStr);
		}

		Board board = boardService.findById(boardId);

		Map<String, Object> getArticleListParam = new HashMap<>(param);
		getArticleListParam.put("boardId", boardId);
		List<Article> articleList = boardService.getArticleList(getArticleListParam);

		model.addAttribute("board", board);
		model.addAttribute("articleList", articleList);

		if (param.containsKey("page")) {
			String pageStr = (String) param.get("page");
			pageStr = pageStr.trim();

			if (pageStr.length() == 0) {
				pageStr = "1";
			}

			try {
				page = Integer.parseInt(pageStr);
			} catch (NumberFormatException e) {
				page = 1;
			}

			page = Integer.parseInt(pageStr);
		}

		model.addAttribute("page", page);
		model.addAttribute("itemsCountInAPage", itemsCountInAPage);

		Map<String, Object> pagedListRs = boardService.getPagedList(page, itemsCountInAPage, boardId, param);

		model.addAttribute("pagedListRs", pagedListRs);

		return "article/list";
	}

	///
	@RequestMapping("/article/getRepliesFrom")
	@ResponseBody
	public Map<String, Object> getRepliesFrom(int articleId, int from) {
		Map<String, Object> rs = new HashMap<>();

		List<ArticleReply> messages = boardService.getRepliesFrom(articleId, from);

		rs.put("resultCode", "S-1");
		rs.put("msg", "성공했습니다.");
		rs.put("messages", messages);

		return rs;
	}

	@RequestMapping("/article/doWriteReply")
	@ResponseBody
	public Map<String, Object> doWriteReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		String articleIdStr = (String) param.get("articleId");

		Map<String, Object> rs = new HashMap<>();

		if (articleIdStr == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "게시물 번호를 입력해주세요.");

			return rs;
		}

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		param.put("memberId", loginedMemberId);

		Map<String, Object> writeReplyRs = boardService.writeReply(param);

		rs.put("resultCode", writeReplyRs.get("resultCode"));
		rs.put("msg", writeReplyRs.get("msg"));

		/*
		 * // 3초간 쉬는거 테스트 try { Thread.sleep(3000); } catch (Exception e) {
		 * 
		 * }
		 */

		return rs;
	}

	@RequestMapping("/article/doDeleteReply")
	@ResponseBody
	public Map<String, Object> doDeleteReply(int id, HttpServletRequest req) {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> deleteReplyRs = boardService.deleteReply(id, loginedMemberId);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", deleteReplyRs.get("resultCode"));
		rs.put("msg", deleteReplyRs.get("msg"));

		return rs;
	}

	@RequestMapping("/article/doModifyReply")
	@ResponseBody
	public Map<String, Object> doDeleteReply(@RequestParam Map<String, Object> param, HttpServletRequest req) {
		param.put("id", Integer.parseInt((String) param.get("id")));

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> modifyReplyRs = boardService.modifyReply(param, loginedMemberId);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", modifyReplyRs.get("resultCode"));
		rs.put("msg", modifyReplyRs.get("msg"));

		return rs;
	}
}
