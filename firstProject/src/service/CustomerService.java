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

			String resPer = Integer.toString(ScanUtil.nextInt("인원 수 >> "));

			String resTime = Integer.toString(ScanUtil.nextInt("예약 시간 >> "));

			reservationDao.viewTable();
			String tblNo = Integer.toString(ScanUtil.nextInt("테이블 번호 >> "));
			
			orderMenuDao.orderMenu(strNum);
			
			String resReq = ScanUtil.nextLine("요청사항을 입력해주세요. >> ");
				reservationDao.reservation(resPer, resTime, tblNo, resReq);
				orderMenuDao.updateResno(strNum);
			
			return View.RESERVATION;
		}
	}
}
