package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.ScanUtil;

public class OrderMenuDAO {
	private static OrderMenuDAO instance = null;

	private OrderMenuDAO() {
	}

	public static OrderMenuDAO getInstance() {
		if (instance == null)
			instance = new OrderMenuDAO();
		return instance;
	}

	JDBCUtil jdbc = JDBCUtil.getInstance();
	LoginDAO loginDao = LoginDAO.getInstance();
//	ReservationDAO reservationDao = ReservationDAO.getInstance();

	public List<Map<String, Object>> viewOrderMenu() {
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectList() {
		String sql = "SELECT * FROM MENU";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> selectOrderList() {
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectlist(sql, 0);
	}

	public Map<String, Object> menuNo(int i) {
		return jdbc.selectOne(
				"SELECT SUBSTR(MENU_NO, 5, 6) AS MENU_NO FROM MENU WHERE SUBSTR(MENU_NO, 5, 6) LIKE '%" + i + "'");
	}

	public Map<String, Object> menuNoCheck(int menuNum, String strNo) {
		return jdbc.selectOne("SELECT SUBSTR(MENU_NO, 6, 6) AS MENU_NO FROM MENU " + "WHERE SUBSTR(MENU_NO, 6, 6) = '"
				+ menuNum + "' AND STR_NO = '" + strNo + "'");
	}

	public List<Map<String, Object>> menu(String strNo) {
		return jdbc.selectList(
				"SELECT SUBSTR(A.MENU_NO,5,6),A.MENU_NAME, A.MENU_DESC, A.MENU_PRICE  FROM MENU A, STORES B "
						+ "WHERE A.STR_NO = B.STR_NO " + "AND  B.STR_NO = '" + strNo + "'");
	}

	public void insertOrder(int menuNum, String strNo) {
		String sql = "INSERT INTO ORDERMENU(OM_NAME, OM_PRICE, OM_QTY, STR_NO) "
				+ "SELECT MENU_NAME, MENU_PRICE, 1 , STR_NO " + "FROM MENU "
				+ "WHERE SUBSTR(MENU_NO, 6,6) = ? AND STR_NO = ?";
		jdbc.updateOrderlist(sql, menuNum, strNo);
	}

	public void updateResno(String resNo, String strNo) {
		String sql = "UPDATE ORDERMENU SET " + "RES_NO = ? " + "WHERE STR_NO = ? AND RES_NO IS NULL ";
		jdbc.updateOrdermenuResno(sql, resNo, strNo);
	}

}
