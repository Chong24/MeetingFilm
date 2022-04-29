package com.mooc.meetingfilm.backendfilm.controller.vo;

import lombok.Data;

/**
 * 演员列表返回对象：用于返回给前端显示
 * @author wang
 * @create 2022-04-21
 */
@Data
public class DescribeActorsRespVO {

    private Integer actorId;
    private String actorName;

}
