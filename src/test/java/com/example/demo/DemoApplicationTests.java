package com.example.demo;

import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    public MongoTemplate mongoTemplate;

    public student st;

    @Test
    void contextLoads() {
        System.out.print("AAA");
    }

    @Test
    void MongoCreateCollection(){


        Boolean emp = mongoTemplate.collectionExists("emp");
        System.out.print("执行成功当前集合是否存在:"+emp);
        if(!emp){
            mongoTemplate.createCollection("emp");
        }else{
            mongoTemplate.dropCollection("emp");
        }
    }

    @Test
    void MongoInsertCollection(){


        mongoTemplate.dropCollection(student.class);

        List<student> list = Arrays.asList(
                new student("name1",23,false,new Date()),
                new student("name2",21,false,new Date()),
                new student("name3",22,false,new Date()),
                new student("name4",26,true,new Date()),
                new student("name5",27,false,new Date()),
                new student("name6",25,true,new Date())
        );
        mongoTemplate.insert(list,"emp");
        mongoTemplate.insert(list,student.class);

    }


    @Test
    void MongoFindCollection(){


        //条件查询gte:大于等于；gt:大于；lt:小于；regex:模糊；and：并且；andOperator:并且
        //orOperator:并且；is:等于；lte:小于等于；in:包含；nin:不包含于
        Query querygte = new Query(Criteria.where("sex").gte(false));
        Query querygt = new Query(Criteria.where("age").gte(22));
        Query querylt = new Query(Criteria.where("age").gte(25));
        Query queryregex= new Query(Criteria.where("name").regex("1"));
        Criteria criteria = new Criteria();
        Query queryltgt = new Query(criteria.andOperator(Criteria.where("age").gte(23),Criteria.where("age").lte(25)));

        System.out.print("=====================limit.skip==================="+"\n");

        Query query = new Query();
        query.with(Sort.by(Sort.Order.asc("age"))).skip(0).limit(4);
        List<student>listlimit = mongoTemplate.find(query,student.class);
        listlimit.forEach(System.out::println);


        System.out.print("========================================"+"\n");
        List<student> list = mongoTemplate.find(querygt,student.class);
        list.forEach(System.out::println);
        System.out.print("========================================"+"\n");
        student one = mongoTemplate.findOne(queryregex,student.class);
        System.out.print(one+"\n");

        System.out.print("===================criteria.andOperator====================="+"\n");
        queryltgt.with(Sort.by(Sort.Order.desc("age")));
        List<student> e = mongoTemplate.find(queryltgt,student.class);
        e.forEach(System.out::println);
        System.out.print("========================================"+"\n");
        student biyid = mongoTemplate.findById(1,student.class);
        System.out.print(biyid);

    }

    @Test
    void MongoUpdateCollection(){
        //UpdateFirst()只跟新一次记录
        //updateMulti()只更新合适的记录、
        //Upsert()没有条件的时候插入数据
        System.out.print("======================数据更新前=================="+"\n");
        Query query = new Query(Criteria.where("age").is(22));
        List<student> list = mongoTemplate.find(query,student.class);
        list.forEach(System.out::println);


        System.out.print("======================数据更新后=================="+"\n");
        Update update = new Update();
        update.set("age","20");
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,student.class);
        List<student> listlater = mongoTemplate.findAll(student.class);
        listlater.forEach(System.out::println);

    }

    @Test
    void MongoDeleteCollection(){

        System.out.print("======================数据删除前=================="+"\n");
        List<student> listbefore = mongoTemplate.findAll(student.class);
        listbefore.forEach(System.out::println);

        System.out.print("======================数据删后=================="+"\n");
        Query query =new Query(Criteria.where("name").is("name1"));
        mongoTemplate.remove(query,student.class);
        List<student> listbelater = mongoTemplate.findAll(student.class);
        listbelater.forEach(System.out::println);
    }

}
