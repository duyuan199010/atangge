package com.roid.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.roid.AbsApplication;
import com.roid.config.AbsConfig;

public abstract class BaseSQLiteHelperOrm extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	public ConnectionSource connectionSource;
	
	public BaseSQLiteHelperOrm() {
		super(AbsApplication.getApplication(), AbsConfig.DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		this.connectionSource = connectionSource;
		createTable();
	}
	
	public abstract void createTable();

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
			int arg3) {
		this.connectionSource = connectionSource;
		dropTable();
		onCreate(db, connectionSource);
	}

	public abstract void dropTable();
}
