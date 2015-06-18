package cn.edu.jmu.cec.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ArtColumnEntry implements Serializable {

	private Integer columnId;
    private String colName;
    private Integer colLevel;
    private Integer artCount;
    private ArtColumn parentColumn;
    private Boolean isAddress;
    private String outAddress;
    private Boolean isNewWindows;
    private Boolean isNav;
    private Boolean isIndex;
    private Set articles = new HashSet(0);
    private Set colmunRights = new HashSet(0);
	private int countPic;
	
	
	
	public ArtColumnEntry(Integer columnId, String colName, Integer colLevel,
			Integer artCount, ArtColumn parentColumn, Boolean isAddress,
			String outAddress, Boolean isNewWindows, Boolean isNav,
			Boolean isIndex) {
		super();
		this.columnId = columnId;
		this.colName = colName;
		this.colLevel = colLevel;
		this.artCount = artCount;
		this.parentColumn = parentColumn;
		this.isAddress = isAddress;
		this.outAddress = outAddress;
		this.isNewWindows = isNewWindows;
		this.isNav = isNav;
		this.isIndex = isIndex;
	}


	public int getCountPic() {
		return countPic;
	}
	
	
	public Integer getColumnId() {
		return columnId;
	}


	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}


	public String getColName() {
		return colName;
	}


	public void setColName(String colName) {
		this.colName = colName;
	}


	public Integer getColLevel() {
		return colLevel;
	}


	public void setColLevel(Integer colLevel) {
		this.colLevel = colLevel;
	}


	public Integer getArtCount() {
		return artCount;
	}


	public void setArtCount(Integer artCount) {
		this.artCount = artCount;
	}

	
	public ArtColumn getParentColumn() {
		return parentColumn;
	}


	public void setParentColumn(ArtColumn parentColumn) {
		this.parentColumn = parentColumn;
	}


	public Boolean getIsAddress() {
		return isAddress;
	}


	public void setIsAddress(Boolean isAddress) {
		this.isAddress = isAddress;
	}


	public String getOutAddress() {
		return outAddress;
	}


	public void setOutAddress(String outAddress) {
		this.outAddress = outAddress;
	}


	public Boolean getIsNewWindows() {
		return isNewWindows;
	}


	public void setIsNewWindows(Boolean isNewWindows) {
		this.isNewWindows = isNewWindows;
	}


	public Boolean getIsNav() {
		return isNav;
	}


	public void setIsNav(Boolean isNav) {
		this.isNav = isNav;
	}


	public Boolean getIsIndex() {
		return isIndex;
	}


	public void setIsIndex(Boolean isIndex) {
		this.isIndex = isIndex;
	}


	public Set getArticles() {
		return articles;
	}


	public void setArticles(Set articles) {
		this.articles = articles;
	}


	public Set getColmunRights() {
		return colmunRights;
	}


	public void setColmunRights(Set colmunRights) {
		this.colmunRights = colmunRights;
	}


	public void setCountPic(int countPic) {
		this.countPic = countPic;
	}
}
