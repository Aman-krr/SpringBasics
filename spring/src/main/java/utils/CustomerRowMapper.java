package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer= new Customer();
		customer.setCust_id(rs.getInt(1));
		customer.setCust_name(rs.getString(2));
		customer.setCust_add(rs.getString(3));
		customer.setCity(rs.getString(4));
		customer.setState(rs.getString(5));
		customer.setZipCode(rs.getString(6));
		return customer;
	}

}
