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

			String tblNo = Integer.toString(ScanUtil.nextInt("테이블 번호 >> "));
		
		reservationDao.reserv(strNum); // 메뉴 리스트 보여주는 메서드
			int orderMenu = ScanUtil.nextInt("주문할 메뉴를 선택하세요 >> ");
			int param = orderMenu;
			if(orderMenu == 0) {
				System.out.println("잘못 입력하였습니다. 다시 선택해주세요.");
				orderMenu = ScanUtil.nextInt("주문할 메뉴를 선택하세요 >> ");
			}else {
				int orderlist = orderMenuDao.orderList(param);
			}

			String resReq = Integer.toString(ScanUtil.nextInt("요청 사항 >> "));

			List<Object> param1 = new ArrayList<Object>();
			param1.add(resPer);
			param1.add(resTime);
			param1.add(tblNo);
			param1.add(resReq);

			return View.CUSTOMER;
		}
	}
}
