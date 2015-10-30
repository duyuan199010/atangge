package com.strod.yssl.database;


import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.roid.db.BaseSQLiteHelperOrm;
import com.strod.yssl.bean.main.Collect;

public class SQLiteHelperOrm extends BaseSQLiteHelperOrm {

	@Override
	public void createTable() {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, Collect.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		super.onUpgrade(db, connectionSource, oldVersion, newVersion);
	}

	@Override
	public void dropTable() {
		// TODO Auto-generated method stub
		try {
			TableUtils.dropTable(connectionSource, Collect.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
