package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.TableDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class TableService {

	private static final View View = null;
	private static TableService instance = null;

	private TableService() {
	}

	public static TableService getInstance() {
		if (instance == null)
			instance = new TableService();
		return instance;
	}

	// dao
	TableDAO tableDao = TableDAO.getInstance();

	// 테이블 등록
	public View createTable() {
		PrintUtil.printTitle("테이블 등록");

		Map user = (Map) Controller.sessionStorage.get("loginInfo");
		Map<String, Object> store = tableDao.getStoreInfo(user.get("USERS_NO").toString());
		
		int i=0;
		while(true) {
			
			int strSeat = ScanUtil.nextInt(i + "번 테이블 : 인원수 입력 >> ");
			
			List<Object> param = new ArrayList<Object>();
			param.add(strSeat);
			param.add(store.get("STR_NUM"));
			param.add(store.get("STR_NO"));
			
			int cnt = tableDao.createTable(param);
			if(cnt > 0) {
				System.out.println("테이블이 정상적으로 등록되었습니다.");
				String yn = ScanUtil.nextLine("추가로 등록 하시겠습니까? (y/n) >> ");
				if("n".equalsIgnoreCase(yn)) {
					break;
				} 
			} 
			else {
				System.out.println("테이블 등록에 실패했습니다. 다시 입력해주세요.");
				break;
			}
		}
		
		return View.HOME;
	}

	
	// 테이블 조회
	public View getTableInfo() {
		PrintUtil.printTitle("테이블 조회");
		
		String strName = ScanUtil.nextLine("조회할 매장 >> ");
		Map<String, Object> getStrNum = tableDao.getStoreInfo(strName);
		
		Object strNum = getStrNum.get("STR_NUM"); // STR_NUM
		
		List<Map<String, Object>> result = tableDao.getTableInfo(strNum);
		
		if (result != null) {
		    if (result.size() > 0) {
		        for (int i = 0; i < result.size(); i++) {
		            Map<String, Object> map = result.get(i);
		            System.out.print("테이블 " + map.get("TBL_COUNT") + " - ");
		            System.out.println("인원수:" + map.get("TBL_SEAT"));
		        }
		    } else {
		        System.out.println("테이블 데이터가 존재하지 않습니다.");
		    }
		} else {
		    System.out.println("데이터를 가져오는 데 문제가 발생했습니다.");
		}
		
		return View;
	}
}
