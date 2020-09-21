package com.zxw.dto;

/**
 * @Description: 图表结果DTO
 * @Author Zhouxw
 * @Date 2020/9/18 0018 17:02
 **/
public class ChartDTO {
    private Long boy;
    private Long girl;
    private Long unknown;

    public ChartDTO() {
    }

    public ChartDTO(Long boy, Long girl, Long unknown) {
        this.boy = boy;
        this.girl = girl;
        this.unknown = unknown;
    }

    public Long getBoy() {
        return boy;
    }

    public void setBoy(Long boy) {
        this.boy = boy;
    }

    public Long getGirl() {
        return girl;
    }

    public void setGirl(Long girl) {
        this.girl = girl;
    }

    public Long getUnknown() {
        return unknown;
    }

    public void setUnknown(Long unknown) {
        this.unknown = unknown;
    }
}
