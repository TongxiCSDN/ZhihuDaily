package com.tongxi.zhihudaily.model;

import java.util.List;

/**
 * Created by qf on 2016/10/20.
 */
public class ThemeListBean {

    /**
     * limit : 1000  限制 -- 作用未知
     * subscribed : []   已经订阅的条目信息
     * others ：其他订阅的条目信息
     */

    private int limit;
    private List<SubscribedBean> subscribed;
    /**
     * color : 颜色 --- 用途未知
     * thumbnail 显示的图片地址: http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg
     * description 主题日报介绍: 了解自己和别人，了解彼此的欲望和局限。
     * id 主题日报编号: 13
     * name 主题日报名称: 日常心理学
     */

    private List<OthersBean> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<SubscribedBean> subscribed) {
        this.subscribed = subscribed;
    }

    public List<OthersBean> getOthers() {
        return others;
    }

    public void setOthers(List<OthersBean> others) {
        this.others = others;
    }

    public static class OthersBean {
        private int color;
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public OthersBean(String name) {
            this.name = name;
        }

        public OthersBean() {
        }

        public OthersBean(int color, String thumbnail, String description, int id, String name) {
            this.color = color;
            this.thumbnail = thumbnail;
            this.description = description;
            this.id = id;
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SubscribedBean {
        private int color;
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
