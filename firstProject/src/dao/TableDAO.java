package dao;

import java.util.List;
import java.util.Map;

import service.TableService;
import util.JDBCUtil;
public class TableDAO {

	private static TableDAO instance = null;
	private TableDAO() {}
	public static TableDAO getInstance() {
		if (instance == null) instance = new TableDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();

	// 테이블 조회
	public List<Map<String, Object>> getTableList(String strNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TBL_NO, TBL_SEAT ");
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

	// 테이블 수정
	public int updateTable(String setString, List<Object> param) {
		String sql = " UPDATE TABLES SET ";
		sql += setString;
		sql += " WHERE TBL_NO = ?";
		
		return jdbc.update(sql, param);
	}
	
	// 테이블 삭제
	public int deleteTable(String tblNo) {
		String sql = "DELETE FROM TABLES WHERE TBL_NO = '"+tblNo+"'" ;
		return jdbc.update(sql);
	}
	
}
