package com.mooc.meetingfilm.backendshowtestng.demo;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;

@Slf4j
public class HelloWorld {

    @Test
    public void test(){
        log.error("this is test!");
    }

    @Test
    public void test02(){
        log.error("this is test02!");
    }

    //@BeforeMethod、@AfterMethod是每一个test方法都要执行一次
    @BeforeMethod
    public void beforemethod(){
        log.info("this is beforemethod test!");
    }

    @AfterMethod
    public void aftermethod(){
        log.info("this is aftermethod test!");
    }

    //@BeforeClass和@AfterClass是一个测试类执行一次
    @BeforeClass
    public void beforeclass(){
        log.info("this is beforeclass test!");
    }

    @AfterClass
    public void afterclass(){
        log.info("this is afterclass test!");
    }

    //@BeforeSuite、@AfterSuite最先执行的
    @BeforeSuite
    public void beforesuite(){
        log.info("this is beforesuite test!");
    }

    @AfterSuite
    public void aftersutie(){
        log.info("this is aftersutie test!");
    }

}
