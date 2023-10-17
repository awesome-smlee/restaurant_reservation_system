package dao;

import java.util.List;

import java.util.Map;

import util.JDBCUtil;

public class ReservationDAO {
	private static ReservationDAO instance = null;
	private ReservationDAO(){}
	public static ReservationDAO getInstance() {
		if(instance == null) instance = new ReservationDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	OrderMenuDAO orderMenuDao = OrderMenuDAO.getInstance();
	
	public List<Map<String, Object>> reservation(String resPer, String resTime, String tblNo, String resReq){
		return jdbc.selectList("UPDATE TABLE RESERVATION SET" 
				+" RES_PER = '"+resPer+"'"
           		+" RES_TIME = '"+resTime+"' AND"
           		+" TBL_NO = '"+tblNo+"' AND"
           		+" RES_REQ = '"+resReq+"'");
	}
	public int reservNO(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE RESERVATION SET RES_NO = ");
		sb.append("(SELECT DBMS_RANDOM.STRING('X',20) FROM DUAL)");
		sb.append("WHERE USER_ID = ?");
		
		String sql = sb.toString();
		return jdbc.update(sql, param);
	}
	
	
	public List<Map<String, Object>> reserv(List<Object> param) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO RESERVATION (RES_PER, RES_TIME, TBL_NO, RES_REQ)");
		sb.append("VALUES(?,?,?,?)");
		
		String sql = sb.toString();
		return jdbc.selectOnes(sql, param);
	}
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}
	
	public void reserv(int strNum) {
		List<Map<String, Object>> results = orderMenuDao.menu(strNum);
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> res = results.get(i);
			Map<String, Object> menuNumMap = orderMenuDao.menuNo(i);
			System.out.print(orderMenuDao.menuNo(i+1));
			System.out.println(res.get("MENU_NAME") + ":" + res.get("MENU_PRICE"));
			System.out.println(" -"+res.get("MENU_DESC"));
		}
		
	}
}
