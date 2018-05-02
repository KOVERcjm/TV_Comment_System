package com.kover.tvcommentsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KOVER.
 */
public class Database extends SQLiteOpenHelper
{
	private static final String CREATE_TABLE = "create table user(id integer primary key autoincrement, phone integer(11), password text)";
	
	public Database(Context context)
	{
		super(context, "user.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
