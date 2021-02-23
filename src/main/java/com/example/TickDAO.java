package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TickDAO {

	@Autowired
	private DataSource dataSource;

	private List<Timestamp> loadTicks(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

		List<Timestamp> result = new ArrayList<>();
		while (rs.next()) {
			result.add(rs.getTimestamp("tick"));
		}

		return result;
	}

	public List<Timestamp> loadTick() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();

			// create table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");

			// add tick
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");

			return loadTicks(stmt);

		} catch (SQLException e) {
			throw e;
		}
	}

}
