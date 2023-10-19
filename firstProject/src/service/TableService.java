package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	// 테이블 등록 (createTable(param)으로 던진 리스트가 쿼리에 1개만 전달됨. 입력하는 모든값을 한꺼번에 다 쿼리로 날리고 싶음)
	public View createTable() {
		PrintUtil.printTitle("테이블 등록");
		String strName = ScanUtil.nextLine("등록할 매장 >> ");
		List<Object> param = getHeadCount(strName);
		
		int result = tableDao.createTable(param);
		if (result > 0) {
			System.out.println("테이블이 정상적으로 등록되었습니다.");
		} else {
			System.out.println("테이블 등록에 실패했습니다. 다시 입력해주세요.");
			return View.STORE_TABLE_INSERT;
		}
		return View.STORE;
	}

	// 인원수 입력받는 메서드
	public List<Object> getHeadCount(String strName) {
		String yesNo = "";
		int count = 1;
		int headCount = 0;

		Map<String, Object> getStrName = tableDao.getStoreInfo(strName);
		String strNum = getStrName.get("STR_NUM").toString(); // STR_NUM
		String strNo = (String) getStrName.get("STR_NO");     // STR_NO

		while (true) {
			if (getStrName != null && getStrName.get("STR_NAME").equals(strName)) {
				yesNo = ScanUtil.nextLine("인원수를 입력하시겠습니까? (y/n) >> ");
				if (yesNo.equalsIgnoreCase("y")) {
					headCount = ScanUtil.nextInt(count + "번째 입력 : 인원수 입력 >> ");
					count++;
				} else if (yesNo.equalsIgnoreCase("n")) {
					break;
				}
			} else {
				System.out.println("데이터를 불러오지 못했습니다. 다시 시도해주세요.");
			}
		}
		
		List<Object> param = new ArrayList<Object>();
		param.add(headCount);
		param.add(strNum);
		param.add(strNo);
		
		return param;
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
