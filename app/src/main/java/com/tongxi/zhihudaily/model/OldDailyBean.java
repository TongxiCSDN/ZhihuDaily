package com.tongxi.zhihudaily.model;

import java.util.List;

/**
 * Created by qf on 2016/10/27.
 */
public class OldDailyBean {


    /**
     * date : 20130519
     * stories : [{"images":["http://p2.zhimg.com/96/81/9681ea63890cb65dab503bddfd7b7113_r.raw"],"type":0,"id":401,"ga_prefix":"051922","title":"学英语才是正经事儿"},{"images":["http://p1.zhimg.com/88/07/8807985324e4cf2ae315eaf1bcfeb22a_r.raw"],"type":0,"id":396,"ga_prefix":"051919","title":"中老年人和外星人的区别"},{"images":["http://p3.zhimg.com/b3/a8/b3a81050144a8684211f800982588ee3_r.raw"],"type":0,"id":395,"ga_prefix":"051918","title":"Windows 也能优雅地用？"},{"images":["http://p3.zhimg.com/b0/79/b079691703f8926fec05febe8fd21f84_r.raw"],"type":0,"id":394,"ga_prefix":"051917","title":"Kindle 到底好在哪儿？"},{"images":["http://p1.zhimg.com/4b/fa/4bfa1f143f01ff560893414cd64d9a97_r.raw"],"type":0,"id":390,"ga_prefix":"051915","title":"为什么光逃离不了黑洞"},{"images":["http://p2.zhimg.com/ab/19/ab19235b36da52420b6654c03b8d259c_r.raw"],"type":0,"id":388,"ga_prefix":"051913","title":"小龙虾怎么做比较好吃"}]
     */

    private String date;
    /**
     * images : ["http://p2.zhimg.com/96/81/9681ea63890cb65dab503bddfd7b7113_r.raw"]
     * type : 0
     * id : 401
     * ga_prefix : 051922
     * title : 学英语才是正经事儿
     */

    private List<StoriesBean> stories;

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

    public static class StoriesBean {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        private boolean multipic;

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
}
