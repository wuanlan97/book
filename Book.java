package com.example.wuanlan97.androidexam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by wuanlan97 on 2017/12/14.
 */
/*************************************************
数据库表结构：
TABLE user(    //用户表
 username text primary key not null   //用户名，不允许重复
 name text not null    //昵称
 password text not null  //密码
 )

 TABLE book(   //记账表
 username text not null,  //用户名
 date text not null,    //账目时间
 money real not null   //金额（收入为正数，支出为负数）
 )

 函数表：
 void init(Context)   类初始化函数，传入上下文，实例化类之后必须初始化！！！
 注意：以下函数传入变量所有都为String类型！！！
 boolean login(username,password)  登录函数，传入用户名和密码，返回真则登录通过
 boolean signin(name,username,password)  注册函数，传入昵称（允许重复）、用户名（不允许重复）、密码，返回真则注册通过
 Boolean income(money)//增加收入记录，传入金额
 Boolean income(money,date)//增加收入记录,传入金额和时间（时间格式：yyyy-MM-dd HH:mm:ss）
 Boolean pay(money)//增加支出记录，传入金额
 Boolean income(money,date)//增加收入记录,,传入金额和时间（时间格式：yyyy-MM-dd HH:mm:ss）
 String getName()返回当前用户昵称
 boolean updatename(name)//修改昵称，传入新昵称，成功则返回真
 boolean updatepassword(password)//修改密码，传入新密码，成功则返回真
 ArrayAdapter<String> selectall()//查询当前用户所有记录
 ArrayAdapter<String> selectincome()//查询当前用户所有收入记录
 ArrayAdapter<String> selectpay()//查询当前用户所有支出记录
 ArrayAdapter<String> selectdate(selectdate)//查询当前用户所有支出记录,传入时间（时间格式：yyyy-MM-dd HH:mm:ss）

 提示：
 查询函数返回的为ArrayAdapter适配器，可直接绑定ListView
 例如：ListView.setAdapter(b.selectincome());
****************************************************/
public class Book {
    private static String username;//用户名
    private static String name;//昵称
    private Context mContext;
    private static MyDB DB;
    private static SQLiteDatabase sqlite;
    public void init(Context context)//初始化数据库
    {
        mContext=context;
        DB=new MyDB(mContext,"book.db",null,1);
        sqlite=DB.getWritableDatabase();

    }
    public boolean login (String username,String password)//登录函数，传入用户名和密码，返回真则为成功登录
    {
        String str="select name from user where username='";
        str+=username;
        str+="' and password='";
        str+=password;
        str+="'";
        try{
            Cursor cursor = sqlite.rawQuery(str, null);
            cursor.moveToFirst();
            Book.name=cursor.getString(cursor.getColumnIndex("name"));
            Book.username=username;
            final Toast toast = Toast.makeText(mContext,"登录成功",Toast.LENGTH_LONG);
            toast.show();
            cursor.close();
            return true;
        }
        catch(Exception e)
        {
            final Toast toast = Toast.makeText(mContext,"登录失败，请检查用户名或密码",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

    }
    public boolean signin (String name,String username,String password)//注册函数，传入昵称、用户名、密码，返回真则注册成功，返回假则注册失败（用户名已被注册）
    {
        if(username.equals("")||name.equals("")||password.equals(""))
        {
            final Toast toast = Toast.makeText(mContext,"注册失败：请填写完整",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        //构建sql语句
        String str="insert into user (username,name,password) values('";
        str+=username;
        str+="','";
        str+=name;
        str+="','";
        str+=password;
        str+="')";

        //写数据可
        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"注册成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"注册失败：用户名重名",Toast.LENGTH_LONG);
            toast.show();
            return false;
    }
    }
    public Boolean income(String money)//增加收入记录
    {
        SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
        Date time=new Date(System.currentTimeMillis());
        String timestamp = formatter.format(time);
        String str="insert into book(username,date,money)values('";
        str+=username;
        str+="','";
        str+=timestamp;
        str+="','";
        str+=money;
        str+="')";
        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"记账成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"记账失败",Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }
    public Boolean income(String money,String date)//增加收入记录,时间格式：yyyy-MM-dd HH:mm:ss
    {
        String str="insert into book(username,date,money)values('";
        str+=username;
        str+="','";
        str+=date;
        str+="','";
        str+=money;
        str+="')";
        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"记账成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"记账失败",Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }
    public Boolean pay(String money)//增加支出记录
    {
        SimpleDateFormat formatter = new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");
        Date time=new Date(System.currentTimeMillis());
        String timestamp = formatter.format(time);
        String str="insert into book(username,date,money)values('";
        str+=username;
        str+="','";
        str+=timestamp;
        str+="','-";
        str+=money;
        str+="')";
        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"记账成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"记账失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return false;
    }
    public Boolean pay(String money,String date)//增加支出记录,时间格式：yyyy-MM-dd HH:mm:ss
    {
        String str="insert into book(username,date,money)values('";
        str+=username;
        str+="','";
        str+=date;
        str+="','-";
        str+=money;
        str+="')";
        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"记账成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"记账失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return false;
    }
    public String getName()
    {
        return name;
    }
    public boolean updatename(String name)//传入新昵称，成功则返回真
    {
        if(name.equals(""))
        {
            final Toast toast = Toast.makeText(mContext,"昵称不能为空",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        String str="update user set name='";
        str+=name;
        str+="' where username='";
        str+=username;
        str+="'";

        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"修改昵称成功",Toast.LENGTH_LONG);
            toast.show();
            Book.name=name;
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"修改昵称失败",Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }
    public boolean updatepassword(String password)//传入新密码，成功则返回真
    {
        if(password.equals(""))
        {
            final Toast toast = Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        String str="update user set password='";
        str+=password;
        str+="' where username='";
        str+=username;
        str+="'";

        try{
            DB.SQL(str,sqlite);
            final Toast toast = Toast.makeText(mContext,"修改密码成功",Toast.LENGTH_LONG);
            toast.show();
            return true;
        }catch(Exception e){
            final Toast toast = Toast.makeText(mContext,"修改密码失败",Toast.LENGTH_LONG);
            toast.show();
        }
        return false;
    }
    public ArrayAdapter<String> selectall()//查询当前用户所有记录
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1);
        String str="select * from book where username='";
        str+=username;
        str+="'order by date desc";
        try{
            Cursor cursor = sqlite.rawQuery(str, null);
            if(cursor.moveToFirst())
            {
                do {

                    String print="";
                    String date=cursor.getString(cursor.getColumnIndex("date"));
                    float money=cursor.getFloat(cursor.getColumnIndex("money"));
                    print+=date;
                    if(money>=0){print+="收入";}else{print+="支出";}
                    money=Math.abs(money);
                    print+=money;
                    print+="元";

                    adapter.add(print);
                }while(cursor.moveToNext());
                final Toast toast = Toast.makeText(mContext,"查询成功",Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                final Toast toast = Toast.makeText(mContext,"查询成功，无记录",Toast.LENGTH_LONG);
                toast.show();
            }
            cursor.close();
        }
        catch(Exception e)
        {
            final Toast toast = Toast.makeText(mContext,"查询失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return adapter;
    }
    public ArrayAdapter<String> selectincome()//查询当前用户所有收入记录
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1);
        String str="select * from book where money>=0 and username='";
        str+=username;
        str+="'order by date desc";
        try{
            Cursor cursor = sqlite.rawQuery(str, null);
            if(cursor.moveToFirst())
            {
                do {

                    String print="";
                    String date=cursor.getString(cursor.getColumnIndex("date"));
                    float money=cursor.getFloat(cursor.getColumnIndex("money"));
                    print+=date;
                    if(money>=0){print+="收入";}else{print+="支出";}
                    money=Math.abs(money);
                    print+=money;
                    print+="元";

                    adapter.add(print);
                }while(cursor.moveToNext());
                final Toast toast = Toast.makeText(mContext,"查询成功",Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                final Toast toast = Toast.makeText(mContext,"查询成功，无记录",Toast.LENGTH_LONG);
                toast.show();
            }
            cursor.close();
        }
        catch(Exception e)
        {
            final Toast toast = Toast.makeText(mContext,"查询失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return adapter;
    }
    public ArrayAdapter<String> selectpay()//查询当前用户所有支出记录
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1);
        String str="select * from book where money<0 and username='";
        str+=username;
        str+="'order by date desc";
        try{
            Cursor cursor = sqlite.rawQuery(str, null);
            if(cursor.moveToFirst())
            {
                do {

                    String print="";
                    String date=cursor.getString(cursor.getColumnIndex("date"));
                    float money=cursor.getFloat(cursor.getColumnIndex("money"));
                    print+=date;
                    if(money>=0){print+="收入";}else{print+="支出";}
                    money=Math.abs(money);
                    print+=money;
                    print+="元";

                    adapter.add(print);
                }while(cursor.moveToNext());
                final Toast toast = Toast.makeText(mContext,"查询成功",Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                final Toast toast = Toast.makeText(mContext,"查询成功，无记录",Toast.LENGTH_LONG);
                toast.show();
            }
            cursor.close();
        }
        catch(Exception e)
        {
            final Toast toast = Toast.makeText(mContext,"查询失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return adapter;
    }
    public ArrayAdapter<String> selectdate(String selectdate)//查询当前用户指定日期所有记录
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1);
        String str="select * from book where username='";
        str+=username;
        str+="'and date like'";
        str+=selectdate;
        str+="%'order by date desc";
        try{
            Cursor cursor = sqlite.rawQuery(str, null);
            if(cursor.moveToFirst())
            {
                do {

                    String print="";
                    String date=cursor.getString(cursor.getColumnIndex("date"));
                    float money=cursor.getFloat(cursor.getColumnIndex("money"));
                    print+=date;
                    if(money>=0){print+="收入";}else{print+="支出";}
                    money=Math.abs(money);
                    print+=money;
                    print+="元";

                    adapter.add(print);
                }while(cursor.moveToNext());
                final Toast toast = Toast.makeText(mContext,"查询成功",Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                final Toast toast = Toast.makeText(mContext,"查询成功，无记录",Toast.LENGTH_LONG);
                toast.show();
            }
            cursor.close();
        }
        catch(Exception e)
        {
            final Toast toast = Toast.makeText(mContext,"查询失败",Toast.LENGTH_LONG);
            toast.show();

        }
        return adapter;
    }

}
