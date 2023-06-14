package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import dto.Customer;
import utils.CustomerRowMapper;

public class CustomerDaoImpl implements CustomerDao {
	@Autowired
	@Qualifier("jdbcTemplate")
    JdbcTemplate jdbctemplate;

	public int create(Customer e) {
		String query="insert into customers values(?,?,?,?,?,?)";
		int result = jdbctemplate.update(query,e.getCust_id(),e.getCust_name(),e.getCust_add(),e.getCity(),e.getState(),e.getZipCode());
		return result;
	}

	public int update(Customer e) {
		String query ="update customers set ADDRESS=? ,ZIP_CODE=? where customer_id=?";
		int result =jdbctemplate.update(query,e.getCust_add(),e.getZipCode(),e.getCust_id());
		return result;
	}

	public int delete(Customer e) {
		String query = "delete from customers where customer_id=?";
		int result = jdbctemplate.update(query, e.getCust_id());
		return result;
	}

	public Customer getInfo(String id) {
		String query = "select * from customers where customer_id=?";
		Customer customer = jdbctemplate.queryForObject(query,new CustomerRowMapper(), id);
		return customer;
	}

	public List<Customer> getInfoAll() {
		String query="select * from customers";
		List<Customer> customers= jdbctemplate.query(query,new CustomerRowMapper());
		return customers;
	}

	public List<Customer> getInfoUsingListQuery() {
		List<Customer> custList = new ArrayList<Customer>();
		String query="select * from customers";
		List<Map<String, Object>> customersRows= jdbctemplate.queryForList(query);
		if(customersRows!=null) {
			for(Map row :customersRows) {
				Customer customer = new Customer();
				customer.setCust_id(Integer.parseInt(row.get("customer_id").toString()));
				customer.setCust_name(row.get("customer_name").toString());
				customer.setCity(row.get("CITY").toString());
	            customer.setCust_add(row.get("address").toString());
	            customer.setState(row.get("state").toString());
	            customer.setZipCode(row.get("zip_code").toString());
	            custList.add(customer);
			}
		}
		return custList;
	}
	
	public int [] batchUpdate(List<Customer> custList) {
		String query="insert into customers values(?,?,?,?,?,?)";
		int[] updateList = jdbctemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, custList.get(i).getCust_id());
				ps.setString(2, custList.get(i).getCust_name());
				ps.setString(3, custList.get(i).getCust_add());
			    ps.setString(4, custList.get(i).getCity());
				ps.setString(5, custList.get(i).getZipCode());
				ps.setString(6, custList.get(i).getZipCode());
			}

			@Override
			public int getBatchSize() {
				return custList.size();
			}
			
		});
		return updateList;
	}
	
	public List<Customer> getCursorFromProc(){
		List<Customer> custList= new ArrayList<Customer> ();
		
		return custList;
	}
}
