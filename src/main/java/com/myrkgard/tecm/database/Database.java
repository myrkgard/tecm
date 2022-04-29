package com.myrkgard.tecm.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.myrkgard.tecm.model.Document;
import com.myrkgard.tecm.model.Folder;

public class Database {
	private static Database instance = new Database();
	private final String DB_BASEURL = System.getenv().getOrDefault("TECM_DB_BASEURL", "jdbc:mysql://localhost/");
	private final String DB_NAME = "tecm";
	private final String DB_USER = System.getenv().getOrDefault("TECM_DB_USER", "root");
	private final String DB_PASS = System.getenv().getOrDefault("TECM_DB_PASS", "password");

	private Database() {
	}

	public static Database getInstance() {
		return instance;
	}

	public static String intToIdString(int n) {
		return String.format("%032d", n);
	}

	public void createDummy() {
		dropDb();

		createDb();
		createTables();

		addFolder(new Folder(intToIdString(1), null, "root", 2));
		addDocument(new Document(intToIdString(1), intToIdString(1), "doc_a", 1001));
		addDocument(new Document(intToIdString(2), intToIdString(1), "doc_b", 2002));

		addFolder(new Folder(intToIdString(2), intToIdString(1), "folder_a", 0));
		addDocument(new Document(intToIdString(3), intToIdString(2), "doc_c", 1001));
		addDocument(new Document(intToIdString(4), intToIdString(2), "doc_d", 2002));

		addFolder(new Folder(intToIdString(3), intToIdString(1), "folder_b", 0));
		addDocument(new Document(intToIdString(5), intToIdString(3), "doc_e", 1001));
		addDocument(new Document(intToIdString(6), intToIdString(3), "doc_f", 2002));
	}

	private void dropDb() {
		try (Connection conn = DriverManager.getConnection(DB_BASEURL, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();) {
			String sql = "DROP DATABASE tecm";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Could not drop databse");
		}
	}

	private void createDb() {
		// no database exists yet, so connect to DB_BASEURL (not to DB_BASEURL +
		// DB_NAME)
		try (Connection conn = DriverManager.getConnection(DB_BASEURL, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();) {
			String sql = "CREATE DATABASE tecm";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTables() {
		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();) {

			// TABLE documents
			String sql = "CREATE TABLE documents " + "(id CHAR(32) NOT NULL, " + " parentId CHAR(32) NOT NULL, "
					+ " name VARCHAR(255) NOT NULL, " + " size INTEGER NOT NULL, " + " PRIMARY KEY (id))";
			stmt.executeUpdate(sql);

			// TABLE FOLDERS
			sql = "CREATE TABLE folders " + "(id CHAR(32) NOT NULL, " + " parentId CHAR(32), "
					+ " name VARCHAR(255) NOT NULL, " + " number_of_children INTEGER NOT NULL, " + " PRIMARY KEY (id))";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addFolder(Folder folder) {
		// TODO: validate input
		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();) {

			if (folder.parentId == null) {
				String sql = "INSERT INTO folders VALUES (" + "'" + folder.id + "'" + ", " + "NULL" + ", " + "'"
						+ folder.name + "'" + ", " + Integer.toString(folder.numberOfChildren) + ")";
				stmt.executeUpdate(sql);
			} else {
				String sql = "INSERT INTO folders VALUES (" + "'" + folder.id + "'" + ", " + "'" + folder.parentId + "'"
						+ ", " + "'" + folder.name + "'" + ", " + Integer.toString(folder.numberOfChildren) + ")";
				stmt.executeUpdate(sql);
			}
			System.out.println("INSERTING FOLDER OK");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("INSERTING FOLDER FAILED");
			return false;
		}
	}

	public List<Folder> allFolders() {
		final String query = "SELECT * FROM folders";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			List<Folder> res = new ArrayList<Folder>();
			while (rs.next()) {
				res.add(new Folder(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("number_of_children")));
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Folder getRootFolder() {
		final String query = "SELECT * FROM folders WHERE parentId IS NULL";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				return new Folder(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("number_of_children"));
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Folder getFolderById(String id) {
		final String query = "SELECT * FROM folders WHERE id='" + id + "'";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				return new Folder(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("number_of_children"));
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Folder> getFoldersByParentId(String parentId) {
		final String query = "SELECT * FROM folders WHERE parentId='" + parentId + "'";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			List<Folder> res = new ArrayList<Folder>();
			while (rs.next()) {
				res.add(new Folder(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("number_of_children")));
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean addDocument(Document document) {
		// TODO: validate input
		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();) {

			String sql = "INSERT INTO documents VALUES (" + "'" + document.id + "'" + ", " + "'" + document.parentId
					+ "'" + ", " + "'" + document.name + "'" + ", " + Integer.toString(document.size) + ")";
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Document> allDocuments() {
		final String query = "SELECT * FROM documents";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			List<Document> res = new ArrayList<Document>();
			while (rs.next()) {
				res.add(new Document(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("size")));
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Document getDocumentById(String id) {
		final String query = "SELECT * FROM documents WHERE id='" + id + "'";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			while (rs.next()) {
				return new Document(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("size"));
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Document> getDocumentsByParentId(String parentId) {
		final String query = "SELECT * FROM documents WHERE parentId='" + parentId + "'";

		try (Connection conn = DriverManager.getConnection(DB_BASEURL + DB_NAME, DB_USER, DB_PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			List<Document> res = new ArrayList<Document>();
			while (rs.next()) {
				res.add(new Document(rs.getString("id"), rs.getString("parentId"), rs.getString("name"),
						rs.getInt("size")));
			}
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
