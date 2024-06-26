package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.UserDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class UserService {

	private static UserService instance = null;

	private UserService() {}

	public static UserService getInstance() {
		if (instance == null) instance = new UserService();
		return instance;
	}

	// dao
	UserDAO userDao = UserDAO.getInstance();

	// 정규표현식
	String idPattern = "[a-z0-9]{5,20}";
	String pwPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
	String tellNoPattern = "^(010-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})$";
	String birthPattern = "^\\d{8}$";

	// 회원가입
	public View signUp() {
		int type = 0; // 1:점주, 2:손님
		String id = "";
		String pw = "";
		String name = "";
		String tellNo = "";
		String birth = "";

		PrintUtil.printTitle("회원가입");

		// 타입 선택
		System.out.println("1. 사용자 유형 ");
		type = ScanUtil.nextInt("[1] 매장 점주\t[2] 손님  >> ");

		// 아이디
		while (true) {
			System.out.println();
			System.out.println("2. 아이디");
			System.out.println("※ 5~20자의 영문 소문자,숫자를 입력하세요. ※");
			id = ScanUtil.nextLine("▶ 아이디 입력 >> ");

			// 아이디 유효성 및 중복 체크
			Map<String, Object> result = userDao.getUserInfo(id);

			if (id.matches(idPattern) && result == null) {
				System.out.println("가입 가능한 아이디입니다.");
				break;
			} else if (result != null) {
				System.out.println("중복 된 아이디입니다. 다른 아이디를 입력하세요.");
			} else {
				System.out.println("아이디 형식이 올바르지 않습니다. 다시 입력하세요.");
			}
		}

		// 비밀번호
		while (true) {
			System.out.println();
			System.out.println("3. 비밀번호");
			System.out.println("※ 5~15자의 영문 소문자,숫자를 입력하세요. ※");
			pw = ScanUtil.nextLine("▶ 비밀번호 입력 >> ");

			if (pw.matches(pwPattern)) {
				System.out.println("사용 가능한 비밀번호입니다. ");
				break;
			} else {
				System.out.println("비밀번호 형식이 올바르지 않습니다. 다시 입력하세요.");
			}
		}

		// 이름
		System.out.println();
		System.out.println("4. 이름 ");
		name = ScanUtil.nextLine("▶ 이름 입력 >> ");

		// 생년월일
		while (true) {
			System.out.println();
			System.out.println("5. 전화번호");
			System.out.println("※ 예시) 010-0000-0000 또는 (지역번호)-000-0000※");
			tellNo = ScanUtil.nextLine("▶ 전화번호 입력 >> ");
			if (!tellNo.matches(tellNoPattern)) {
				System.out.println("전화번호 형식이 올바르지 않습니다. 다시 입력하세요.");
			} else
				break;
		}

		// 생년월일
		while (true) {
			System.out.println();
			System.out.println("6. 생년월일");
			System.out.println("※ 생년월일 8자리를 입력하세요. 예시) 19940308 ※");
			birth = ScanUtil.nextLine("▶ 생년월일 입력>> ");

			if (!birth.matches(birthPattern)) {
				System.out.println("생년월일 형식이 올바르지 않습니다. 다시 입력하세요.");
			} else
				break;
		}

		List<Object> param = new ArrayList<Object>();
		param.add(type);
		param.add(id);
		param.add(pw);
		param.add(name);
		param.add(tellNo);
		param.add(birth);

		int result = userDao.signUp(param);
		if (result > 0) {
			System.out.println();
			System.out.println("회원가입을 축하드립니다♥ " + "\n원활한 서비스 이용을 위해 가입하신 아이디와 패스워드를 다시 입력해주세요!");
		} else {
			System.out.println("회원가입이 정상적으로 이루어지지 않았습니다. 처음부터 다시 입력해주세요.");
		}

		return View.HOME;
	}

	// 마이페이지
	public View userMypage() {
		View view = null;
		PrintUtil.printTitle("마이페이지");
		System.out.println("0. 뒤로가기");
		System.out.println("1. 회원정보조회");

		int num = ScanUtil.nextInt("▶ 번호 입력 >> ");
		switch (num) {
		case 0:
			view = View.HOME;
			break;
		case 1:
			view = View.USER_DETAIL;
			break;
		}
		return view;
	}

	// 회원 정보 조회
	public View userDetail() {
		View view = null;

		Map user = (Map) Controller.sessionStorage.get("USERS");
		List<Map<String, Object>> userList = userDao.userInfoList(user.get("USERS_NO").toString());
		
		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				Map map = userList.get(i);
				System.out.println("1. 이름          : " + map.get("USERS_NAME"));
				System.out.println("2. 아이디       : " + map.get("USERS_ID"));
				System.out.println("3. 비밀번호    : " + map.get("USERS_PW"));
				System.out.println("4. 전화번호    : " + map.get("USERS_HP"));
				
				// 날짜 형식 변환
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = (Date) map.get("USERS_BIRTH");
                String formattedBirthDate = sdf.format(birthDate);
                System.out.println("5. 생년월일    : " + formattedBirthDate);
			}
		}

		// 메뉴 분기처리
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("1. 회원정보수정");
		int num = ScanUtil.nextInt("▶ 번호 입력 >> ");
		switch (num) {
		case 0:
			view = View.USER_MYPAGE;
			break;
		case 1:
			view = View.RESIGNUP;
			break;
		}

		return view;
	}

	// 회원 정보 수정
	public View resignUp() {

		PrintUtil.printTitle("회원 정보 수정");
		String setString = "";

		// 아이디 수정
		String yesNo = ScanUtil.nextLine("아이디를 수정하시겠습니까?(y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			while (true) {
				String id = ScanUtil.nextLine("▶ 아이디 입력 >> ");

				// 아이디 중복 체크
				Map<String, Object> userResult = userDao.getUserInfo(id);
				if (id.matches(idPattern) && userResult == null) {
					System.out.println("수정 가능한 아이디입니다.");
					setString += " USERS_ID = '" + id + "', ";
					break;
				} else if (userResult != null) {
					System.out.println("중복 된 아이디입니다. 다른 아이디를 입력하세요.");
				} else {
					System.out.println("아이디 형식이 올바르지 않습니다. 다시 입력하세요.");
				}
			}
		}

		// 비밀번호 수정
		yesNo = ScanUtil.nextLine("비밀번호를 수정하시겠습니까?(y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			while (true) {
				String pw = ScanUtil.nextLine("▶ 비밀번호 입력>> ");
				if (pw.matches(pwPattern)) {
					setString += " USERS_PW = '" + pw + "', ";
					break;
				} else {
					System.out.println("비밀번호 형식이 올바르지 않습니다. 다시 입력하세요.");
				}
			}
		}

		// 이름 수정
		yesNo = ScanUtil.nextLine("이름을 수정하시겠습니까?(y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			String name = ScanUtil.nextLine("▶ 이름 입력>> ");
			setString += " USERS_NAME = '" + name + "', ";
		}

		// 전화번호 수정
		yesNo = ScanUtil.nextLine("전화번호를 수정하시겠습니까?(y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			while (true) {
				String tellNo = ScanUtil.nextLine("전화번호 >> ");
				if (tellNo.matches(tellNoPattern)) {
					setString += " USERS_HP = '" + tellNo + "', ";
					break;
				} else {
					System.out.println("전화번호 형식이 올바르지 않습니다. 다시 입력하세요.");
				}
			}
		}

		// 생년월일 수정
		yesNo = ScanUtil.nextLine("생년월일을 수정하시겠습니까?(y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			while (true) {
				String birth = ScanUtil.nextLine("▶ 생년월일 입력 >> ");

				if (birth.matches(birthPattern)) {
					setString += " USERS_BIRTH = '" + birth + "', ";
					break;
				} else {
					System.out.println("생년월일 형식이 올바르지 않습니다. 다시 입력하세요.");
				}
			}
		}
		
		setString = setString.substring(0, setString.length() - 2);
		
		Map user = (Map)Controller.sessionStorage.get("USERS");
		
		List<Object> param = new ArrayList<Object>();
		param.add(user.get("USERS_NO"));

		int result = userDao.resignUp(setString, param);
		if (result > 0) {
			System.out.println("회원정보가 정상적으로 수정되었습니다.");
		} else {
			System.out.println("자료 수정에 실패했습니다. 다시 입력해주세요.");
			return View.RESIGNUP;
		}

		return View.STORE;
	}
}
