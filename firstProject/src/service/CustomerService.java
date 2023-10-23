package service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.CustomerDAO;
import dao.OrderMenuDAO;
import dao.ReservationDAO;
import dao.UserDAO;
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
	UserDAO userDao = UserDAO.getInstance();

	public View list() {
		PrintUtil.printTitle("매장리스트 & 마이페이지");

		// 매장 선택
		System.out.println("0. 마이페이지");
		List<Map<String, Object>> storeList = customerDao.selectList();
		for (int i = 0; i < storeList.size(); i++) {
			Map<String, Object> res = storeList.get(i);
			System.out.print(i + 1 + ".");
			System.out.print("(" + res.get("STR_TYPE") + ")");
			System.out.print(res.get("STR_NAME"));
			System.out.println();
		}
		System.out.println("-----------------------------");
		int num = ScanUtil.nextInt("▶ 예약할 매장을 선택해주세요  >> ");
		if (num == 0) {
			return View.CUSTOMER_MYPAGE;
		}

		// 예약 시작
		PrintUtil.printTitle("예약하기");
		Map<String, Object> store = storeList.get(num - 1); // 매장 리스트
		String strNo = (String) store.get("STR_NO");
		System.out.println("※ 숫자만 입력해주세요 ※");
		String resPer = ScanUtil.nextLine("▶ 인원 수 입력 >> ");

		String resNo = reservationDao.makeResNo();

		while (resPer == null) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			resPer = ScanUtil.nextLine("▶ 인원 수 입력 >> ");
		}
		// 시간 예약
		System.out.println();
		System.out.println("※ 예시) 12시 예약 -> 1200 ※");
		String resTime = ScanUtil.nextLine("▶ 예약 시간 입력 >> ");
		
		//테이블 번호 
		System.out.println();
		System.out.println("※ 인원 수에 맞는 테이블을 예약해주세요 ※");
		List<Map<String, Object>> result = reservationDao.tables(strNo);
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> tableListResult = result.get(i);
			System.out.print(tableListResult.get("TBL_COUNT") + ".(" + tableListResult.get("TBL_SEAT") + "인석)");
			System.out.println();
		}
		
		int tblNo = 0;
		// 테이블 중복 체크
		System.out.println();
		while (true) {
			tblNo = ScanUtil.nextInt("▶ 테이블 번호 입력 >> ");
			Map<String, Object> tableNoResult = reservationDao.tableNoCheck(strNo, tblNo); // 테이블 번호
			Map<String, Object> tableResult = reservationDao.tableCheck(resTime, tblNo, strNo); // 테이블 예약
			if (tableNoResult != null && tableResult == null) {
				System.out.println("예약 가능한 테이블입니다.");
				break;
			} else if (tableResult != null) {
				System.out.println("이미 예약된 테이블 입니다. 다른 테이블을 입력해주세요.");
			} else if (tableNoResult == null) {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
		// 메뉴 리스트 보여주기
		System.out.println();
		List<Map<String, Object>> menuList = orderMenuDao.menu(strNo); // 해당 매장 번호와 일치하는 메뉴 리스트화
		for (int j = 0; j < menuList.size(); j++) {
			Map<String, Object> res = menuList.get(j);
			Map<String, Object> menuNumMap = orderMenuDao.menuNo(j + 1);
			System.out.print(menuNumMap.get("MENU_NO") + ".");
			System.out.println(res.get("MENU_NAME") + ":" + res.get("MENU_PRICE"));
			System.out.println(" -" + res.get("MENU_DESC"));
		}
		System.out.println("------------------------------");
		while (true) {
			int menuNum = ScanUtil.nextInt("▶ 주문하실 메뉴를 선택하세요 >> ");
			Map<String, Object> menuNoResult = orderMenuDao.menuNoCheck(menuNum, strNo);

			if (menuNoResult == null) {
				System.out.println("잘못 입력하였습니다. 다시 선택해주세요.");
				continue;
			}
			orderMenuDao.insertOrder(menuNum, strNo); // 주문한 메뉴 등록

			System.out.println();
			String yesNo = ScanUtil.nextLine("메뉴를 추가하시겠습니까?(y/n) >> ");
			if (yesNo.equalsIgnoreCase("n")) {
				break;
			} else if (!yesNo.equalsIgnoreCase("y")) {
				System.out.println("잘못 입력하였습니다. 다시 선택해주세요.");
			}
		}

		System.out.println();
		String resReq = ScanUtil.nextLine("▶ 요청사항을 입력해주세요 >> ");

		
		// USERS_NO 가져오기
		Map user = (Map)Controller.sessionStorage.get("USERS");
		BigDecimal usersNoBigDeciaml = (BigDecimal) user.get("USERS_NO");
		int usersNo = usersNoBigDeciaml.intValue();
		
		// 예약 테이블 입력
		reservationDao.generateResNo(resNo, resPer, resTime, tblNo, resReq, strNo, usersNo);
		orderMenuDao.updateResno(resNo, strNo);
		
		return View.CUSTOMER_RESERVATION;
	}
}
