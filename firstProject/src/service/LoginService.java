package service;

import java.util.Map;

import controller.Controller;
import dao.LoginDAO;
import util.ScanUtil;
import util.View;

public class LoginService {
	
	private static LoginService instance = null;
	private LoginService() {}
	public static LoginService getInstance() {
		if(instance == null) instance = new LoginService();
		return instance;
	}
	
	public static int loginCount = 0;
	
	// dao
	LoginDAO loginDao = LoginDAO.getInstance();
	
	public View login() {
		View view = null;
		String id = ScanUtil.nextLine("▶ 아이디 입력 >> ");
		String pw = ScanUtil.nextLine("▶ 비밀번호 입력 >> ");
		
		Map<String, Object> user = loginDao.login(id,pw);
		
		if(user != null) {
			Controller.sessionStorage.put("USERS", user);
			
			int usersType = Integer.parseInt(user.get("USERS_TYPE").toString());
			
			// 사용자 타입에 따라 다른 화면으로 이동 (1-점주, 2-고객)
			if(usersType == 1) {
				System.out.println(user.get("USERS_NAME") + " 사장님, 환영합니다. 오늘도 파이팅하세요!");
				view = View.STORE;
			} else {
				System.out.println(user.get("USERS_NAME") + " 손님, 환영합니다. 맛있는 식사 예약하세요!");
				view = View.CUSTOMER;
			}
			
		} else {
			System.out.println("로그인이 정상적으로 이루어지지 않았습니다. 다시 로그인해주세요!");
			view = View.HOME;
		}
		return view;
	}
}
