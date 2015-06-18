package cn.edu.jmu.cec.domain;



/**
 * ColmunRight entity. @author MyEclipse Persistence Tools
 */

public class ColmunRight  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private ArtColumn artColumn;
     private Users users;


    // Constructors

    /** default constructor */
    public ColmunRight() {
    }

    
    /** full constructor */
    public ColmunRight(ArtColumn artColumn, Users users) {
        this.artColumn = artColumn;
        this.users = users;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public ArtColumn getArtColumn() {
        return this.artColumn;
    }
    
    public void setArtColumn(ArtColumn artColumn) {
        this.artColumn = artColumn;
    }

    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
   








}