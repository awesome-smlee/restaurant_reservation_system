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
	
	LoginService loginService = LoginService.getInstance();  // 로그인 
	UserService userService = UserService.getInstance();     // 사용자
	ReservationService reservationService = ReservationService.getInstance(); // [공통] 예약
	CustomerService customerService = CustomerService.getInstance(); // [고객]
	StoreService storeService = StoreService.getInstance();  // [점주] 매장
	MenuService menuService = MenuService.getInstance();     // [점주] 메뉴
	TableService tableService = TableService.getInstance();  // [점주] 테이블

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
			case RESIGNUP:
				view = userService.resignUp();
				break;
			case USER_MYPAGE:
				view = userService.userMypage();
				break;
			case USER_DETAIL:
				view = userService.userDetail();
				break;	
			//--------------------------------------------------
			// 매장 관리 
			case STORE:
				view = storeService.store();
				break;
			case STORE_MGMT:
				view = storeService.storeMgmt();
				break;
			case STORE_MGMT_DETAIL:
				view = storeService.storeMgmtDetail();
				break;
			case STORE_MGMT_UPDATE:
				view = storeService.storeMgmtUpdate();
				break;
			case STORE_MGMT_INSERT:
				view = storeService.storeMgmtInsert();
				break;
			// 메뉴 관리
			case MENU_MGMT:
				view = menuService.menuMgmt();
				break;
			case MENU_MGMT_LIST:
				view = menuService.menuMgmtList();
				break;	
			case MENU_MGMT_DETAIL:
				view = menuService.menuMgmtDetail();
				break;
			case MENU_MGMT_UPDATE:
				view = menuService.menuMgmtUpdate();
				break;	
			case MENU_MGMT_DELETE:
				view = menuService.menuMgmtDelete();
				break;	
			case MENU_MGMT_INSERT:
				view = menuService.menuMgmtInsert();
				break;	
			// 테이블 관리
			case TABLE_MGMT:
				view = tableService.tableMgmt();
				break;
			case TABLE_MGMT_LIST:
				view = tableService.tableMgmtList();
				break;
			case TABLE_MGMT_DETAIL:
				view = tableService.tableMgmtDetail();
				break;
			case TABLE_MGMT_UPDATE:
				view = tableService.tableMgmtUpdate();
				break;
			case TABLE_MGMT_DELETE:
				view = tableService.tableMgmtDelete();
				break;
			case TABLE_MGMT_INSERT:
				view = tableService.tableMgmtInsert();
				break;
			// 예약 관리
			case RESV_MGMT:
				view = reservationService.resvMgmt();
				break;
			case RESV_MGMT_LIST:
				view = reservationService.resvMgmtList();
				break;
			case RESV_MGMT_DETAIL:
				view = reservationService.resvMgmtDetail();
				break;
			//--------------------------------------------------
			// 고객 화면
			case CUSTOMER:
				view = customerService.list();
				break;
			case CUSTOMER_RESERVATION:
				view = reservationService.reservationList();
				break;
			case CUSTOMER_MYPAGE:
				view = reservationService.reservationList();
			}
		}
	}

	private View home() {

		System.out.println();
		String welcomeMessage =
		        "W   W  EEEEE  L      CCCC  OOO   M   M  EEEEE\n" +
		        "W   W  E      L     C     O   O  MM MM  E\n" +
		        "W W W  EEE    L     C     O   O  M M M  EEE\n" +
		        "W W W  E      L     C     O   O  M   M  E\n" +
		        " W W   EEEEE  LLLLL  CCCC  OOO   M   M  EEEEE\n";

        System.out.println(welcomeMessage);
    	System.out.println("=========[[식당 주문 예약 시스템]]=========");
		System.out.println("식당 주문 예약 시스템에 오신 걸 환영합니다.");
		System.out.println();
		System.out.println("이용하실 메뉴를 선택해주세요.");
		System.out.println();
		System.out.println("1. 로그인\t2. 회원가입 ");

		switch (ScanUtil.nextInt("▶ 번호 입력 >> ")) {
		case 1:
			return View.LOGIN;
		case 2:
			return View.SIGNUP;
		default:
			return View.HOME;
		}

	}
	
}
