/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.tablight.dataaddon.sqlite;

import dev.tablight.dataaddon.storeload.CustomDataStorage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLiteDataAddonStorage<T> implements CustomDataStorage<T>, AutoCloseable {
	private Connection connection;
	private Statement statement;

	public SQLiteDataAddonStorage() {
		try {
			File dataAddonDir = new File("DataAddon");
			String url = "jdbc:sqlite:DataAddon/data-addons.db";

			if (!dataAddonDir.exists()) dataAddonDir.mkdir();
			if (!dataAddonDir.isDirectory()) throw new IllegalStateException("DataAddon is not a directory!");

			final String sql = "CREATE TABLE IF NOT EXISTS DATAADDONS " +
					"(ID 			INT PRIMARY KEY    	NOT NULL," +
					" CLASS         TEXT    			NOT NULL, " +
					" MARK          TEXT     			NOT NULL)";

			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T obtainData() {
		//statement.executeUpdate("");
		return null;
	}

	@Override
	public void insertData(T type) {

	}

	@Override
	public void close() throws Exception {
		if (statement != null)
			statement.close();
		if (connection != null)
			connection.close();
	}
}
