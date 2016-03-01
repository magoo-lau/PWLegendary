package com.example.pwlegendary;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
	
	private final String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath()+"/PWData";
	private final static String DATABASE_NAME="PW.db";
    private final static int DATABASE_VERSION=1;
    //角色信息表
    private final static String TABLE_NAME="RoleInfo";
    public final static String FIELD_SysID="SysID";
    public final static String FIELD_SysName="SysName";
    private final static String SysSex="SysSex";
    private final static String TABLE_SysType="SysType";
    private final static String TABLE_SysExp="SysExp";
    private final static String TABLE_SysLev="SysLev";
    //仓库表
    private final static String TABLE_WarehouseTable="WarehouseTable";
    public final static String FIELD_WhID="WhID";
    public final static String FIELD_WhName="WhName";
    private final static String FIELD_WhNum="WhNum";
    private final static String FIELD_WhState="WhState";
    SQLiteDatabase db;
    
	public dbHelper(Context context)
    {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
	
	public void openDatabase() {
		File dir = new File(DATABASE_PATH);
		if(!dir.exists())
		{
			dir.mkdirs();
		}else
		{
			deleteDir(dir);
			dir.mkdirs();
		}
		db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
		String sql="Create table "+TABLE_NAME+"("+FIELD_SysID+" integer primary key autoincrement,"
		        +FIELD_SysName+" varchar(10),"+SysSex+" Integer,"+TABLE_SysType+" Integer,"
		        +TABLE_SysExp+" Integer,"
		        +TABLE_SysLev+" Integer);";
       
		String sql2="Create table "+TABLE_WarehouseTable+"("+FIELD_WhID+" integer primary key autoincrement,"
		        +FIELD_WhName+" varchar(15),"+FIELD_WhNum+" Integer,"+FIELD_WhState+" Integer);";
  
		db.execSQL(sql);
		db.execSQL(sql2);
	}
	
	//遍历删除
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

	//清空表数据
	public void clearFeedTable()
	{
		String sql = "DELETE FROM " + TABLE_NAME +";";
		db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
		db.execSQL(sql);
		revertSeq();
	}
	
	//还原主键
	private void revertSeq()
	{
		String sql = "update sqlite_sequence set seq=0 where name='"+TABLE_NAME+"'";
		db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
		db.execSQL(sql);
	}
	
	@Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        
    }
	
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    	String sql=" DROP TABLE IF EXISTS "+TABLE_NAME;
    	String sql2=" DROP TABLE IF EXISTS "+TABLE_WarehouseTable;
    	db.execSQL(sql);
    	db.execSQL(sql2);
        onCreate(db);
    }
    
    //查询所有记录
    public Cursor select()
    {
    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
        Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null,  " cId desc");
        return cursor;
    }
    //查询指定ID
    public Cursor selectId(String id)
    {
    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
    	String sql = "select "+TABLE_NAME+".* from "+TABLE_NAME+" where "+FIELD_SysID+"='"+id+"'";
    	Cursor cursor = db.rawQuery(sql, null);
    	return cursor;
    }
    //插入用户
    public void insertUser(String pwd)
    {
    	String sql = "insert into "+TABLE_NAME+"(pwd) values ('"+pwd+"')";
    	db.execSQL(sql);
    }
//    //修改用户
//    public int updateUser(String id,String pwd)
//    {
//    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
//        String where=FIELD_ID+"=?";
//        String[] whereValue={id};
//        ContentValues cv=new ContentValues(); 
//        cv.put(FIELD_TITLE, pwd);
//        int Flg = db.update(TABLE_NAME, cv, where, whereValue);
//        return Flg;
//    }
    /*
     * 更新角色经验值、等级
     * */
    public int updateRole(int exp,int lev)
    {
    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
    	String where=FIELD_SysID+"=?";
    	String[] whereValue={"1"};
    	ContentValues cv=new ContentValues();
    	cv.put(TABLE_SysExp, exp);
    	cv.put(TABLE_SysLev, lev);
    	int Flg = db.update(TABLE_NAME, cv, where, whereValue);
    	return Flg;
    }
    /*
     * 功能：插入数据
     * 参数：name 角色名字
     *      sex 角色性别
     *      type 角色类型
     *      hp 血量
     *      mp 法蓝
     *      exp 经验
     *      defa 战防御
     *      defb 法防御
     *      attack 攻击力
     *      lev 等级
     * */
    public long insert(String name,int sex,int type,int exp,int lev)
    {
    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
        ContentValues cv=new ContentValues(); 
        cv.put(FIELD_SysName, name);
        cv.put(SysSex, sex);
        cv.put(TABLE_SysType, type);
        cv.put(TABLE_SysExp, exp);
        cv.put(TABLE_SysLev, lev);
        long row=db.insert(TABLE_NAME, null, cv);
        return row;
    }
//    //删除
//    public int delete(String id)
//    {
//    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
//        String where=TABLE_cId+"=?";
//        String[] whereValue={id};
//        int Flg = db.delete(TABLE_NoteBook, where, whereValue);
//        return Flg;
//    }
//    //修改
//    public int update(String id,String Title,String Content)
//    {
//    	db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH+"/"+DATABASE_NAME,null);
//        String where=TABLE_cId+"=?";
//        String[] whereValue={id};
//        ContentValues cv=new ContentValues(); 
//        cv.put(TABLE_cTitle, Title);
//        cv.put(TABLE_cContent, Content);
//        int Flg = db.update(TABLE_NoteBook, cv, where, whereValue);
//        return Flg;
//    }
    public void close() {
        if (db != null) {
        	db.close();
        }
    }
}
