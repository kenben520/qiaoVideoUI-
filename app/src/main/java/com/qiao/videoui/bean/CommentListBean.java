package com.qiao.videoui.bean;

import java.io.Serializable;
import java.util.List;

public class CommentListBean implements Serializable {


    /**
     * list : [{"comment_id":"5","userid":"xxxx","nickname":"xxxx","avatar":"http://xxxx","content":"xxxx","add_time":"2分钟前","commend_count":"30"}]
     * total : 66
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * comment_id : 5
         * userid : xxxx
         * nickname : xxxx
         * avatar : http://xxxx
         * content : xxxx
         * add_time : 2分钟前
         * commend_count : 30
         */

        private String comment_id;
        private String userid;
        private String nickname;
        private String avatar;
        private String content;
        private String add_time;
        private String commend_count;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getCommend_count() {
            return commend_count;
        }

        public void setCommend_count(String commend_count) {
            this.commend_count = commend_count;
        }
    }
}
