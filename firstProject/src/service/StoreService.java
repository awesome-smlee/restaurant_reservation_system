package service;

import util.FormatUtil;
import util.PrintUtil;
import util.ScanUtil;
import util.View;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dao.StoreDAO;

public class StoreService {

	private static final View View = null;
	private static StoreService instance = null;
	private StoreService() {}
	public static StoreService getInstance() {
		if (instance == null) instance = new StoreService();
		return instance;
	}

	// dao
	StoreDAO storeDao = StoreDAO.getInstance();

	// 정규표현식
	String tellNoPattern = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";
	String tellNumber = "000-000-0000";
	Pattern pattern = Pattern.compile(tellNoPattern);
	Matcher matcher = pattern.matcher(tellNumber);

	String foodType = "";
	
	// 매장 조회
	public View getDetailStore() {
		
		PrintUtil.printTitle("등록한 매장 조회");
		String name = ScanUtil.nextLine("매장 이름 >> ");
		List<Map<String, Object>> result = storeDao.getStoreInfo(name);
		
		if(result != null) {
			for(int i=0; i<result.size(); i++) {
				Map<String, Object> map = result.get(i);
				System.out.println();
				System.out.print("1.매장명 : ");
				System.out.println(map.get("STR_NAME"));
				System.out.print("2.매장 타입 : ");
				System.out.println(map.get("STR_TYPE"));
				System.out.print("3.매장 전화번호 : ");
				System.out.println(map.get("STR_TELNO"));
				System.out.print("4.매장 주소 : ");
				System.out.println(map.get("STR_ADDRESS"));
				System.out.print("5.매장 운영시간 : ");
				System.out.println(FormatUtil.formatTime((String) map.get("STR_OPEN")) 
						+ " ~ " + FormatUtil.formatTime((String) map.get("STR_CLOSE")));
				System.out.print("6.브레이크 타임 : ");
				System.out.println(FormatUtil.formatTime((String) map.get("STR_BRKSTRT"))
						+ " ~ " + FormatUtil.formatTime((String) map.get("STR_BRKCLS")));
				System.out.print("7.사업자명 : ");
				System.out.println(map.get("STR_CEO"));
				System.out.print("8.사업자 등록번호 : ");
				System.out.println(map.get("STR_BN"));
			}
		} else if(result == null) {
			System.out.println("매장 데이터가 존재하지 않습니다.");
		}
	
		return View.STORE_INFO_DETAIL;
	}

	// 매장 등록
	public View createStore() {
		PrintUtil.printTitle("매장 등록");

		System.out.println("1. 매장 타입");
		
		int type = ScanUtil.nextInt("[1] 한식 [2] 중식 [3] 일식 [4] 양식 [5] 분식 [6] 기타  >> ");
		foodType = getStoreType(1);
		
		System.out.println();
		System.out.println("2. 매장명");
		String name = ScanUtil.nextLine("매장명 >> ");

		System.out.println();
		System.out.println("3. 매장 전화번호");
		String tellNo = "";
		while (true) {
			System.out.println("※ 예시)(지역번호)-000-0000 ※");
			tellNo = ScanUtil.nextLine("매장 전화번호 >> ");

			if (!tellNo.matches(tellNoPattern)) {
				System.out.println("전화번호 형식이 올바르지 않습니다. 다시 입력하세요.");
			} else
				break;
		}

		System.out.println();
		System.out.println("4. 매장 주소");
		String address = ScanUtil.nextLine("매장 주소 >> ");

		System.out.println();
		System.out.println("5. 오픈 시간");
		System.out.println("※ 예시) 11:00 -> 1100 ※");
		String open = ScanUtil.nextLine("오픈 시간 >> ");

		System.out.println();
		System.out.println("6. 브레이크 타임 시작 시간");
		System.out.println("※ 예시) 15:00 -> 1500 ※");
		String breakStart = ScanUtil.nextLine("브레이크 타임 시작 시간 >> ");

		System.out.println();
		System.out.println("7. 브레이크 타임 종료 시간");
		System.out.println("※ 예시) 17:00 -> 1700 ※");
		String breakClose = ScanUtil.nextLine("브레이크 타임 종료 시간 >> ");

		System.out.println();
		System.out.println("8. 마감 시간");
		System.out.println("※ 예시) 21:00 -> 2100 ※");
		String close = ScanUtil.nextLine("마감 시간 >> ");

		System.out.println();
		System.out.println("9. 사업자명");
		String ceo = ScanUtil.nextLine("사업자명 >> ");

		System.out.println();
		System.out.println("10. 사업자번호");
		String businessNum = ScanUtil.nextLine("사업자번호 >> ");

		List<Object> param = new ArrayList<Object>();
		param.add(foodType);
		param.add(name);
		param.add(tellNo);
		param.add(address);
		param.add(open);
		param.add(breakStart);
		param.add(breakClose);
		param.add(close);
		param.add(ceo);
		param.add(businessNum);

		int result = storeDao.createStore(param);

		if (result > 0) {
			System.out.println("매장 정보가 정상적으로 등록되었습니다.");
		} else {
			System.out.println("매장 등록이 정상적으로 이루어지지 않았습니다. 처음부터 다시 입력해주세요.");
			return View.STORE_INFO_INSERT;
		}

		return View.STORE;
	}

