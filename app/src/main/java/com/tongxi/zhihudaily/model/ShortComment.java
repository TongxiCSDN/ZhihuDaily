package com.tongxi.zhihudaily.model;

import java.util.List;

/**
 * Created by qf on 2016/10/20.
 */
public class ShortComment {

    /**
     * author : 评论作者
     * content : 评论内容
     * avatar : 用户头像的地址
     * time : 评论时间
     * id : 用户唯一标识符
     * likes : 点赞数量
     */

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }
}
