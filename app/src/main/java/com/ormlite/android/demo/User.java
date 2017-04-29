package com.ormlite.android.demo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhangquan on 17/4/29.
 */

@DatabaseTable(tableName = "tb_user")
public class User {
    @DatabaseField(generatedId = true)  //自动生成id，并注入到user对象中
    private int id;
    @DatabaseField(columnName = "username",dataType = DataType.STRING)
    private String username;
    @DatabaseField(columnName = "birth",dataType = DataType.LONG)
    private long birth;

    @ForeignCollectionField
    //每个User关联一个或多个Article,在查询User的时候，一并能够获取到user的articals的值
    private Collection<Artical> articals;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public Collection<Artical> getArticals() {
        return articals;
    }

    public void setArticals(Collection<Artical> articals) {
        this.articals = articals;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birth=" + birth +
                ", articals=" +(null!=articals? Arrays.toString(articals.toArray()):articals) +
                '}';
    }
}
