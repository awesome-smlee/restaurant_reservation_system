package dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.LoginService;
import util.JDBCUtil;
import util.ScanUtil;

public class OrderMenuDAO {
	private static OrderMenuDAO instance = null;
	private OrderMenuDAO() {}
	public static OrderMenuDAO getInstance() {
		if(instance == null) instance = new OrderMenuDAO();
		return instance;
	}


	JDBCUtil jdbc = JDBCUtil.getInstance();
	LoginDAO loginDao = LoginDAO.getInstance();
//	ReservationDAO reservationDao = ReservationDAO.getInstance();
	
	public List<Map<String, Object>> viewOrderMenu(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM MENU";
		return jdbc.selectList(sql);
	}

	public Map<String, Object> menuNo(int i){
		return jdbc.selectOne("SELECT SUBSTR(MENU_NO, 5, 6) AS MENU_NO FROM MENU WHERE SUBSTR(MENU_NO, 5, 6) LIKE '%"+i+"'");
	}
	
	public List<Map<String,Object>> menu(int strNum) {
		return jdbc.selectList("SELECT SUBSTR(A.MENU_NO,5,6),A.MENU_NAME, A.MENU_DESC, A.MENU_PRICE  FROM MENU A, STORES B " 
				     + "WHERE A.STR_NO = B.STR_NO " + "AND  B.STR_NUM = '"+strNum+"'");
	}
	
	public void insertOrder(int StrNum, int param, int strNum){
		String sql = "INSERT INTO ORDERMENU(OM_NAME, OM_PRICE, OM_QTY, STR_NUM) " +
				     "SELECT MENU_NAME, MENU_PRICE, 1 , ?" +
				     "FROM MENU " +
				     "WHERE SUBSTR(MENU_NO, 5,6) = ? AND STR_NUM = ? ";
		jdbc.updateOrderlist(sql, StrNum, param, strNum);
	}
//	public void updateResno(String resNo, int strNum) {
//		String sql = "UPDATE ORDERMENU SET RES_NO = ? WHERE STR_NUM = ?";
//		jdbc.updateOrdermenu(sql, resNo, strNum);
//	}
	
	public void updateResno(String resNo, int strNum) {
		String sql = "UPDATE ORDERMENU SET " +
					 "RES_NO = ? " +
					 "WHERE STR_NUM = ?";
		jdbc.updateOrdermenuResno(sql, resNo, strNum);
	}
	
	public List<Map<String, Object>> selectOrderList(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectlist(sql, 0);
	}
	public void orderMenu(int strNum) {
		menuList(strNum);
		int orderMenu = ScanUtil.nextInt("주문하실 메뉴를 선택하세요 >> ");
		int StrNum = strNum;
		selectMenu(orderMenu, strNum, StrNum);
	}
	public void menuList(int strNum) {
		List<Map<String, Object>> result = menu(strNum);
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			Map<String, Object> menuNumMap = menuNo(i+1);
			System.out.print(menuNumMap.get("MENU_NO")+".");
			System.out.println(res.get("MENU_NAME") + ":" + res.get("MENU_PRICE"));
			System.out.println(" -"+res.get("MENU_DESC"));
		}
	}
	public void selectMenu(int orderMenu, int strNum, int StrNum) {
		while(true) {
			if(orderMenu == 0) {
				System.out.println("잘못 입력하였습니다. 다시 선택해주세요.");
				orderMenu = ScanUtil.nextInt("주문할 메뉴를 선택하세요 >> ");
			}else {
				int param = orderMenu;
				StrNum = strNum;
				insertOrder(StrNum, param, strNum);
			}
			String yesNo = ScanUtil.nextLine("메뉴를 추가하시겠습니까?(y/n) >> ");
			if(yesNo.equalsIgnoreCase("n")) {
				break;
			}else if(!yesNo.equalsIgnoreCase("y")) {
				orderMenu = ScanUtil.nextInt("잘못 입력하였습니다. 다시 선택해주세요. >> ");
			}else {
				orderMenu = ScanUtil.nextInt("주문할 메뉴를 선택하세요 >> ");
			}
		}
	}
	
}
	
	
	



