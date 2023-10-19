package util;

public enum View {
	HOME("홈"),
    LOGIN("로그인"),
    SIGNUP("회원가입"),
    RESIGNUP("회원 수정"),
    USER("사용자"),
    USER_MYPAGE("마이페이지"),
    
    // 매장
    STORE("매장"),
    STORE_INFO_DETAIL("매장정보 상세"),
    STORE_INFO_INSERT("매장정보 등록"),
    STORE_INFO_UPDATE("매장정보 수정"),
    
    STORE_MENU_LIST("메뉴정보 리스트"),
    STORE_MENU_DETAIL("메뉴정보 상세"),
    STORE_MENU_INSERT("메뉴정보 등록"),
    STORE_MENU_UPDATE("메뉴정보 수정"),
    STORE_MENU_DELETE("메뉴정보 삭제"),
	
    STORE_TABLE_LIST("테이블 리스트"),
    STORE_TABLE_INSERT("테이블 등록"),
    STORE_TABLE_UPDATE("테이블 수정"),
    STORE_TABLE_DELETE("테이블 삭제"),
    
    // 고객
    CUSTOMER("고객"),
	RESERVATION("예약 현황");
    
	// 예약 현황
   
	View(String title){}
	
}
