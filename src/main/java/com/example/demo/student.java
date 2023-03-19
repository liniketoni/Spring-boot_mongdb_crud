package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author：LiKe
 * @Package：com.example.demo
 * @Project：demo
 * @name：student
 * @Date：2023/3/19 11:45
 * @Filename：student
 */
@Data
@AllArgsConstructor
public class student implements Serializable {
    public String name;
    public int age;
    public Boolean sex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    public Date date;
}
