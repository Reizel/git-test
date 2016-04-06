package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.City;

public class CityDao extends BaseObjectDao<City> {

	private static CityDao instance = null;

	public static CityDao getInstance() {
		if (instance == null) {
			instance = new CityDao();
		}
		return instance;
	}

	@Override
	protected void init() {
		this.tableName = "cities";
		this.columnNames.add("id");
		this.columnNames.add("cityname");
	}

	@Override
	protected void initAddStatement(PreparedStatement st, City obj) throws SQLException {
		st.setString(1, obj.getName());
	}

	@Override
	protected void initUpdateStatement(PreparedStatement st, City obj) throws SQLException {
		initAddStatement(st, obj);
		st.setInt(2, obj.getId());
	}

	@Override
	protected City baceObjectFactory(ResultSet res) throws SQLException {
		City city = new City();

		city.setId(res.getInt("id"));
		city.setName(res.getString("cityname"));
		return city;
	}
}