package dao;

import java.util.List;

import java.util.Map;
import java.util.Random;

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
	
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}
	public Map<String, Object> viewReservation(){
		String sql = "SELECT * FROM RESERVATION";
		return jdbc.selectOne(sql);
	}
	public List<Map<String, Object>> tables(int strNum){
		String sql = "SELECT  TBL_NO , STR_NUM, " + 
				"ROW_NUMBER() OVER (ORDER BY TBL_NO) AS TBL_COUNT, TBL_SEAT " + 
				"FROM TABLES " + 
				"WHERE STR_NUM = '"+strNum+"'";
		return jdbc.selectList(sql);
	}
	
	public void viewTable(int strNum) {
		List<Map<String, Object>> result = tables(strNum);
		for (int i = 0; i < result.size(); i++) { 
			Map<String, Object> res = result.get(i);
			System.out.print(res.get("TBL_COUNT") + ".("+res.get("TBL_SEAT")+"인석)");
			System.out.println();
		}
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
					 "GROUP BY RES_NO, OM_NAME)" +
					 "GROUP BY RES_NO, OM_NAME)" +
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
	
	public void reservation(String resNo, int resPer, String resTime, int tblNo, String resReq, int strNum) {
		String sql = "INSERT INTO RESERVATION(RES_NO, RES_PER, RES_TIME, TBL_NO, RES_REQ, STR_NUM) VALUES (?, ?, ?, ?, ?, ? )";
		jdbc.updateReservList(sql, resNo, resPer, resTime, tblNo, resReq, strNum);
	}
	
	
	public void generateResNo(String resNo, int resPer, String resTime,  int tblNo, String resReq, int strNum) {
		reservation(resNo, resPer, resTime, tblNo, resReq, strNum);
		orderMenuDao.updateResno(resNo, strNum);
		
	}
	
	
}
