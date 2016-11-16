package com.tongxi.zhihudaily.model;

import java.util.List;

/**
 * 包括stories  top_stories
 * Created by Administrator on 2016/10/19 0019.
 */
public class NewDailyBean {


    /**
     * date : 20161110
     * stories : [{"images":["http://pic2.zhimg.com/b1bf21f4b6a7b4b5851279dc517a613d.jpg"],"type":0,"id":8963864,"ga_prefix":"111009","title":"家庭里的「仪式感」多一点，孩子感受到的爱就多一点"},{"title":"打开上帝视角，重新发现一座城市","ga_prefix":"111008","images":["http://pic3.zhimg.com/c959c6353d0adb61c053757a8b1d8052.jpg"],"multipic":true,"type":0,"id":8963763},{"images":["http://pic4.zhimg.com/2e6a91f04f5f885119e2837be7488ebf.jpg"],"type":0,"id":8964773,"ga_prefix":"111007","title":"特朗普「逆袭」取胜，为什么所有预测机构都出错了？"},{"images":["http://pic3.zhimg.com/7c187dbb9c060748e162d234280168d2.jpg"],"type":0,"id":8958248,"ga_prefix":"111007","title":"说真的，看电影还有个好处是，可以止痛"},{"images":["http://pic3.zhimg.com/9c3c775781a74373023f55bca724cee2.jpg"],"type":0,"id":8964898,"ga_prefix":"111007","title":"年轻人独自一人居住，如何有效地保持自律？"},{"images":["http://pic4.zhimg.com/d1b96f8e289674d972f57c14a422a4db.jpg"],"type":0,"id":8964643,"ga_prefix":"111007","title":"读读日报 24 小时热门 TOP 5 · 特朗普总统的第一个任期"},{"images":["http://pic2.zhimg.com/80473eae77df078154818643ec3b7bdd.jpg"],"type":0,"id":8961174,"ga_prefix":"111006","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic4.zhimg.com/5c62faccf6a763a512b397cead2f7d43.jpg","type":0,"id":8964898,"ga_prefix":"111007","title":"年轻人独自一人居住，如何有效地保持自律？"},{"image":"http://pic2.zhimg.com/b4619a9eac3cc846da6503fdf4a44fb9.jpg","type":0,"id":8964773,"ga_prefix":"111007","title":"特朗普「逆袭」取胜，为什么所有预测机构都出错了？"},{"image":"http://pic1.zhimg.com/e5053d6285df25c8439add650db1d0fc.jpg","type":0,"id":8964643,"ga_prefix":"111007","title":"读读日报 24 小时热门 TOP 5 · 特朗普总统的第一个任期"},{"image":"http://pic4.zhimg.com/f13660640eb2cae39bb7508c664ff243.jpg","type":0,"id":8958778,"ga_prefix":"110811","title":"投票日是个大晴天，希拉里的胜算又增加了一点"},{"image":"http://pic3.zhimg.com/7766a061e3374ca2f498f6ed589e105e.jpg","type":0,"id":8942755,"ga_prefix":"110306","title":"这里是广告 · 斜杠青年的进阶，是斜杠中年还是高级玩家？"}]
     */

    private String date;
    /**
     * images : ["http://pic2.zhimg.com/b1bf21f4b6a7b4b5851279dc517a613d.jpg"]
     * type : 0
     * id : 8963864
     * ga_prefix : 111009
     * title : 家庭里的「仪式感」多一点，孩子感受到的爱就多一点
     */

    private List<StoriesBean> stories;
    /**
     * image : http://pic4.zhimg.com/5c62faccf6a763a512b397cead2f7d43.jpg
     * type : 0
     * id : 8964898
     * ga_prefix : 111007
     * title : 年轻人独自一人居住，如何有效地保持自律？
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;
        private boolean multipic;

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

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

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
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

    public static class TopStoriesBean {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        private boolean multipic;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
