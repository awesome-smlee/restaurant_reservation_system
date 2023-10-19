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

	private ReservationService() {
	}

	public static ReservationService getInstance() {
		if (instance == null)
			instance = new ReservationService();
		return instance;
	}

	CustomerService customerService = CustomerService.getInstance();
	ReservationDAO reservationDao = ReservationDAO.getInstance();
	OrderMenuDAO orderMenuDao = OrderMenuDAO.getInstance();

	public View reservationList() {
		PrintUtil.printTitle("주문 예약 현황");
		List<Map<String, Object>> result = reservationDao.totalMenuStateList();
		Map<String, Object> res = reservationDao.viewReservation();
		System.out.println("- 예약번호 : " + res.get("RES_NO"));
		String resNo = (String) res.get("RES_NO");
		System.out.println("- 주문 내역  ");
		List<Map<String, Object>> result2 = reservationDao.ResnoOrderList(resNo);
		for (int i = 0; i < result2.size(); i++) {
			Map<String, Object> rs = result2.get(i);
			System.out.println(
					"   " + rs.get("OM_NAME") + " " + rs.get("TOTAL_QTY") + "개" + " / " + rs.get("TOTAL_PRICE") + "원");
		}
		System.out.println("- 요청사항 : " + reservationDao.viewReservation().get("RES_REQ"));
		System.out.println("--------------------------------");
		System.out.println("- 총 금액 : " + reservationDao.totalPriceList(resNo).get("TOTAL_PRICE_SUM")+"원");
		return View.CUSTOMER;
	}

}
