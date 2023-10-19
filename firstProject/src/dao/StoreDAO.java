package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class StoreDAO {

	private static StoreDAO instance = null;
	private StoreDAO() {}
	public static StoreDAO getInstance() {
		if(instance == null) instance = new StoreDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	// 매장 조회
	public Map<String, Object> getStoreByUsersNo(String UsersNo) {
		String sql = "SELECT * FROM STORES WHERE USERS_NO = '"+ UsersNo +"'";
		return jdbc.selectOne(sql);
	}
	
	// 매장 등록
	public int insertStore(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO STORES (STR_NO, STR_TYPE, STR_NAME, STR_TELNO, STR_ADDRESS, STR_OPEN, STR_BRKSTRT, STR_BRKCLS, STR_CLOSE, STR_CEO, STR_BN) ");
		sb.append(" VALUES(STR_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		return jdbc.update(sql, param);
	}
	
	
	// 매장 이름 조회
	public Map<String, Object> getStoreNameInfo(String strName) {
		String sql = "SELECT * FROM STORES WHERE STR_NAME = '"+ strName +"'";
		return jdbc.selectOne(sql);
	}
//	
//	// 매장 조회
//	public List<Map<String, Object>> getStoreInfo(String name) {
//		String sql = "SELECT STR_NAME,STR_TYPE,STR_TELNO,STR_ADDRESS,STR_OPEN,STR_CLOSE,STR_BRKSTRT,STR_BRKCLS,STR_CEO,STR_BN";
//		sql += " FROM STORES WHERE STR_NAME = '" + name +"' ";
//		return jdbc.selectList(sql);
//	}
//	
	
	// 매장 수정
	public int updateStore(String setString, List<Object> param) {
		String sql = " UPDATE STORES SET ";
		sql += setString;
		sql += " WHERE STR_NAME = ? ";
		
		return jdbc.update(sql, param);
	}
}
