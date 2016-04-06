package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UsersDao extends BaseObjectDao<User> {
	private static UsersDao instance = null;

	public static UsersDao getInstance() {
		if (instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	@Override
	protected void init() {
		this.tableName = "users";
		this.columnNames.add("id");
		this.columnNames.add("name");
		this.columnNames.add("surname");
		this.columnNames.add("age");
		this.columnNames.add("addres");
		this.columnNames.add("salary");
	}

	@Override
	protected void initAddStatement(PreparedStatement st, User obj) throws SQLException {
		st.setString(1, obj.getName());
		st.setString(2, obj.getSurname());
		st.setInt(3, obj.getAge());
		st.setString(4, obj.getAddres());
		st.setInt(5, obj.getSalary());
	}

	@Override
	protected void initUpdateStatement(PreparedStatement st, User obj) throws SQLException {
		initAddStatement(st, obj);
		st.setInt(6, obj.getId());
	}

	@Override
	protected User baceObjectFactory(ResultSet res) throws SQLException {
		User usr = new User();
		usr.setId(res.getInt("id"));
		usr.setName(res.getString("name"));
		usr.setSurname(res.getString("surname"));
		usr.setAge(res.getInt("age"));
		usr.setAddres(res.getString("addres"));
		usr.setSalary(res.getInt("salary"));
		return usr;
	}

}