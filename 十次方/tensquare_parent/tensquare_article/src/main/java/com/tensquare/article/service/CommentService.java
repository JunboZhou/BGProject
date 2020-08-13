package com.tensquare.article.service;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Comment findById(String commentId) {
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> findAll() {
        List<Comment> list = commentRepository.findAll();
        return list;
    }

    public void save(Comment comment) {
        String id = idWorker.nextId() + "";
        comment.set_id(id);
        // 初始化点赞数据 发布时间
        comment.setThumbup(0);
        comment.setPublishdate(new Date());
        // 保存数据
        commentRepository.save(comment);
    }

    public void updateById(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public void thumbup(String commentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        // 封装修改的数值
        Update update = new Update();
        // 使用inc列值增长
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "comment");
    }

    public List<Comment> findByArticleId(String articleId) {
        List<Comment> list = commentRepository.findByArticleid(articleId);
        return list;
    }
}
