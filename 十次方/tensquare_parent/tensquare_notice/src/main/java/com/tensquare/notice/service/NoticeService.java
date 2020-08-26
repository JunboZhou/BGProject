package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

//    @Autowired
//    private ArticleClient articleClient;
//
//    @Autowired
//    private UserClient userClient;

    @Autowired
    private IdWorker idWorker;

    public Notice selectById(String id) {
        Notice notice = noticeDao.selectById(id);
        // 完善消息
        return notice;
    }

    public Page<Notice> selectByPage(Map<String, Object> map, Integer page, Integer size) {
        // 设置查询对象
        EntityWrapper<Notice> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            wrapper.eq(map.get(key) != null, key, map.get(key));
        }
        // 封装分页对象
        Page<Notice> pageData = new Page<>(page, size);
        // 执行分页查询
        List<Notice> noticeList = noticeDao.selectPage(pageData, wrapper);

        // 设置结果到分页对象中
        pageData.setRecords(noticeList);
        return pageData;
    }

    public void save(Notice notice) {
        //设置初始值
        //设置状态 0表示未读  1表示已读
        notice.setState("0");
        notice.setCreatetime(new Date());

        // 使用分布式id生成器生成id
        String id = idWorker.nextId() + "";
        notice.setId(id);
        noticeDao.insert(notice);
        
        // 待推送消息入库, 新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(id);
        noticeFresh.setUserId(notice.getReceiverId());
        noticeFreshDao.insert(noticeFresh);
    }

    public void updateById(Notice notice) {
        noticeDao.updateById(notice);
    }

    public Page<NoticeFresh> freshPage(String userId, Integer page, Integer size) {
        // 封装查询条件
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(userId);

        // 创建分页对象
        Page<NoticeFresh> pageData = new Page<>(page, size);
        // 执行查询
        List<NoticeFresh> list = noticeFreshDao.selectPage(pageData, new EntityWrapper<>(noticeFresh));
        // 设置查询结果集到分页对象中
        pageData.setRecords(list);
        return pageData;
    }

    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }
}
