package com.tongxi.zhihudaily.model;

import java.util.List;

/**
 * Created by qf on 2016/10/20.
 */
public class ThemeContent {

    /**
     * stories : 该主题日报中的文章列表
     * description : 该主题日报介绍
     * background : 该主题日报背景图片（大图） http://pic1.zhimg.com/a5128188ed788005ad50840a42079c41.jpg
     * color : 颜色 作用未知 8307764
     * name : 该主题日报的名称 不许无聊
     * image : 背景图片的小图版本 http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg
     * editors : 该主题日报的编辑（『用户推荐日报』中此项的指是一个空数组，在 App 中的主编栏显示为『许多人』，点击后访问该主题日报的介绍页面，请留意）
     * image_source : 图像版权信息
     */

    private String description;
    private String background;
    private int color;
    private String name;
    private String image;
    private String image_source;
    /**
     * images :
     * type : 0
     * id : 7468668
     * title : 不许无聊在读读日报里等你哟
     */

    private List<StoriesBean> stories;
    /**
     * url : 主编的知乎用户主页 http://www.zhihu.com/people/wezeit
     * bio : 主编的个人简介 微在 Wezeit 主编
     * id :  数据库的唯一标识符 70
     * avatar : 主编的头像 http://pic4.zhimg.com/068311926_m.jpg
     * name : 主编的姓名 益康糯米
     */

    private List<EditorsBean> editors;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<EditorsBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsBean> editors) {
        this.editors = editors;
    }

    public static class StoriesBean {
        private int type;       //类型  作用未知
        private int id;         //数据库中唯一标识
        private String title;   //消息的标题
        private List<String> images;    //图像地址（其类型为数组。请留意在代码中处理无该属性与数组长度为 0 的情况）

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class EditorsBean {
        private String url;
        private String bio;
        private int id;
        private String avatar;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
