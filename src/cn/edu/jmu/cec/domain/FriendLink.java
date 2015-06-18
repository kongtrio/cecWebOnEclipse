package cn.edu.jmu.cec.domain;



/**
 * FriendLink entity. @author MyEclipse Persistence Tools
 */

public class FriendLink  implements java.io.Serializable {


    // Fields    

     private Integer friendLinkId;
     private String name;
     private String address;
     private String pic;
     private String summary;
     private Boolean isIndex;


    // Constructors

    /** default constructor */
    public FriendLink() {
    }

	/** minimal constructor */
    public FriendLink(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public FriendLink(String name, String address, String pic, String summary, Boolean isIndex) {
        this.name = name;
        this.address = address;
        this.pic = pic;
        this.summary = summary;
        this.isIndex = isIndex;
    }

   
    // Property accessors

    public Integer getFriendLinkId() {
        return this.friendLinkId;
    }
    
    public void setFriendLinkId(Integer friendLinkId) {
        this.friendLinkId = friendLinkId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic() {
        return this.pic;
    }
    
    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSummary() {
        return this.summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getIsIndex() {
        return this.isIndex;
    }
    
    public void setIsIndex(Boolean isIndex) {
        this.isIndex = isIndex;
    }
   








}