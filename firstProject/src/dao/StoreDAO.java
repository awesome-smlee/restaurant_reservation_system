package dao;

import java.util.List;

import util.JDBCUtil;

public class StoreDAO {

	private static StoreDAO instance = null;
	private StoreDAO() {}
	public static StoreDAO getInstance() {
		if(instance == null) instance = new StoreDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	// 매장 등록
	public int createStore(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO STORES (STR_NO, STR_TYPE, STR_NAME, STR_TELNO, STR_ADDRESS, STR_OPEN, STR_BRKSTRT, STR_BRKCLS, STR_CLOSE, STR_CEO, STR_BM, STR_NUM, USERS_NO) ");
		sb.append(" VALUES(STR_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, STR_NUM_SEQ.NEXTVAL, NULL)");
		String sql = sb.toString();
		
		return jdbc.update(sql, param);
	}
}
