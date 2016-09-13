package com.zzl.mmm.db;

import android.util.Log;

import com.zzl.mmm.application.MyApplication;
import com.zzl.mmm.bean.Person;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class PersonDB {
    /****************************************************************************************/
    //写两个测试方法，也就是常用的数据库操作
    public void savePerson(Person person){
        try {
            DBUtil.getDbManager().save(person);
            Log.d("xyz", "save succeed!");
        } catch (DbException e) {
            Log.d("xyz",e.toString());
        }
    }
    //将Person实例存进数据库
    public List<Person> loadPerson(){
        List<Person> list = null;
        try {
            list = DBUtil.getDbManager().selector(Person.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }
    //读取所有Person信息
}
