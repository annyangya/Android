package com.example.apple.imookdemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by apple on 2018/3/19.
 */

public class costInfo implements Serializable{//序列化
    public String cost_title;
    public String cost_date;
    public String cost_money;

    public String getCost_title() {
        return cost_title;
    }

    public void setCost_title(String cost_title) {
        this.cost_title = cost_title;
    }

    public String getCost_date() {
        return cost_date;
    }

    public void setCost_date(String cost_date) {
        this.cost_date = cost_date;
    }

    public String getCost_money() {
        return cost_money;
    }

    public void setCost_money(String cost_money) {
        this.cost_money = cost_money;
    }
}
