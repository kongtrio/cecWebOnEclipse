package cn.edu.jmu.cec.action.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.edu.jmu.cec.action.BaseAction;
import cn.edu.jmu.cec.domain.ArtColumn;
import cn.edu.jmu.cec.domain.ColmunRight;
import cn.edu.jmu.cec.domain.Users;
import net.sf.json.JSONObject;


public class ColumnRightAction extends BaseAction<ColmunRight> {

    private int columnId;
    private int userId;

    public String showColumnRight() {
        List<ArtColumn> artColumnList = artColumnSevice.findIsParentWithAll();
        List<Users> users = usersService.findAll();
        request.setAttribute("colmun", artColumnList);
        request.setAttribute("users", users);
        request.setAttribute("path", "columnRight");
        return "columnRight";
    }

    public void addRight() {
        logger.info("add columnRight:userID=" + userId + ",columnId=" + columnId);
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();

        Users user = usersService.getById(userId);
        ArtColumn artColumn = artColumnSevice.getById(columnId);
        boolean flag = false;
        if (user != null & artColumn != null) {
            flag = true;
            colmunRightService.add(user, artColumn);

            ArtColumn columnById = artColumnSevice.getById(columnId);
            List<String> users = new ArrayList<String>();
            for (ColmunRight colmunRight : (Set<ColmunRight>) columnById.getColmunRights()) {
                String userStr = colmunRight.getUsers().getUserId() + "_" + colmunRight.getUsers().getUserName();
                users.add(userStr);
            }
            json.put("users", users);
            json.put("columnId", columnId);
        }
        json.put("result", flag);
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delRight() {
        logger.info("del columnRight:userID=" + userId + ",columnId=" + columnId);
        response.setContentType("text/html;charset=UTF-8");
        JSONObject json = new JSONObject();
        Users user = usersService.getById(userId);
        ArtColumn artColumn = artColumnSevice.getById(columnId);
        boolean flag = false;
        if (user != null & artColumn != null) {
            flag = true;
            colmunRightService.delete(user, artColumn);
            ArtColumn columnById = artColumnSevice.getById(columnId);
            List<String> users = new ArrayList<String>();
            for (ColmunRight colmunRight : (Set<ColmunRight>) columnById.getColmunRights()) {
                String userStr = colmunRight.getUsers().getUserId() + "_" + colmunRight.getUsers().getUserName();
                users.add(userStr);
            }
            json.put("users", users);
            json.put("columnId", columnId);
        }
        json.put("result", flag);
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
