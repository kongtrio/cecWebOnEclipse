package cn.edu.jmu.cec.json;

import java.sql.Timestamp;

public class ArticleJson {
    private Integer artId;
    private int artColumnId;
    private String artColumnName;
    private Timestamp publicTime;
    private String title;
    private Boolean isNew;
    private Boolean isMark;
    private Boolean isSchool;
    private Boolean isTop;
    private Boolean isColmunTop;
    private Boolean isIndexTop;

    public Integer getArtId() {
        return artId;
    }

    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    public int getArtColumnId() {
        return artColumnId;
    }

    public void setArtColumnId(int artColumnId) {
        this.artColumnId = artColumnId;
    }

    public String getArtColumnName() {
        return artColumnName;
    }

    public void setArtColumnName(String artColumnName) {
        this.artColumnName = artColumnName;
    }

    public Timestamp getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Timestamp publicTime) {
        this.publicTime = publicTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsMark() {
        return isMark;
    }

    public void setIsMark(Boolean isMark) {
        this.isMark = isMark;
    }

    public ArticleJson(Integer artId, int artColumnId, String artColumnName,
                       Timestamp publicTime, String title) {
        super();
        this.artId = artId;
        this.artColumnId = artColumnId;
        this.artColumnName = artColumnName;
        this.publicTime = publicTime;
        this.title = title;
    }

    public Boolean getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(Boolean isSchool) {
        this.isSchool = isSchool;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsColmunTop() {
        return isColmunTop;
    }

    public void setIsColmunTop(Boolean isColmunTop) {
        this.isColmunTop = isColmunTop;
    }

    public Boolean getIsIndexTop() {
        return isIndexTop;
    }

    public void setIsIndexTop(Boolean isIndexTop) {
        this.isIndexTop = isIndexTop;
    }

    public ArticleJson() {
    }
}
