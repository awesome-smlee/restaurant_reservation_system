package dao;

import java.util.List;

import java.util.Map;
import util.PrintUtil;
import util.JDBCUtil;

public class CustomerDAO {
	private static CustomerDAO instance = null;
	private CustomerDAO () {}
	public static CustomerDAO getInstance() {
		if(instance == null) instance = new CustomerDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM STORES ";
		return jdbc.selectList(sql);
	}
}
