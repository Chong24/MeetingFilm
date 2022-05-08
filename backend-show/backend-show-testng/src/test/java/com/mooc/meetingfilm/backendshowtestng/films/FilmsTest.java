package com.mooc.meetingfilm.backendshowtestng.films;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mooc.meetingfilm.backendshowtestng.common.RestUtils;
import com.mooc.meetingfilm.common.vo.BaseResponseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author : jiangzh
 * @program : com.mooc.meetingfilm.testng.films
 * @description :
 **/
@Slf4j
public class FilmsTest {

    @Test
    public void addFilm(){
        String url = "http://localhost:8401/films/film:add";
        FilmSavedReqVO filmSavedReqVO = new FilmSavedReqVO();
        filmSavedReqVO.setFilmStatus("1");
        filmSavedReqVO.setFilmName("SpringCloud Jiangzh讲的课程");
        filmSavedReqVO.setFilmEnName("SpringCloud");
        filmSavedReqVO.setMainImgAddress("/imgs/main.jpg");
        filmSavedReqVO.setFilmScore("10.0");
        filmSavedReqVO.setFilmScorers("123456");
        filmSavedReqVO.setPreSaleNum("50000");
        filmSavedReqVO.setBoxOffice("90000");
        filmSavedReqVO.setFilmTypeId("1");
        filmSavedReqVO.setFilmSourceId("1");
        filmSavedReqVO.setFilmCatIds("1");
        filmSavedReqVO.setAreaId("1");
        filmSavedReqVO.setDateId("1");
        filmSavedReqVO.setFilmTime("2025-12-11");
        filmSavedReqVO.setDirectorId("1");
        filmSavedReqVO.setActIds("1,2");
        filmSavedReqVO.setRoleNames("管理员,实习");
        filmSavedReqVO.setFilmLength("20");
        filmSavedReqVO.setBiography("SpringCloud Jiangzh讲的课程");
        filmSavedReqVO.setFilmImgs("/imgs/1.jpg,/imgs/2.jpg,/imgs/3.jpg,/imgs/4.jpg,/imgs/5.jpg");

        RestTemplate restTemplate = RestUtils.getRestTemplate();
        ResponseEntity<BaseResponseVO> baseresponse
                = restTemplate.postForEntity(url, filmSavedReqVO, BaseResponseVO.class);
        log.info("addFilm baseresponse : {}", baseresponse);
        // 验证返回值的Code是不是200
        BaseResponseVO body = baseresponse.getBody();
        Integer code = new Integer(200);

        // 第一道拦截
        Assert.assertEquals(code, body.getCode());
    }


    @Test(dataProvider = "filmsDataProvider")
    public void films(String filmsName, int expectCounts) {
        //提供uri供远程调用，写死的不用开启Eureka服务
        String uri = "http://localhost:8401/films";
        RestTemplate restTemplate = RestUtils.getRestTemplate();
        String response
                = restTemplate.getForObject(uri, String.class);
        log.info("response : {}", response);
        JSONObject result = JSONObject.parseObject(response);
        // 对查出的数据进行校验，是否符合期望的：比如数量是否大于1

        // 名字与插入的内容是否相同
        JSONObject data = result.getJSONObject("data");
        JSONArray films = data.getJSONArray("films");

        // 成功计数器
        int count = 0;

        List<DescribeFilmsRespVO> describeFilmsRespVOS = films.toJavaList(DescribeFilmsRespVO.class);
        for(DescribeFilmsRespVO vo : describeFilmsRespVOS){
            if(vo.getFilmEnName().equals(filmsName)){
                count ++ ;
            }
        }

        log.info("count : {}", count);
        Assert.assertEquals(count, expectCounts);
    }

    //标记一种方法来提供测试方法的数据。 注释方法必须返回一个Object [] []，其中每个Object []可以被分配给测试方法的参数列表。
    // 要从该DataProvider接收数据的@Test方法需要使用与此注释名称相等的dataProvider名称。
    @DataProvider(name = "filmsDataProvider")
    public Object[][] filmsDataProvider(){
        Object[][] objects = new Object[][]{
                {"SpringCloud", 1},
                {"SpringCloud2", 0}
        };

        return objects;
    }

    //单元测试，无法直到其他微服务的JavaBean，所以在测试类定义一个一样的
    @Data
    public static class DescribeFilmsRespVO{

        private String filmId;
        private String filmStatus;
        private String filmName;
        private String filmEnName;
        private String filmScore;
        private String preSaleNum;
        private String boxOffice;
        private String filmTime;
        private String filmLength;
        private String mainImg;

    }

    @Data
    public static class FilmSavedReqVO{
        private String filmStatus;
        private String filmName;
        private String filmEnName;
        private String mainImgAddress;
        private String filmScore;
        private String filmScorers;
        private String preSaleNum;
        private String boxOffice;
        private String filmTypeId;
        private String filmSourceId;
        private String filmCatIds;
        private String areaId;
        private String dateId;
        private String filmTime;
        private String directorId;
        private String actIds;      // actIds与RoleNames是不是能在数量上对应上
        private String roleNames;
        private String filmLength;
        private String biography;
        private String filmImgs;
    }

}

