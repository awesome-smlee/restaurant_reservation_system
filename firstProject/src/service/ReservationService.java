package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.CustomerDAO;
import dao.OrderMenuDAO;
import dao.ReservationDAO;
import util.JDBCUtil;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class ReservationService {
	private static ReservationService instance = null;
	private ReservationService () {}
	public static ReservationService getInstance() {
		if(instance == null) instance = new ReservationService();
		return instance;
	}
	CustomerService customerService = CustomerService.getInstance();
	ReservationDAO reservationDao = ReservationDAO.getInstance();
	OrderMenuDAO orderMenuDao = OrderMenuDAO.getInstance();
	
//	
	public View res(int strNum) {
		PrintUtil.printTitle("예약하기");
		
		String resPer = Integer.toString(ScanUtil.nextInt("인원 수 >> "));
		
		String resTime = Integer.toString(ScanUtil.nextInt("예약 시간 >> "));
		
		String tblNo = Integer.toString(ScanUtil.nextInt("테이블 번호 >> "));
		
		String orderMenu = Integer.toString(ScanUtil.nextInt("주문할 매뉴를 선택하세요 >> "));
		
		String resReq = Integer.toString(ScanUtil.nextInt("요청 사항 >> "));
		
		List<Object> param1 = new ArrayList<Object>();
		param1.add(resPer);
		param1.add(resTime);
		param1.add(tblNo);
		param1.add(resReq);
		
		
	return View.CUSTOMER;	
	}
	
}
