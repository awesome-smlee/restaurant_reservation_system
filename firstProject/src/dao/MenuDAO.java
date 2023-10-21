package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MenuDAO {
	private static MenuDAO instance = null;
	private MenuDAO() {}
	public static MenuDAO getInstance() {
		if(instance == null) instance = new MenuDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();

	// 메뉴명 조회
	public List<Map<String, Object>> getMenu(String strNo) {
		String sql = "SELECT MENU_NAME FROM MENU WHERE STR_NO = '"+strNo+"' ";
		return jdbc.selectList(sql);
	}
	
	
	// 메뉴 상세 조회
	public List<Map<String, Object>> getMenuList(String menuName) {
		String sql = "SELECT MENU_NAME, MENU_DESC, MENU_PRICE " +
                "FROM MENU " +
                "WHERE MENU_NAME = '"+menuName+"' ";
		return jdbc.selectList(sql);
	}
	
	// 메뉴 전체 조회
	public List<Map<String, Object>> getMenuListAll(String strNo) {
		String sql = "SELECT * FROM MENU WHERE = '"+strNo+"' ";
		return jdbc.selectList(sql);
	}
	
	// 메뉴 등록
	public int createStore(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO MENU (MENU_NO, MENU_NAME, MENU_DESC, MENU_PRICE, STR_NO) ");
		sb.append(" VALUES(MENU_NO_SEQ.NEXTVAL, ?, ?, ?, ?) ");
		String sql = sb.toString();
		
		return jdbc.update(sql, param);
	}
	
	// 메뉴명 중복체크
	public int duplCheckMenuName(String menu, String strNo) {
	    String sql = "SELECT COUNT(*) FROM MENU WHERE MENU_NAME = ? AND STR_NO = ?";
	    List<Object> params = new ArrayList<>();
	    params.add(menu);
	    params.add(strNo);

	    List<Map<String, Object>> result = JDBCUtil.getInstance().selectList(sql, params);

	    if (result != null && result.size() > 0) {
	        Map<String, Object> row = result.get(0);
	        Object countObj = row.get("COUNT(*)");
	        if (countObj instanceof Number) {
	            return ((Number) countObj).intValue();
	        }
	    }
	    return 0; 
	}

	// 메뉴 수정
	public int updateMenu(String setString, List<Object> param) {
		String sql = " UPDATE MENU SET ";
		sql += setString;
		sql += " WHERE MENU.STR_NO IN (SELECT STR_NO FROM STORES WHERE STR_NAME = ? AND MENU_NAME = ?) ";
		
		return jdbc.update(sql, param);
	}
	
	// 메뉴 삭제
	public int deleteMenu(String menuName) {
		String sql = "DELETE FROM MENU WHERE MENU_NAME = '"+menuName+"'" ;
		return jdbc.update(sql);
	}
	
}

