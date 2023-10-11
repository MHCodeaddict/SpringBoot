package ca.sheridancollege.hussamos.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.hussamos.beans.PasswordRecord;

@Repository
public class DatabaseAccessImpl implements DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Override
	public void insertRecord(PasswordRecord record) {
		String query = "INSERT INTO password (id,title,username,password,url,email,notes)"
				+ "VALUES (:id, :title, :username, :password, :url, :email, :notes)";
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("id", record.getId());
		namedParameters.addValue("title", record.getTitle());
		namedParameters.addValue("username", record.getUsername());
		namedParameters.addValue("password", record.getPassword());
		namedParameters.addValue("url", record.getUrl());
		namedParameters.addValue("email", record.getEmail());
		namedParameters.addValue("notes", record.getNotes());
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if(rowsAffected > 0)
				System.out.println("PasswordRecord inserted into database");
	}
	
	@Override
	public List<PasswordRecord> getRecordList(){
		MapSqlParameterSource namedParameter = new MapSqlParameterSource();
		String query = "SELECT * FROM password";
		return jdbc.query(query, namedParameter, new BeanPropertyRowMapper<PasswordRecord>(PasswordRecord.class));
	}
	
	@Override
	public void deleteRecordById(Long Id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM password WHERE id = :id";
		namedParameters.addValue("id", Id);
		if(jdbc.update(query, namedParameters)>0)
			System.out.println("Deleted password " + Id + " from database.");
	}
	
	@Override
	public List<PasswordRecord> getRecordListById(Long Id){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM password WHERE id = :id";
		namedParameters.addValue("id",Id);
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<PasswordRecord>(PasswordRecord.class));
	}
}
