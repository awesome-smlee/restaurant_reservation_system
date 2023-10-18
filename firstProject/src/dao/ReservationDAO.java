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
	
	
	public List<Map<String, Object>> selectList(){
		String sql = "SELECT * FROM ORDERMENU";
		return jdbc.selectList(sql);
	}
	public Map<String, Object> viewTotal(){
		String sql = "SELECT * FROM TOTAL_RESERVATION";
		return jdbc.selectOne(sql);
	}
	public List<Map<String, Object>> tables(){
		String sql = "SELECT TBL_NO, TBL_SEAT FROM TABLES";
		return jdbc.selectList(sql);
	}
//	public List<Map<String, Object>> viewreservation(String resNo){
//		String sql = "SELECT * FROM RESERVATION WHERE RES_NO = '"+resNo+"'";
//		return jdbc.selectList(sql);
//	}
	
	public void viewTable() {
		List<Map<String, Object>> result = tables();
		for (int i = 0; i < result.size(); i++) { 
			Map<String, Object> res = result.get(i);
			System.out.print(res.get("TBL_NO") + ".("+res.get("TBL_SEAT")+"인석)");
			System.out.println();
		}
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
	
	public void reservation(String resPer, String resTime, String tblNo, String resReq) {
		String sql = "INSERT INTO RESERVATION(RES_NO, RES_PER, RES_TIME, TBL_NO, RES_REQ) VALUES (RES_NO_SEQ.NEXTVAL, ?, ?, ?, ? )";
		jdbc.updateOne(sql, resPer, resTime, tblNo, resReq);
	}
	
	public View reservationList() {
		ScanUtil.nextLine("주문 예약 현황");
//		List<Map<String,Object>> resResult = viewreservation(resNo);
		List<Map<String,Object>> orderResult = orderMenuDao.viewOrderMenu();
		Map<String,Object> viewTotalMap = viewTotal();
		System.out.print("-" + viewTotalMap.get("RES_NO") + viewTotalMap.get("OM_NAME") +
						 viewTotalMap.get("SAL(OM_QTY)") + "개" + "/" + viewTotalMap.get("SAL(OM_PRICE)")+"원"); 	
		System.out.println("\n-요청사항 :" +viewTotalMap.get("RES_REQ"));
		return View.CUSTOMER;
	}
	
	
}
