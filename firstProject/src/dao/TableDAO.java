package dao;

import java.util.List;
import java.util.Map;

import service.TableService;
import util.JDBCUtil;

/**
 * @author PC-07
 *
 */
public class TableDAO {

	private static TableDAO instance = null;
	private TableDAO() {}
	public static TableDAO getInstance() {
		if (instance == null) instance = new TableDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	// 매장 조회
	public Map<String, Object> getStoreInfo(String userNo) {
		String sql = "SELECT * FROM STORES WHERE USERS_NO='"+ userNo +"' ";
		return jdbc.selectOne(sql);
	}

	// 테이블 조회
	public List<Map<String, Object>> getTableList(String strNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ROW_NUMBER() OVER (ORDER BY TBL_NO) AS TBL_COUNT, TBL_SEAT ");
		sb.append("FROM TABLES WHERE STR_NO = '"+strNo+"' ");
		String sql = sb.toString();
		return jdbc.selectList(sql);
	}

	// 테이블 등록
	public int createTable(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO TABLES (TBL_NO, TBL_SEAT, STR_NO) ");
		sb.append("VALUES(TBL_NO_SEQ.NEXTVAL, ?, ?)");
		String sql = sb.toString();
		return jdbc.update(sql, param);
	}

	
}
