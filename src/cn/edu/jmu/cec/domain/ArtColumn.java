package cn.edu.jmu.cec.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * ArtColumn entity. @author MyEclipse Persistence Tools
 */

public class ArtColumn implements java.io.Serializable {

    // Fields

    private Integer columnId;
    private ArtColumn artColumn;
    private String colName;
    private Integer colLevel;
    private Integer artCount;
    private Boolean isAddress;
    private String outAddress;
    private Boolean isNewWindows;
    private Boolean isNav;
    private Boolean isIndex;
    private Short status;
    private Set articles = new HashSet(0);
    private Set artColumns = new HashSet(0);
    private Set colmunRights = new HashSet(0);
    private List listArticles;
    // Constructors

    /** default constructor */
    public ArtColumn() {
    }

    /** minimal constructor */
    public ArtColumn(String colName) {
        this.colName = colName;
    }

    /** full constructor */
    public ArtColumn(ArtColumn artColumn, String colName, Integer colLevel,
                     Integer artCount, Boolean isAddress, String outAddress,
                     Boolean isNewWindows, Boolean isNav, Boolean isIndex, Short status,
                     Set articles, Set artColumns, Set colmunRights) {
        this.artColumn = artColumn;
        this.colName = colName;
        this.colLevel = colLevel;
        this.artCount = artCount;
        this.isAddress = isAddress;
        this.outAddress = outAddress;
        this.isNewWindows = isNewWindows;
        this.isNav = isNav;
        this.isIndex = isIndex;
        this.status = status;
        this.articles = articles;
        this.artColumns = artColumns;
        this.colmunRights = colmunRights;
    }

    // Property accessors

    public Integer getColumnId() {
        return this.columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public ArtColumn getArtColumn() {
        return this.artColumn;
    }

    public void setArtColumn(ArtColumn artColumn) {
        this.artColumn = artColumn;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Integer getColLevel() {
        return this.colLevel;
    }

    public void setColLevel(Integer colLevel) {
        this.colLevel = colLevel;
    }

    public Integer getArtCount() {
        return this.artCount;
    }

    public void setArtCount(Integer artCount) {
        this.artCount = artCount;
    }

    public Boolean getIsAddress() {
        return this.isAddress;
    }

    public void setIsAddress(Boolean isAddress) {
        this.isAddress = isAddress;
    }

    public String getOutAddress() {
        return this.outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }

    public Boolean getIsNewWindows() {
        return this.isNewWindows;
    }

    public void setIsNewWindows(Boolean isNewWindows) {
        this.isNewWindows = isNewWindows;
    }

    public Boolean getIsNav() {
        return this.isNav;
    }

    public void setIsNav(Boolean isNav) {
        this.isNav = isNav;
    }

    public Boolean getIsIndex() {
        return this.isIndex;
    }

    public void setIsIndex(Boolean isIndex) {
        this.isIndex = isIndex;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Set getArticles() {
        return this.articles;
    }

    public void setArticles(Set articles) {
        this.articles = articles;
    }

    public Set getArtColumns() {
        return this.artColumns;
    }

    public void setArtColumns(Set artColumns) {
        this.artColumns = artColumns;
    }

    public Set getColmunRights() {
        return this.colmunRights;
    }

    public void setColmunRights(Set colmunRights) {
        this.colmunRights = colmunRights;
    }

    public List getListArticles() {
        return listArticles;
    }

    public void setListArticles(List<Article> listArticles) {
        this.listArticles = listArticles;
    }

    public ArtColumn cloneColumn(){
        ArtColumn artColumn1 = new ArtColumn();
        artColumn1.setColName(this.colName);
        artColumn1.setColumnId(this.columnId);
        return artColumn1;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ArtColumn)){
             return false;
        }
        ArtColumn column = (ArtColumn) obj;
        if(this.getColumnId()==((ArtColumn) obj).getColumnId()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return this.colName;
    }
}