package com.tongxi.zhihudaily.model;

/**
 * Created by qf on 2016/10/20.
 */
public class ExtraMessage {

    /**
     * long_comments : 长评数量
     * popularity : 点赞总数
     * short_comments : 短评数量
     * comments : 评论总数 （长评+短评）
     */

    private int long_comments;
    private int popularity;
    private int short_comments;
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
