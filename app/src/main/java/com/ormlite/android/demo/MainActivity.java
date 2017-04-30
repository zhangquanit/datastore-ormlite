package com.ormlite.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private Dao<User, Integer> userDao;
    private Dao<Artical, Integer> articalDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.create).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);

        try {
            userDao = DataBaseHelper.getInstance(this).getDao(User.class);
            articalDao = DataBaseHelper.getInstance(this).getDao(Artical.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /*
     User(1)-----Artical(N)
     User与Artical关联后，
     1、查询
     查询user能够直接查询出Artical，同时查询Artical也能查询出它所属的User
     2、插入
     需要先插入User，然后再插入Artical，而且artical需要注入User，这样才能建立关系


     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create:
                User user = new User();
                user.setUsername("zhangquan");
                user.setBirth(System.currentTimeMillis());

                try {
                    int create = userDao.create(user); //create=1 添加的条数  生成的id会自动注入到user对象中
                    System.out.println("create=" + create);
                    System.out.println(user);

                    List<User> users = userDao.queryForAll();
                    System.out.println("query users=" + users);

//
                    List<Artical> articals = new ArrayList<>();
                    Artical artical = new Artical();
                    artical.setName("文章1");
                    artical.setContent("内容1");
                    artical.setUser(user); //关联user

                    articals.add(artical);

                    articalDao.create(articals);

                    articals = articalDao.queryForAll();
                    System.out.println("query articals=" + articals);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

//                public int create(T data) throws SQLException;
//                public int create(Collection<T> datas) throws SQLException;
//                public T createIfNotExists(T data) throws SQLException;
//                public Dao.CreateOrUpdateStatus createOrUpdate(T data) throws SQLException;

                break;
            case R.id.update:

//                public int update(T data) throws SQLException;
//                public int updateId(T data, ID newId) throws SQLException;
//                public int update(PreparedUpdate<T> preparedUpdate) throws SQLException;
//                public int updateRaw(String statement, String... arguments) throws SQLException;

                break;
            case R.id.delete:
                try {
                    int del = userDao.deleteById(1);
                    System.out.println("del=" + del);

                    user = new User();
                    user.setUsername("zhangquan");
                    user.setId(1);
                    del = userDao.delete(user);

                    System.out.println("del=" + del);

//                    public int delete(T data) throws SQLException;
//                    public int delete(Collection<T> datas) throws SQLException;
//                    public int deleteById(ID id) throws SQLException;
//                    public int deleteIds(Collection<ID> ids) throws SQLException;
//                    public int delete(PreparedDelete<T> preparedDelete) throws SQLException;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.query:
                System.out.println("----------query users-------");
                try {
                    List<User> users = userDao.queryForAll();
                    for(User item:users){
                        System.out.println("user="+item);
                        System.out.println("user.artials="+Arrays.toString(item.getArticals().toArray()));
                    }


                   System.out.println("--------query articals-------");

                    List<Artical> articals = articalDao.queryForAll();
                    System.out.println("articals="+articals);
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                System.out.println("-------queryBuilder--------");
                //queryBuilder 查询
                try {
                    Where<User, Integer> queryBuilder = userDao.queryBuilder().where().eq("username", "zhangquan");//select * from user where username="";
                    List<User> userList = queryBuilder.query();
                    System.out.println("userList="+userList);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
