package cn.edu.jmu.cec.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/2/4.
 */
public class Users {

        private Integer userId;
        private String userName;
        private String nickName;
        private String password;
        private Boolean ULevel;
        private Timestamp regTime;
        private Integer loginCount;
        private Timestamp lastTime;
        private String lastIp;
        private Integer artCount;
        private String remark;
        private Set colmunRights = new HashSet(0);


        // Constructors

        /** default constructor */
        public Users() {
        }

        /** minimal constructor */
        public Users(String userName, String nickName, String password, Timestamp regTime, Timestamp lastTime) {
            this.userName = userName;
            this.nickName = nickName;
            this.password = password;
            this.regTime = regTime;
            this.lastTime = lastTime;
        }

        /** full constructor */
        public Users(String userName, String nickName, String password, Boolean ULevel, Timestamp regTime, Integer loginCount, Timestamp lastTime, String lastIp, Integer artCount, String remark, Set colmunRights) {
            this.userName = userName;
            this.nickName = nickName;
            this.password = password;
            this.ULevel = ULevel;
            this.regTime = regTime;
            this.loginCount = loginCount;
            this.lastTime = lastTime;
            this.lastIp = lastIp;
            this.artCount = artCount;
            this.remark = remark;
            this.colmunRights = colmunRights;
        }


        // Property accessors

        public Integer getUserId() {
            return this.userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return this.nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Boolean getULevel() {
            return this.ULevel;
        }

        public void setULevel(Boolean ULevel) {
            this.ULevel = ULevel;
        }

        public Timestamp getRegTime() {
            return this.regTime;
        }

        public void setRegTime(Timestamp regTime) {
            this.regTime = regTime;
        }

        public Integer getLoginCount() {
            return this.loginCount;
        }

        public void setLoginCount(Integer loginCount) {
            this.loginCount = loginCount;
        }

        public Timestamp getLastTime() {
            return this.lastTime;
        }

        public void setLastTime(Timestamp lastTime) {
            this.lastTime = lastTime;
        }

        public String getLastIp() {
            return this.lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }

        public Integer getArtCount() {
            return this.artCount;
        }

        public void setArtCount(Integer artCount) {
            this.artCount = artCount;
        }

        public String getRemark() {
            return this.remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Set getColmunRights() {
            return this.colmunRights;
        }

        public void setColmunRights(Set colmunRights) {
            this.colmunRights = colmunRights;
        }









}
