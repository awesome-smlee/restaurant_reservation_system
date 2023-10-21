package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.OrderMenuDAO;
import dao.ReservationDAO;
import dao.StoreDAO;
import util.FormatUtil;
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
	StoreDAO storeDao = StoreDAO.getInstance();
	
	// [점주 화면]
	// 예약 현황 조회
	public View resvMgmt() {
		View view = null;
		
		PrintUtil.printTitle("메뉴");
		System.out.println("0. 뒤로가기");
		System.out.println("1. 예약 현황 조회");
		
		int num = ScanUtil.nextInt("입력 >> ");
		switch(num) {
			case 0:
				view = View.HOME;
				break;
			case 1:
				view = View.RESV_MGMT_LIST;
				break;
		}
		
		return view.STORE;
		
	}
	
	// 예약 현황 목록
	public View resvMgmtList() {
		View view = null;
		
		PrintUtil.printTitle("예약 현황 목록");
		
		Map user = (Map)Controller.sessionStorage.get("USERS");
		Map<String, Object> getStr = storeDao.getStoreByUsersNo(user.get("USERS_NO"));
		List<Map<String, Object>> getResvList = reservationDao.resvMgmtList(getStr.get("STR_NO").toString());
		
		if(getResvList != null) {
			System.out.println("[" + getResvList.size() + "]"+ "개의 예약이 있습니다.");
			for(int i=0; i<getResvList.size(); i++) {
				Map map = getResvList.get(i);
				System.out.print(i + 1 + ". ");
				System.out.print("예약번호 : " + map.get("RES_NO") + " | ");
				System.out.print("예약자명 : " + map.get("USERS_NAME") + " | ");
				System.out.print("예약시간 : " + FormatUtil.formatTime(map.get("RES_TIME").toString()) + " | ");
				System.out.print("예약인원 : " + map.get("RES_PER"));
				System.out.println();
			}
		} else {
			System.out.println("예약된 내역이 없습니다.");
		}
		
		int num = ScanUtil.nextInt("조회할 번호 입력 >> ");
		Map selectedReservation = getResvList.get(num - 1);
		Controller.sessionStorage.put("SELECTED_RESERVATION", selectedReservation);

		return view.RESV_MGMT_DETAIL;
	}
	
	// 예약 현황 상세
	public View resvMgmtDetail() {
		View view = null;
		
		PrintUtil.printTitle("예약 현황 상세");
		
		Map selectedResv = (Map)Controller.sessionStorage.get("SELECTED_RESERVATION");
		List<Map<String, Object>> getDetail = reservationDao.resvMgmtDetail(selectedResv.get("RES_NO").toString());
	    if (getDetail != null) {
	    	for(int i=0; i<getDetail.size(); i++) {
	    		Map<String, Object> resv = getDetail.get(i);
	    		System.out.println("1.예약번호 : " + resv.get("RES_NO"));
	    		System.out.println("2.예약자명 : " + resv.get("USERS_NAME"));
	    		System.out.println("3.예약시간 : " + FormatUtil.formatTime(resv.get("RES_TIME").toString()));
	    		System.out.println("4.예약인원 : " + resv.get("RES_PER"));
	    		System.out.println("5.주문메뉴 : " + resv.get("OM_NAMES"));
	    		System.out.println("6.총액 : " + resv.get("TOTAL_OM_PRICE") + "원");
	    		System.out.println("7.요청사항 : " + resv.get("RES_REQ"));
	    	}
	    } else {
	    	System.out.println("예약이 존재하지 않습니다.");
	    }
	    
		return view.RESV_MGMT;
	}
	
	
	// ---------------------------------------------------

	// [고객 화면]
	// 예약 현황 조회
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


