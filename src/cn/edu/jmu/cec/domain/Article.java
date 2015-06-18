package cn.edu.jmu.cec.domain;

import java.sql.Timestamp;

public class Article implements java.io.Serializable {

    private Integer artId;
    private ArtColumn artColumn;
    private String title;
    private String content;
    private Boolean isAddress;
    private String outAddress;
    private String titlePic;
    private String summary;
    private String author;
    private Boolean isTop;
    private Boolean isColmunTop;
    private Boolean isIndexTop;
    private Timestamp publicTime;
    private Integer readCount;
    private Boolean isMark;
    private Short Status;
    private Boolean isNew;
    private Integer isNewTab;
    private Boolean isSchool;

    public Article() {
    }

    public Article(String title, String content, String author, Timestamp publicTime) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publicTime = publicTime;
    }

    public Article(ArtColumn artColumn, String title, String content, Boolean isAddress, String outAddress, String titlePic, String summary, String author, Boolean isTop, Boolean isColmunTop, Boolean isIndexTop, Timestamp publicTime, Integer readCount) {
        this.artColumn = artColumn;
        this.title = title;
        this.content = content;
        this.isAddress = isAddress;
        this.outAddress = outAddress;
        this.titlePic = titlePic;
        this.summary = summary;
        this.author = author;
        this.isTop = isTop;
        this.isColmunTop = isColmunTop;
        this.isIndexTop = isIndexTop;
        this.publicTime = publicTime;
        this.readCount = readCount;
    }



    public Integer getArtId() {
        return this.artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public ArtColumn getArtColumn() {
        return this.artColumn;
    }

    public void setArtColumn(ArtColumn artColumn) {
        this.artColumn = artColumn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTitlePic() {
        return this.titlePic;
    }

    public void setTitlePic(String titlePic) {
        this.titlePic = titlePic;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getIsTop() {
        return this.isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsColmunTop() {
        return this.isColmunTop;
    }

    public void setIsColmunTop(Boolean isColmunTop) {
        this.isColmunTop = isColmunTop;
    }

    public Boolean getIsIndexTop() {
        return this.isIndexTop;
    }

    public void setIsIndexTop(Boolean isIndexTop) {
        this.isIndexTop = isIndexTop;
    }

    public Timestamp getPublicTime() {
        return this.publicTime;
    }

    public void setPublicTime(Timestamp publicTime) {
        this.publicTime = publicTime;
    }

    public Integer getReadCount() {
        return this.readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Short getStatus() {
        return Status;
    }

    public void setStatus(Short status) {
        Status = status;
    }

    public Boolean getIsMark() {
        return isMark;
    }

    public void setIsMark(Boolean isMark) {
        this.isMark = isMark;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Integer getIsNewTab() {
        return isNewTab;
    }

    public void setIsNewTab(Integer isNewTab) {
        this.isNewTab = isNewTab;
    }

    public Boolean getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(Boolean isSchool) {
        this.isSchool = isSchool;
    }
}