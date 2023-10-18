package dao;

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
	
	// 매장 조회
	public Map<String, Object> getUserInfo(String strName) {
		String sql = "SELECT * FROM STR_MENU_VIEW WHERE STR_NAME = '"+ strName+"' ";
		return jdbc.selectOne(sql);
	}
	
	// 메뉴 등록
	public int createStore(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO MENU (MENU_NO, MENU_NAME, MENU_DESC, MENU_PRICE, STR_NO, STR_NUM) ");
		sb.append(" VALUES('SZ0005', ?, ?, ?, 'SB0001', 5)");
		String sql = sb.toString();
		
		return jdbc.update(sql, param);
	}
	
	// 메뉴 수정
	public int updateMenu(String setString, List<Object> param) {
		String sql = " UPDATE MENU SET ";
		sql += setString;
		sql += " WHERE MENU.STR_NO IN (SELECT STR_NO FROM STORES WHERE STR_NAME = ? AND MENU_NAME = ?) ";
		
		return jdbc.update(sql, param);
		
//		UPDATE MENU
//		SET MENU_NAME = '뿅뿅마라떡반', MENU_PRICE = 99999
//		WHERE MENU.STR_NO IN (SELECT STR_NO FROM STORES WHERE STR_NAME = '떡반집' AND MENU_NAME = '마라떡반');
	}
}
