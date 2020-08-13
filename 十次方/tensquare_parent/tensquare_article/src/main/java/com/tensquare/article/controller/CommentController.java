package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    //PUT /comment/thumbup/{commentId} 根据评论id点赞评论
    @RequestMapping(value = "thumbup/{commentId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String commentId) {

        String userId = "1012";
        Object flag = redisTemplate.opsForValue().get("thumbup_" + userId + "_" + commentId);
        if (flag == null) {
            // 如果为空 表示用户没有点赞过 可以点赞
            commentService.thumbup(commentId);
            // 点赞成功 保存点赞信息
            redisTemplate.opsForValue().set("thumbup_" + userId + "_" + commentId, 1);
        }
        return new Result(true, StatusCode.OK, "点赞成功", "");
    }

    //GET /comment/article/{articleId} 根据文章id查询文章评论
    @RequestMapping(value = "article/{articleId}", method = RequestMethod.GET)
    public Result findByArticleId(@PathVariable String articleId) {
        List<Comment> list = commentService.findByArticleId(articleId);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //GET /comment/{commentId} 根据评论id查询评论数据
    @RequestMapping(value = "{commentId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String commentId) {
        Comment comment = commentService.findById(commentId);
        return new Result(true, StatusCode.OK, "查询成功", comment);
    }

    //GET /comment 查询所有评论
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Comment> list = commentService.findAll();

        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    //POST /comment 新增评论
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result(true, StatusCode.OK, "新增成功", "");
    }

    //PUT /comment/{commentId} 修改评论
    @RequestMapping(value = "{commentId}", method = RequestMethod.PUT)
    public Result updateById(@PathVariable String commentId, @RequestBody Comment comment) {
        // 设置评论主键
        comment.set_id(commentId);
        // 执行修改
        commentService.updateById(comment);
        return new Result(true, StatusCode.OK, "修改成功", "");
    }

    //DELETE /comment/{commentId} 根据id删除评论
    @RequestMapping(value = "{commentId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String commentId) {
        commentService.deleteById(commentId);
        return new Result(true, StatusCode.OK, "删除成功", "");
    }
}
