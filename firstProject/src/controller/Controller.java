package controller;

import java.util.HashMap;

import java.util.Map;

import service.CustomerService;
import service.LoginService;
import service.MenuService;
import service.ReservationService;
import service.StoreService;
import service.TableService;
import service.UserService;
import util.ScanUtil;
import util.View;


public class Controller {
	// sessionStorage
	static public Map<String, Object> sessionStorage = new HashMap<>();
	
	LoginService loginService = LoginService.getInstance(); // 로그인 
	UserService userService = UserService.getInstance();    // 사용자
	StoreService storeService = StoreService.getInstance();  // 매장
	CustomerService customerService = CustomerService.getInstance(); // 고객
	ReservationService reservationService = ReservationService.getInstance();
	MenuService menuService = MenuService.getInstance();    // [매장] 메뉴
	TableService tableService = TableService.getInstance(); // [매장] 테이블식탁

	// 실행
	public static void main(String[] args) {
		new Controller().start();
	}

	public static View view = View.HOME;
	
	private void start() {
		sessionStorage.put("USERS", null);
		
		while (true) {
			switch (view) {
			case HOME:
				view = home();
				break; 
			case LOGIN:
				view = loginService.login();
				break;
			case SIGNUP:
				view = userService.signUp();
				break;
//--------------------------------------------------				
			case STORE:
				view = storeService.store();
				break;
			case STORE_MGMT:
				view = storeService.storeMgmt();
				break;
			case STORE_MGMT_INSERT:
				view = storeService.storeMgmtInsert();
				break;
//--------------------------------------------------

			case CUSTOMER:
				view = customerService.list();
				break;
			case CUSTOMER_RESERVATION:
				view = reservationService.reservationList();
				
				
			}
		}
	}

	private View home() {

		System.out.println("식당 예약 시스템에 오신 걸 환영합니다.");
		System.out.println();
		System.out.println("이용하실 메뉴를 선택해주세요.");
		System.out.println();
		System.out.println("1. 로그인  / 2. 회원가입 ");

		switch (ScanUtil.nextInt("번호 입력 >> ")) {
		case 1:
			return View.LOGIN;
//		case 2:
//			return View.STORE;
		case 2:
			return View.SIGNUP;
		default:
			return View.HOME;
		}

	}
	
}
