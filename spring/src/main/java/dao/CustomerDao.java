package dao;

import java.util.List;

import dto.Customer;

public interface CustomerDao {
int create (Customer e);
int update (Customer e);
int delete (Customer e);
Customer getInfo(String id);
List<Customer> getInfoAll();
public List<Customer> getInfoUsingListQuery();
public int[] batchUpdate(List<Customer> custLis);
}
