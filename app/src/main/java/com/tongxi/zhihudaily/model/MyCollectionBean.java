package com.tongxi.zhihudaily.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qf on 2016/11/8.
 */
@Entity
public class MyCollectionBean {
    @Id(autoincrement = true)
    private Long id;
    private int newsId;
    private boolean multipic;
    private String image;
    private String title;
    @Generated(hash = 1275642385)
    public MyCollectionBean(Long id, int newsId, boolean multipic, String image,
            String title) {
        this.id = id;
        this.newsId = newsId;
        this.multipic = multipic;
        this.image = image;
        this.title = title;
    }
    @Generated(hash = 1429354802)
    public MyCollectionBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getNewsId() {
        return this.newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public boolean getMultipic() {
        return this.multipic;
    }
    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MyCollectionBean{" +
                "id=" + id +
                ", newsId=" + newsId +
                ", multipic=" + multipic +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
