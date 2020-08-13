package com.tensquare.article.repository;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    //SpringDataMongoDB，支持通过查询方法名进行查询定义的方式
    //根据文章id查询文章评论数据
    List<Comment> findByArticleid(String articleId);
}
