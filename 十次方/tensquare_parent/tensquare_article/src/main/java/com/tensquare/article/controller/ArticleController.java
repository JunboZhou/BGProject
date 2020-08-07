package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sun.jdi.Method;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
@CrossOrigin  // 跨域
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 公共异常处理
    @RequestMapping(value = "exception", method = RequestMethod.GET)
    public Result test() {
        int a = 1 / 0;
        return new Result(false, StatusCode.ERROR, "处理异常", null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result finsAll(){
        List<Article> list = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    // 新增标签数据接口
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "查询成功", "");
    }

    // 修改接口
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable String id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);

        return new Result(true, StatusCode.OK, "修改成功", null);
    }

    // 分页
    @RequestMapping(value = "search/{page}/{size}", method = RequestMethod.POST)
    public Result search(@RequestBody Map<String, Object> map, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageData = articleService.search(map, page, size);
        System.out.println(pageData.getTotal());
        //封装分页返回对象
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(), pageData.getRecords()
        );
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    //DELETE /article/{articleId} 根据ID删除文章
    @RequestMapping(value = "{articleId}", method = RequestMethod.DELETE)
    public Result deleteByID(@PathVariable String articleId) {
        articleService.deleteById(articleId);
        return new Result(true, StatusCode.OK, "删除成功", null);
    }

}
