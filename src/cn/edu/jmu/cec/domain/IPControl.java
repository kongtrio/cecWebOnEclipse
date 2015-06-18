package cn.edu.jmu.cec.domain;



/**
 * FriendLink entity. @author MyEclipse Persistence Tools
 */

public class IPControl implements java.io.Serializable {
    // Fields

     private Integer id;
     private String ip;
     private String remark;
     private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}