package service;

import util.PrintUtil;
import util.ScanUtil;
import util.View;

import java.util.ArrayList;
import java.util.List;

import dao.StoreDAO;

public class StoreService {
	
	private static StoreService instance = null;
	private StoreService() {}
	public static StoreService getInstance() {
		if (instance == null)
			instance = new StoreService();
		return instance;
	}
	
	// dao
	StoreDAO storeDao = StoreDAO.getInstance();
	
	// 매장 정보 등록
	public View createStore() {
		
		PrintUtil.printTitle("매장 등록");
		
		System.out.println("1. 매장 타입");
		int type = ScanUtil.nextInt("[1] 한식 [2] 중식 [3] 일식 [4] 양식 [5] 분식 [6] 기타  >> ");
		
		System.out.println("2. 매장명");
		String name = ScanUtil.nextLine("매장명 >> ");
		
		System.out.println("3. 매장 전화번호");
		System.out.println("※ 예시)(지역번호)-000-0000 ※");
		String tellNo = ScanUtil.nextLine("매장 전화번호 >> ");
		
		System.out.println("4. 매장 주소");
		String address = ScanUtil.nextLine("매장 주소 >> ");
		
		System.out.println("5. 오픈시간");
		// 사용자가 콘솔창에서 00:00 으로 입력할 수 있게하는 방법은??
		String open = ScanUtil.nextLine("오픈시간 >> ");
		
		System.out.println("6. 브레이크 타임 시작시간");
		// 사용자가 콘솔창에서 00:00 으로 입력할 수 있게하는 방법은??
		String breakStart = ScanUtil.nextLine("브레이크 타임 시작시간 >> ");
		
		System.out.println("7. 브레이크 타입 종료 시간");
		// 사용자가 콘솔창에서 00:00 으로 입력할 수 있게하는 방법은??
		String breakClose = ScanUtil.nextLine("브레이크 타입 종료 시간 >> ");
		
		System.out.println("8. 마감시간");
		// 사용자가 콘솔창에서 00:00 으로 입력할 수 있게하는 방법은??
		String close = ScanUtil.nextLine("마감시간 >> ");
		
		System.out.println("9. 사업자명");
		String ceo = ScanUtil.nextLine("사업자명 >> ");
		
		System.out.println("10. 사업자번호");
		String businessNum = ScanUtil.nextLine("사업자번호 >> ");
		
		List<Object> param = new ArrayList<Object>();
		param.add(type);
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
		
		if(result > 0) {
			System.out.println("정상적으로 등록되었습니다.");
		} else {
			System.out.println("매장 등록이 정상적으로 이루어지지 않았습니다. 처음부터 다시 입력해주세요.");
		}
		
		return View.STORE;
	}
	
	
}
