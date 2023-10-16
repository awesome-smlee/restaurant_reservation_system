package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class LoginDAO {
	private static LoginDAO instance = null;
	private LoginDAO() {}
	public static LoginDAO getInstance() {
		if(instance == null) instance = new LoginDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public Map<String, Object> login(String id, String pw){
		String sql = "SELECT * FROM USERS WHERE USERS_ID = ? AND USERS_PW = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(pw);
		
		return jdbc.selectOne(sql, param);
	}
}
