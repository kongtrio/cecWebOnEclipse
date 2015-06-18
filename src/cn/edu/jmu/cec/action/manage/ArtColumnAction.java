package cn.edu.jmu.cec.action.manage;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ArtColumnEntry;
import cn.edu.jmu.cec.domain.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/2/2.
 */
public class ArtColumnAction extends BaseAction<ArtColumn> {

    private List<ArtColumnEntry> artColumnEntyrList = new ArrayList<ArtColumnEntry>();
    private int columnId;
    private ArtColumnEntry artColumnEntry;
    private int parentColId;
    private List<String> nameList;
    private int[] ids;

    public String showColumn() {
        List<ArtColumn> artColumnList = artColumnSevice.findIsParentWithAll();
        request.setAttribute("colmun", artColumnList);
        request.setAttribute("path", "showColumn");
        return "showColmun";
    }

    public ArtColumnEntry buildArtColumnEntry(ArtColumn artColumn) {
        ArtColumnEntry artColumnEntry = new ArtColumnEntry(artColumn.getColumnId(), artColumn.getColName(),
                artColumn.getColLevel(), artColumn.getArtCount(), null, artColumn.getIsAddress(), artColumn.getOutAddress(),
                artColumn.getIsNewWindows(), artColumn.getIsNav(), artColumn.getIsIndex());
        return artColumnEntry;
    }

    public String delColumn() {
        artColumnSevice.deleteAll(ids);
        return showColumn();
    }

    public void saveColumnAlter() {
        logger.info("save columnId=" + model.getColumnId());
        ArtColumn art = artColumnSevice.getById(model.getColumnId());
        if (isNotBlank(model.getColName()) && isNotBlank(model.getColLevel()) && art != null) {
            logger.info("begin to save");
            art.setColName(model.getColName().trim());
            art.setColLevel(model.getColLevel());
            art.setIsAddress(model.getIsAddress());
            if (model.getIsAddress()) {
                art.setOutAddress(model.getOutAddress().trim());
            } else {
                art.setIsNewWindows(model.getIsNewWindows());
                art.setIsNav(model.getIsNav());
                art.setIsIndex(model.getIsIndex());
            }
            artColumnSevice.modify(art);
            String msg = "";
            if (art.getArtColumn() != null) {
                msg = "child";
            } else {
                if(art.getArtColumns().size()>0){
                    msg = "hasChild";
                }else{
                    msg = "noChild";
                }

            }
            try {
                response.getWriter().write(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String input() {
        Integer cId = model.getColumnId();
        ArtColumn artColumn = artColumnSevice.getById(cId);
        ArtColumn parentColumn = artColumn.getArtColumn();
        if (parentColumn == null) {
            parentColumn = new ArtColumn();
            parentColumn.setColName("作为一级标题");
        }
        artColumnEntry = new ArtColumnEntry(artColumn.getColumnId(), artColumn.getColName(),
                artColumn.getColLevel(), artColumn.getArtCount(), parentColumn, artColumn.getIsAddress(), artColumn.getOutAddress(),
                artColumn.getIsNewWindows(), artColumn.getIsNav(), artColumn.getIsIndex());
        return "input";
    }

    public String addInput() {
        List<ArtColumn> artColumns = artColumnSevice.findIsParent(true);
        nameList = new ArrayList<String>();
        Iterator<ArtColumn> it = artColumns.iterator();
        while (it.hasNext()) {
            nameList.add(it.next().getColName());
        }
        return "addInput";
    }

    //前往添加新闻页面
    public String toAddPage() {
        List<ArtColumn> parent = artColumnSevice.findIsParentWithAll();
        request.setAttribute("parentCol", parent);
        request.setAttribute("path","addColumn");
        return "addColumn";
    }

    //添加新闻
    public String addColumn() {
        ArtColumn artColumn = (ArtColumn) model;
        if (artColumnSevice.findByName(artColumn.getColName()) != null) {
            request.setAttribute("result", "fail2Name");
            return toAddPage();
        }
        artColumn.setStatus((short) 1);
        artColumn.setArtCount(0);

        if (parentColId == 0) {
            artColumn.setArtColumn(null);
        } else {
            ArtColumn parentColumn = artColumnSevice.getById(parentColId);
            if (parentColumn != null) {
                artColumn.setArtColumn(parentColumn);
            }
        }
        artColumnSevice.add(artColumn);
        request.setAttribute("result", "success");
        return toAddPage();
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public List<ArtColumnEntry> getArtColumnEntyrList() {
        return artColumnEntyrList;
    }

    public void setArtColumnEntyrList(List<ArtColumnEntry> artColumnEntyrList) {
        this.artColumnEntyrList = artColumnEntyrList;
    }

    public ArtColumnEntry getArtColumnEntry() {
        return artColumnEntry;
    }

    public void setArtColumnEntry(ArtColumnEntry artColumnEntry) {
        this.artColumnEntry = artColumnEntry;
    }

    public int getParentColId() {
        return parentColId;
    }

    public void setParentColId(int parentColId) {
        this.parentColId = parentColId;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }
}
