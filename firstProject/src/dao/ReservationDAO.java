package dao;

import java.sql.Connection;
import java.util.List;

import java.util.Map;
import java.util.Random;

import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class ReservationDAO {
	private static ReservationDAO instance = null;
	private ReservationDAO(){}
	public static ReservationDAO getInstance() {
		if(instance == null) instance = new ReservationDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	OrderMenuDAO orderMenuDao = OrderMenuDAO.getInstance();
	
	// [점주 화면]
	
	// 해당 매장의 예약 목록 조회
	public List<Map<String, Object>> resvMgmtList(Object strNo){
		  String sql = "SELECT b.USERS_NAME, a.RES_TIME, a.RES_PER " +
				  		"FROM RESERVATION a " +
				  		"INNER JOIN USERS b " +
				  		"ON a.USERS_NO = b.USERS_NO " +
				  		"WHERE STR_NO = '"+strNo+"' " +
				  		"ORDER BY RES_TIME";
		return jdbc.selectList(sql);
	}	
	
	// 예약 현황 상세
	public List<Map<String, Object>> resvMgmtDetail(Object usersNo){
		  String sql = "SELECT a.RES_NO, b.USERS_NAME, a.RES_TIME, a.RES_PER, " +
				  		"LISTAGG(c.OM_NAME, ', ') WITHIN GROUP (ORDER BY c.OM_NAME), " +
				  		"SUM(c.OM_PRICE), a.RES_REQ " +
				  		"FROM RESERVATION a " +
				  		"INNER JOIN USERS b ON a.USERS_NO = b.USERS_NO " +
				  		"INNER JOIN ORDERMENU c ON a.RES_NO = c.RES_NO " +
				  		"WHERE b.USERS_NO = '"+usersNo+"' " +
				  		"GROUP BY a.RES_NO, b.USERS_NAME, a.RES_TIME, a.RES_PER, a.RES_REQ";
		return jdbc.selectList(sql);
	}	
	
	// ---------------------------------------------------
	// [고객 화면]
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}
	public Map<String, Object> viewReservation(){
		String sql = "SELECT * FROM RESERVATION";
		return jdbc.selectOne(sql);
	}
	
	public Map<String,Object> tableCheck(String resTime, int tblNo, String strNo) {
		String sql = "SELECT * FROM RESERVATION WHERE RES_TIME = '"+resTime+"' AND TBL_NO = '"+tblNo+"' AND STR_NO = '"+strNo+"'";
		return jdbc.selectOne(sql);
	}
	
	public Map<String, Object> getRezTimeInfo(String resTime, String strNo){
		String sql = "SELECT * FROM RESERVATION WHERE RES_TIME = '"+resTime+"' AND STR_NO = '"+strNo+"'";
		return jdbc.selectOne(sql);
	}
	
	public List<Map<String, Object>> tables(String strNo){
		String sql = "SELECT  TBL_NO , STR_NO," + 
				"ROW_NUMBER() OVER (ORDER BY TBL_NO) AS TBL_COUNT, TBL_SEAT " + 
				"FROM TABLES " + 
				"WHERE STR_NO = '"+strNo+"'";
		return jdbc.selectList(sql);
	}
	
	public Map<String, Object> tableNoCheck(String strNo, int tblNo){
		String sql = "SELECT TBL_COUNT " +
				"FROM (SELECT  TBL_NO , STR_NO, " + 
				"ROW_NUMBER() OVER (ORDER BY TBL_NO) AS TBL_COUNT, TBL_SEAT " + 
				"FROM TABLES " + 
				"WHERE STR_NO = '"+strNo+"') "+
				"WHERE TBL_COUNT = '"+tblNo+"'";
		return jdbc.selectOne(sql);
	}
	
	public Map<String, Object> totalPriceList(String resNo){ // 전체 총 금액
		String sql =  "SELECT TOTAL_PRICE_SUM " + 
					  "FROM(SELECT RES_NO, SUM(TOTAL_QTY) AS TOTAL_QTY_SUM, SUM(TOTAL_PRICE) AS TOTAL_PRICE_SUM " +
					  "FROM (SELECT O.RES_NO, SUM(O.OM_QTY) AS TOTAL_QTY, SUM(O.OM_QTY * O.OM_PRICE) AS TOTAL_PRICE " +
				      "FROM ORDERMENU O " +
				      "JOIN RESERVATION R ON O.RES_NO = R.RES_NO " +
				      "GROUP BY O.RES_NO, O.OM_NAME) " +
				      "GROUP BY RES_NO) " +
				      "WHERE RES_NO = '"+resNo+"'";
		return jdbc.selectOne(sql);
	}
	
	public List<Map<String, Object>> totalMenuStateList(){ //메뉴 별 금액
		String sql = "SELECT RES_NO, OM_NAME, SUM(TOTAL_QTY) AS TOTAL_QTY, SUM(TOTAL_PRICE) AS TOTAL_PRICE " +
			 	  	 "FROM(SELECT O.RES_NO, O.OM_NAME, SUM(O.OM_QTY) AS TOTAL_QTY, SUM(O.OM_QTY*O.OM_PRICE) AS TOTAL_PRICE " +
			 	  	 "FROM ORDERMENU O " +
			 	  	 "JOIN RESERVATION R ON O.RES_NO = R.RES_NO " +
			 	     "GROUP BY O.RES_NO, O.OM_NAME) " +
			 	     "GROUP BY RES_NO, OM_NAME ";
		return jdbc.selectList(sql);
	}
	public List<Map<String, Object>> ResnoOrderList(String resNo){
		String sql = "SELECT OM_NAME, TOTAL_QTY, TOTAL_PRICE " +
				     "FROM(SELECT RES_NO, OM_NAME, SUM(TOTAL_QTY) AS TOTAL_QTY, SUM(TOTAL_PRICE) AS TOTAL_PRICE " +
				     "FROM(SELECT RES_NO, OM_NAME, SUM(OM_QTY) AS TOTAL_QTY, SUM(OM_PRICE) AS TOTAL_PRICE " +
				     "FROM ORDERMENU " +
				     "GROUP BY RES_NO, OM_NAME) " +
				     "GROUP BY RES_NO, OM_NAME) " +
				     "WHERE RES_NO = '"+resNo+"'";
		return jdbc.selectList(sql);
	}
	
	public String makeResNo() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();
		
		for(int i=0; i< 5; i++) {
				int idx = random.nextInt(characters.length());
				randomString.append(characters.charAt(idx));
		}
		return randomString.toString();
	}
	
	public void reservation(String resNo, String resPer, String resTime, int tblNo, String resReq, String strNo) {
		String sql = "INSERT INTO RESERVATION(RES_NO, RES_PER, RES_TIME, TBL_NO, RES_REQ, STR_NO) VALUES (?, ?, ?, ?, ?, ? )";
		jdbc.updateReservList(sql, resNo, resPer, resTime, tblNo, resReq, strNo);
	}
	
	
	
	public void generateResNo(String resNo, String resPer, String resTime,  int tblNo, String resReq, String strNo) {
		reservation(resNo, resPer, resTime, tblNo, resReq, strNo);
		orderMenuDao.updateResno(resNo, strNo);
		
	}
//	public void viewTable(String strNo) {
//		List<Map<String, Object>> result = tables(strNo);
//		for (int i = 0; i < result.size(); i++) { 
//			Map<String, Object> res = result.get(i);
//			System.out.print(res.get("TBL_COUNT") + ".("+res.get("TBL_SEAT")+"인석)");
//			System.out.println();
//		}
	
}
