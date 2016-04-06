package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BaseObject;

import org.apache.log4j.Logger;

public abstract class BaseObjectDao<B extends BaseObject> {

	public static final Logger logger = Logger.getLogger(BaseObjectDao.class);

	protected String tableName;
	ArrayList<String> columnNames = new ArrayList<String>();

	public BaseObjectDao() {
		init();
	}

	public void add(B obj) {
		logger.info("���������� ������� � ����");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(tableName);
		sql.append(" (");
		StringBuilder values = new StringBuilder();
		values.append(" VALUES (");
		for (String s : columnNames) {
			if (s.equals("id")) {
				continue;
			}
			sql.append(s);
			sql.append(",");
			values.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		values.setCharAt(values.length() - 1, ')');
		sql.append(values);
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			connection = new DBConnection().initConnection();
			logger.info("����������� ����������");
			st = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			initAddStatement(st, obj);
			st.executeUpdate();
			logger.info("�������� ������");
			rs = st.getGeneratedKeys();
			rs.next();
			logger.info("��������� ��������������� id");
			obj.setId(rs.getInt(1));

		} catch (SQLException e) {
			logger.error(e);
		} finally {
			if (connection != null) {
				try {

					if (rs != null)
						rs.close();
					logger.info("ResultSet ������ ");
					logger.info("Statement ������");
					if (st != null)
						st.close();
					connection.close();
					logger.info("connection ������");
				} catch (SQLException e) {
					logger.warn(e);
				}
			}
		}
	}

	public B get(int id) {
		logger.info("��������� ������� �� ���� c id = " + id);
		StringBuilder sql = generateSelectQery().append(" WHERE id = ").append(id);
		B obj = null;
		ResultSet result = null;
		try {
			result = executeQuery(sql.toString());
			result.next();
			logger.info("��������� ��������� sql �������");
			obj = (B) baceObjectFactory(result);
		} catch (SQLException e) {
			logger.warn(e);
		} finally {
			try {
				if (result != null)
					result.close();
				logger.info("������ ResultSet");
			} catch (SQLException e) {
				logger.warn(e);
			}
		}
		return obj;
	}

	public ArrayList<B> getList() {
		logger.info("��������� ���� �������� �� ����");
		ArrayList<B> arr = new ArrayList<B>();
		StringBuilder sql = generateSelectQery();
		ResultSet result = null;
		try {
			result = executeQuery(sql.toString());
			while (result.next()) {
				arr.add((B) baceObjectFactory(result));
			}
			logger.info("�������� �������");
		} catch (SQLException e) {
			logger.warn(e);
		} finally {
			try {
				if (result != null)
					result.close();
				logger.info("������ ResultSet");
			} catch (SQLException e) {
				logger.warn(e);
			}
		}
		return arr;
	}

	public void update(B obj) {
		logger.info("���������� �������");
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET ");
		StringBuilder whereId = new StringBuilder();
		for (String s : columnNames) {
			if (s.equals("id")) {
				whereId.append(" WHERE ");
				whereId.append(s);
				whereId.append("=?");
				continue;
			}
			sql.append(s);
			sql.append("=?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(whereId);
		Connection connection = null;
		PreparedStatement st = null;
		try {
			connection = new DBConnection().initConnection();
			logger.info("����������� ����������");
			st = connection.prepareStatement(sql.toString());
			initUpdateStatement(st, obj);
			st.executeUpdate();
			logger.info("�������� Sql ������");
		} catch (SQLException e) {
			logger.warn(e);
		} finally {
			if (connection != null) {
				try {
					if (st != null)
						st.close();
					logger.info("������ Statement");
					connection.close();
					logger.info("������� ���������� � ��");
				} catch (SQLException e) {
					logger.warn(e);
				}
			}
		}
	}

	public void delete(B obj) {

		logger.info("�������� �������");
		String sql = "DELETE FROM " + tableName + " WHERE id = ?";
		Connection connection = null;
		PreparedStatement st = null;
		try {
			connection = new DBConnection().initConnection();
			st = connection.prepareStatement(sql);
			st.setInt(1, obj.getId());
			st.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					if (st != null)
						st.close();
					logger.info("������ Statement");
					connection.close();
					logger.info("�������� ���������� � ��");
				} catch (SQLException e) {
					logger.warn(e);
				}
			}
		}
	}

	protected ResultSet executeQuery(String sql) throws SQLException {
		Connection connection = new DBConnection().initConnection();
		logger.info("���������� � �� �����������");

		Statement st = connection.createStatement();
		st.executeQuery(sql);
		logger.info("�������� sql ������");
		ResultSet rs = st.getResultSet();
		logger.info("��������� ������");
		return rs;
	}

	protected abstract B baceObjectFactory(ResultSet res) throws SQLException;

	protected StringBuilder generateSelectQery() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		for (String str : columnNames) {
			sql.append(str);
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" FROM ");
		sql.append(tableName);
		return sql;
	}

	protected abstract void init();

	protected abstract void initAddStatement(PreparedStatement st, B obj) throws SQLException;

	protected abstract void initUpdateStatement(PreparedStatement st, B obj) throws SQLException;
}