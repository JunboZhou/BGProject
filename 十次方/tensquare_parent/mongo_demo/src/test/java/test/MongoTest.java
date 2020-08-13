package test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.org.apache.xml.internal.security.Init;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MongoTest {
    // 客户端
    private MongoClient mongoClient;
    // 集合
    private MongoCollection<Document> comment;

    @Before
    public void init() {
        // 创建mongodb客户端
        mongoClient = new MongoClient("127.0.0.1");
        // 选择数据库
        MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
        // 获取集合
        comment = commentdb.getCollection("comment");
    }

    //查询所有数据db.comment.find()
    @Test
    public void test1() {
        // 使用集合进行查询
        FindIterable<Document> documents = comment.find();
        // 解析结果
        for (Document document : documents) {
            System.out.println("------------------------------");
            System.out.println("_id:" + document.get("_id"));
            System.out.println("content:" + document.get("content"));
            System.out.println("userid:" + document.get("userid"));
            System.out.println("thumbup:" + document.get("thumbup"));
        }
    }

    @After
    public void after() {
        // 释放资源 关闭客户端
        mongoClient.close();
    }

    // 根据条件_id查询数据
    @Test
    public void test2() {
        // 封装查询条件
        BasicDBObject bson = new BasicDBObject("_id", "1");
        // 执行查询
        FindIterable<Document> documents = comment.find(bson);
        for (Document document : documents) {
            System.out.println("------------------------------");
            System.out.println("_id:" + document.get("_id"));
            System.out.println("content:" + document.get("content"));
            System.out.println("userid:" + document.get("userid"));
            System.out.println("thumbup:" + document.get("thumbup"));
        }
    }
}
