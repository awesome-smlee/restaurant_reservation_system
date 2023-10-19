package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.CustomerDAO;
import dao.OrderMenuDAO;
import dao.ReservationDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class CustomerService {
	private static CustomerService instance = null;

	private CustomerService() {
	}

	public static CustomerService getInstance() {
		if (instance == null)
			instance = new CustomerService();
		return instance;
	}

	CustomerDAO customerDao = CustomerDAO.getInstance();
	ReservationDAO reservationDao = ReservationDAO.getInstance();
	OrderMenuDAO orderMenuDao = OrderMenuDAO.getInstance();

	public View list() {
		PrintUtil.printTitle("매장리스트 & 마이페이지");

		System.out.println("0. 마이페이지");
		List<Map<String, Object>> result = customerDao.selectList();
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> res = result.get(i);
			System.out.print(res.get("STR_NUM") + ".");
			System.out.print("(" + res.get("STR_TYPE") + ")");
			System.out.print(res.get("STR_NAME"));
			System.out.println();
		}
		System.out.println("-----------------------------");
		int strNum = ScanUtil.nextInt("예약할 매장을 선택해주세요  >> ");
		if (strNum == 0) {
			return View.USER_MYPAGE;
		} else {
			PrintUtil.printTitle("예약하기");

			int resPer = ScanUtil.nextInt("인원 수 >> ");

			String resTime = Integer.toString(ScanUtil.nextInt("예약 시간 >> "));

			reservationDao.viewTable(); // 테이블 번호 보여주는 메서드
			int tblNo = ScanUtil.nextInt("테이블 번호 >> ");

			orderMenuDao.orderMenu(strNum); // 주문 메서드

			String resReq = ScanUtil.nextLine("요청사항을 입력해주세요. >> ");

			// 예약 번호 생성
			reservationDao.makeResNo();
			String resNo = reservationDao.makeResNo();
			reservationDao.generateResNo(resNo, resPer, resTime, tblNo, resReq, strNum);

			return View.RESERVATION;
		}
	}
}
