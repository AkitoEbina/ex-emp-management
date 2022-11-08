package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	public NamedParameterJdbcTemplate template;
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

	public List<Employee> findALL() {
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC";
		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employeeList;		
	}

	public Employee load(Integer id) {
		try {
			String sql = "SELECT * FROM employees WHERE id=:id";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

			Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
			return employee;
		} catch (Exception e) {
			return null;
		}
	}

	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		if (employee.getId() == null) {
			String insertSql = "INSERT INTO employees (name,image,gender,hire_date,mail_address,zip_code,address"
					+ "telephone,salary,characteristics,dependents_count)"
					+ "VALUES(:name,:image,:gender,:hireDate,:mailAddress,:zipCode,:address,:telephone"
					+ ",:salary,:characteristics,:dependentsCount)";
			template.update(insertSql, param);
		}else {
			String updateSql = "UPDATE employeees SET name=:name,image=:image,gender=:gender,hireDate=:hireDate,"
					+ "mailAddress=:mailAddress,zipCode=:zipCode,address=:address,telephone=:telephone,"
					+ "salaly=:salary,characteristics=:characteristics,dependentsCount=:dependentsCount"+"WHERE id=:id";
			template.update(updateSql, param);
		}
	}
}
