package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    // 查询所有
    public List<Article> findAll() {
        return articleDao.selectList(null);
    }

    public void save(Article article) {
        // 使用分布式id生成器
        String id = idWorker.nextId() + "";
        article.setId(id);
        // 初始化数据
        article.setVisits(0); // 浏览量
        article.setThumbup(0);  // 点赞数
        article.setComment(0); // 评论量
        // 新增
        articleDao.insert(article);
    }

    public void update(Article article) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("id", article.getId());
        articleDao.update(article, wrapper);
    }

    public Page<Article> search(Map<String, Object> map, Integer page, Integer size) {
        // 设置查询对象
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }
        // 设置分页参数
        Page<Article> pageData = new Page<>(page, size);
        // 执行查询
        List<Article> articles = articleDao.selectPage(pageData, wrapper);
        pageData.setRecords(articles);
        return pageData;
    }

    public void deleteById(String articleId) {
        articleDao.deleteById(articleId);
    }
}
