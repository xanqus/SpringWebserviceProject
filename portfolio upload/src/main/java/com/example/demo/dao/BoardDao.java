package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.Article;
import com.example.demo.dto.ArticleReply;
import com.example.demo.dto.Board;
import com.example.demo.dto.ChatRoom;

@Mapper
public interface BoardDao {
	public Board findById(@Param("id") int id);

	public List<Article> getArticleList(Map<String, Object> param);

	public Article findArticleById(@Param("articleId") int articleId);

	public void deleteArticle(@Param("articleId") int articleId);

	public void write(Map<String, Object> param);

	public void modify(Map<String, Object> param);

	public List<ArticleReply> getRepliesFrom(@Param("articleId") int articleId, @Param("from") int from);

	public void writeReply(Map<String, Object> param);

	public ArticleReply getReply(@Param("id") int id);

	public void deleteReply(@Param("id") int id);

	public void modifyReply(Map<String, Object> param);

	public void increaseArticleHit(@Param("id") int id);

	public int getCount(@Param("boardId") int boardId);

	public List<Article> getPagedList(Map<String, Object> param);
}
