package com.ormlite.android.demo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zhangquan on 17/4/29.
 */

@DatabaseTable(tableName = "artical")
public class Artical {
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "content")
    private String content;
    @DatabaseField(columnName = "user_id",foreign = true,foreignAutoRefresh = true)
    //foreign=true表示是一个外键
    //添加foreignAutoRefresh =true，这样能直接查询出user
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Artical{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", user.id=" + (null!=user?user.getId():null) +
                '}';
    }
}
