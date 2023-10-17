package dao;
import java.util.List;
import java.util.Map;

import service.LoginService;
import util.JDBCUtil;

public class OrderMenuDAO {
	private static OrderMenuDAO instance = null;
	private OrderMenuDAO() {}
	public static OrderMenuDAO getInstance() {
		if(instance == null) instance = new OrderMenuDAO();
		return instance;
	}


	JDBCUtil jdbc = JDBCUtil.getInstance();
	LoginDAO loginDao = LoginDAO.getInstance();
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM MENU";
		return jdbc.selectList(sql);
	}
//	
//	public  Object menuNO(int i){
//		 String sql = "SELECT SUBSTR(MENU_NO, 5, 6) AS MENU_NO FROM MENU WHERE SUBSTR(MENU_NO, 5, 6) LIKE '%\"+i+\"' ";
//		    Map<String, Object> result = jdbc.selectOne(sql, i);
//		if(result != null) {
//			return result.get("MENU_NO");
//		}else {
//			return null;
//		}	
	public Map<String, Object> menuNo(int i){
		return jdbc.selectOne("SELECT SUBSTR(MENU_NO, 5, 6) AS MENU_NO FROM MENU WHERE SUBSTR(MENU_NO, 5, 6) LIKE '%"+i+"'");
	}
	
	public List<Map<String,Object>> menu(int strNum) {
		return jdbc.selectList("SELECT SUBSTR(A.MENU_NO,5,6),A.MENU_NAME, A.MENU_DESC, A.MENU_PRICE  FROM MENU A, STORES B " 
				     + "WHERE A.STR_NO = B.STR_NO " + "AND  B.STR_NUM = '"+strNum+"'");
	}
	
	public int orderList(int param){
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ORDERMENU(OM_NAME, OM_PRICE) ");
		sb.append("SELECT MENU_NAME, MENU_PRICE ");
		sb.append("FROM MENU ");
		sb.append("WHERE SUBSTR(MENU_NO,4,6) = ?");
		String sql = sb.toString();
		return jdbc.update(sql, param);
//		return jdbc.selectList("INSERT INTO ORDERMENU(OM_NAME, OM_PRICE) "
//								+ "SELECT MENU_NAME, MENU_PRICE "
//								+ "FROM MENU "
//								+ "WHERE SUBSTR(MENU_NO,4,6) = '?");
	}
	
	
	

}
