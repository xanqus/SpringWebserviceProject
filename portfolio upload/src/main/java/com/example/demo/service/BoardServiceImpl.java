package com.example.demo.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardDao;
import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleReply;
import com.example.demo.dto.Board;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	public Board findById(int boardId) {
		return boardDao.findById(boardId);
	}

	@Override
	public List<Article> getArticleList(Map<String, Object> param) {
		return boardDao.getArticleList(param);
	}

	@Override
	public Article findArticleById(int id) {
		return boardDao.findArticleById(id);
	}

	@Override
	public Map<String, Object> deleteArticle(int id) {
		boardDao.deleteArticle(id);

		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S-1");
		rs.put("msg", id + "번 글을 삭제하였습니다.");

		return rs;
	}

	@Override
	public Map<String, Object> write(Map<String, Object> param) {
		boardDao.write(param);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		int id = ((BigInteger) param.get("id")).intValue();
		rs.put("msg", id + "번 글이 생성되었습니다.");
		rs.put("id", id);

		return rs;
	}

	@Override
	public Map<String, Object> modify(Map<String, Object> param) {
		boardDao.modify(param);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		rs.put("msg", param.get("id") + "번 글이 수정되었습니다.");

		return rs;
	}

	@Override
	public List<ArticleReply> getRepliesFrom(int articleId, int from) {
		return boardDao.getRepliesFrom(articleId, from);
	}

	@Override
	public Map<String, Object> writeReply(Map<String, Object> param) {
		boardDao.writeReply(param);

		Map<String, Object> rs = new HashMap<>();

		rs.put("resultCode", "S-1");
		int id = ((BigInteger) param.get("id")).intValue();
		rs.put("msg", id + "번 댓글이 생성되었습니다.");
		rs.put("id", id);

		return rs;
	}

	@Override
	public Map<String, Object> deleteReply(int id, int loginedMemberId) {
		Map<String, Object> rs = new HashMap<>();
		
		ArticleReply articleReply = boardDao.getReply(id);
		
		if ( loginedMemberId != articleReply.getMemberId() ) {
			rs.put("resultCode", "F-1");
			rs.put("msg", id + "권한이 없습니다.");
			
			return rs;
		}
		
		boardDao.deleteReply(id);

		rs.put("resultCode", "S-1");
		rs.put("msg", id + "번 댓글이 삭제되었습니다.");
		rs.put("id", id);

		return rs;
	}

	@Override
	public Map<String, Object> modifyReply(Map<String, Object> param, int loginedMemberId) {
		Map<String, Object> rs = new HashMap<>();
		
		int id = (int)param.get("id");
		
		ArticleReply articleReply = boardDao.getReply(id);
		
		if ( loginedMemberId != articleReply.getMemberId() ) {
			rs.put("resultCode", "F-1");
			rs.put("msg", id + "권한이 없습니다.");
			
			return rs;
		}
		
		boardDao.modifyReply(param);

		rs.put("resultCode", "S-1");
		rs.put("msg", id + "번 댓글이 수정되었습니다.");
		rs.put("id", id);

		return rs;
	}

	@Override
	public void increaseArticleHit(int id) {
		boardDao.increaseArticleHit(id);
	}

	@Override
	public Map<String,Object> getPagedList(int page, int itemsCountInAPage, int boardId, Map<String, Object> param) {
		int totalCount = boardDao.getCount(boardId);
		int limitFrom = (page -1) * itemsCountInAPage;
		int totalPage = (int)Math.ceil((double)totalCount / itemsCountInAPage);
		int limitTake = itemsCountInAPage;
		
		Map<String,Object> rs = new HashMap();
		
		rs.put("page", page);
		rs.put("itemsCountInAPage", itemsCountInAPage);
		rs.put("totalCount", totalCount);
		rs.put("totalPage", totalPage);
		rs.put("boardId", boardId);
		rs.put("list", boardDao.getPagedList(Maps.of("limitFrom", limitFrom, "limitTake", limitTake, "boardId", boardId)));
		
		return rs;
	}
}