	// 매장 수정
	public View updateStore() {

		PrintUtil.printTitle("매장 정보 수정");

		String setString = "";
		String yesNo = "";

		String newName = ScanUtil.nextLine("매장 이름 >> ");
		Map<String, Object> getName = storeDao.getStoreNameInfo(newName);
		
		// 수정 가능한 매장인지 판별
		if(getName != null && getName.containsValue(newName)) {
			System.out.println("매장 수정이 가능합니다.");
		} else if(getName == null) {
			System.out.println("매장명을 다시 입력해주세요.");
			return View.STORE_INFO_UPDATE;
		}
		
		// 매장명 수정
		while (true) {
			yesNo = ScanUtil.nextLine("매장명을 수정하시겠습니까? (y/n) >> ");
			if (yesNo.equalsIgnoreCase("y")) {
				while (true) {
					String name = ScanUtil.nextLine("매장명 >> ");
					Map<String, Object> result = storeDao.getStoreNameInfo(name);

					// 매장 중복 체크
					if (result == null) {
						System.out.println("수정 가능한 매장입니다.");
						setString += " STR_NAME = '" + name + "', ";
						break;
					} else if (result != null) {
						System.out.println("중복된 매장명입니다. 다른 매장명을 입력하세요.");
					}
					
				} break;
			} else if (yesNo.equalsIgnoreCase("n")) {
				break;
			} else {
				System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}

		// 매장 타입 수정
		yesNo = ScanUtil.nextLine("매장 타입을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			int type = ScanUtil.nextInt("[1] 한식 [2] 중식 [3] 일식 [4] 양식 [5] 분식 [6] 기타  >> ");
			foodType = getStoreType(type);
			setString += " STR_TYPE = '" + foodType + "', ";
		}

		// 매장 전화번호
		String tellNo;
		yesNo = ScanUtil.nextLine("매장 전화번호를 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			while (true) {
				System.out.println("※ 예시)(지역번호)-000-0000 ※");
				tellNo = ScanUtil.nextLine("매장 전화번호 >> ");

				if (!matcher.matches()) {
					System.out.println("전화번호 형식이 올바르지 않습니다. 다시 입력하세요.");
				} else
					break;
			}
			setString += " STR_TELNO = '" + tellNo + "', ";
		}

		// 매장 주소
		yesNo = ScanUtil.nextLine("매장 주소를 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			String address = ScanUtil.nextLine("매장 주소 >> ");
			setString += " STR_ADDRESS = '" + address + "', ";
		}

		// 오픈 시간
		yesNo = ScanUtil.nextLine("매장 오픈시간을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			System.out.println("※ 예시) 11:00 -> 1100 ※");
			String open = ScanUtil.nextLine("오픈시간 >> ");
			setString += " STR_OPEN = '" + open + "', ";
		}

		// 브레이크 타임 시작 시간
		yesNo = ScanUtil.nextLine("브레이크 타임 시작 시간을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			System.out.println("※ 예시) 15:00 -> 1500 ※");
			String breakStart = ScanUtil.nextLine("브레이크 타임 시작 시간 >> ");
			setString += " STR_BRKSTRT = '" + breakStart + "', ";
		}

		// 브레이크 타임 종료 시간
		yesNo = ScanUtil.nextLine("브레이크 타임 종료 시간을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			System.out.println("※ 예시) 17:00 -> 1700 ※");
			String breakClose = ScanUtil.nextLine("브레이크 타임 종료 시간 >> ");
			setString += " STR_BRKCLS = '" + breakClose + "', ";
		}

		// 마감 시간
		yesNo = ScanUtil.nextLine("마감 시간을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			System.out.println("※ 예시) 21:00 -> 2100 ※");
			String close = ScanUtil.nextLine("마감시간 >> ");
			setString += " STR_CLOSE = '" + close + "', ";
		}

		// 사업자명
		yesNo = ScanUtil.nextLine("사업자명을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			String ceo = ScanUtil.nextLine("사업자명 >> ");
			setString += " STR_CEO = '" + ceo + "', ";
		}

		// 사업자 번호 수정
		yesNo = ScanUtil.nextLine("사업자 번호를 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			String businessNum = ScanUtil.nextLine("사업자번호 >> ");
			setString += " STR_BN = '" + businessNum + "', ";
		}

		setString = setString.substring(0, setString.length() - 2);
	
		List<Object> param = new ArrayList<Object>();
		param.add(newName);

		int row = storeDao.updateStore(setString, param);
		if (row > 0) {
			System.out.println("매장 정보가 정상적으로 수정되었습니다.");
		} else {
			System.out.println("매장 정보 수정에 실패했습니다. 다시 입력해주세요.");
			return View.STORE_INFO_UPDATE;
		}

		return View.STORE_INFO_DETAIL;
	}

	// 사용자로부터 번호 입력받아 매장 타입 선택
	public String getStoreType(int type) {
		switch (type) {
		case 1:
			foodType = "한식";
			break;
		case 2:
			foodType = "중식";
			break;
		case 3:
			foodType = "일식";
			break;
		case 4:
			foodType = "양식";
			break;
		case 5:
			foodType = "분식";
			break;
		case 6:
			foodType = "기타";
			break;
		default:
			System.out.println("유형을 다시 선택해주세요.");
			break;
		}
		return foodType;
	}
	
}