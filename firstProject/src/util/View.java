package util;

public enum View {
	HOME("홈"),
    LOGIN("로그인"),
    SIGNUP("회원가입"),
    RESIGNUP("회원 수정"),
    USER("사용자"),
    USER_MYPAGE("마이페이지"),
    USER_DETAIL("회원정보조회"),
    
    // 매장
    STORE("매장"),
 
    STORE_MGMT("매장 관리"),
    STORE_MGMT_DETAIL("매장 상세"),
    STORE_MGMT_INSERT("매장 등록"),
    STORE_MGMT_UPDATE("매장 수정"),
    
    MENU_MGMT("메뉴 관리"),
    MENU_MGMT_LIST("메뉴 목록"),
    MENU_MGMT_INSERT("메뉴 등록"),
    MENU_MGMT_DETAIL("메뉴 상세"),
    MENU_MGMT_UPDATE("메뉴 수정"),
    MENU_MGMT_DELETE("메뉴 삭제"),
	
    TABLE_MGMT("테이블 관리"),
    TABLE_MGMT_LIST("테이블 목록"),
    TABLE_MGMT_INSERT("테이블 등록"),
    TABLE_MGMT_DETAIL("테이블 상세"),
    TABLE_MGMT_UPDATE("테이블 수정"),
    TABLE_MGMT_DELETE("테이블 삭제"),
    
    RESV_MGMT("예약 현황"),
    RESV_MGMT_LIST("예약 목록"),
    RESV_MGMT_DETAIL("예약 상세"),
    
    // 고객
    CUSTOMER("고객"),
    CUSTOMER_RESERVATION("예약 현황");
    
	// 예약 현황
   
	View(String title){}
	
}
