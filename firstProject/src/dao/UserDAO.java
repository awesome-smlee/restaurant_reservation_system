package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDAO {

	private static UserDAO instance = null;
	private UserDAO() {}
	public static UserDAO getInstance() {
		if(instance == null) instance = new UserDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	// 아이디 조회
	public Map<String, Object> getUserInfo(String id) {
		String sql = "SELECT * FROM USERS WHERE USERS_ID = '"+ id +"'";
		return jdbc.selectOne(sql);
	}
	
	// 아이디, 비밀번호 조회
	public Map<String, Object> login(String id, String pw) {
		return jdbc.selectOne("SELECT * FROM USERS "
				+ " WHERE USERS_ID='" + id + "' AND USERS_PW='" + pw + "' ");
	}
	
	// 회원가입
	public int signUp(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO USERS (USERS_NO, USERS_TYPE, USERS_ID, USERS_PW, USERS_NAME, USERS_HP, USERS_BIRTH) ");
		sb.append(" VALUES(USERS_SEQ.nextval, ?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		return jdbc.update(sql, param);
	}
	
	// 사용자 정보 수정
	public int resignUp(String setString, List<Object> param) {
		String sql = " UPDATE USERS SET ";
		sql += setString;
		sql += " WHERE USERS_ID = ? ";
		
		return jdbc.update(sql, param);
	}
	
}
