package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.StoreDAO;
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
	StoreDAO storeDao = StoreDAO.getInstance();

	// 테이블 관리
	public View tableMgmt() {
		View view = null;
		PrintUtil.printTitle("메뉴");
		System.out.println("0. 뒤로가기");
		System.out.println("1. 테이블 목록");
		System.out.println("2. 테이블 등록");
		
		int num = ScanUtil.nextInt("▶ 입력 >> ");
		switch(num) {
			case 0:
				view = View.HOME;
				break;
			case 1:
				view = View.TABLE_MGMT_LIST;
				break;
			case 2:
				view = View.TABLE_MGMT_INSERT;
				break;
		}
		return view;
	}
	
	
	// 테이블 등록
	public View tableMgmtInsert() {
		PrintUtil.printTitle("테이블 등록");

		Map user = (Map) Controller.sessionStorage.get("USERS");
		Map<String, Object> store = storeDao.getStoreByUsersNo(user.get("USERS_NO").toString());
		
		int i=0;
		while(true) {
			
			int strSeat = ScanUtil.nextInt("▶  "+i + "번 테이블 : 인원수 입력 >> ");
			
			List<Object> param = new ArrayList<Object>();
			param.add(strSeat);
			param.add(store.get("STR_NO"));
			
			int cnt = tableDao.createTable(param);
			if(cnt > 0) {
				System.out.println("테이블이 정상적으로 등록되었습니다.");
				String yn = ScanUtil.nextLine("추가로 등록 하시겠습니까?(y/n) >> ");
				if("n".equalsIgnoreCase(yn)) {
					break;
				} 
			} 
			else {
				System.out.println("테이블 등록에 실패했습니다. 다시 입력해주세요.");
				break;
			}
		}
		return View.STORE;
	}

	// 테이블 목록
	Map selectedTable = null;
	public View tableMgmtList() {
		View view = null;
		PrintUtil.printTitle("테이블 목록");
		
		Map user = (Map) Controller.sessionStorage.get("USERS");
		Map<String, Object> strNo = storeDao.getStoreByUsersNo(user.get("USERS_NO"));
		List<Map<String, Object>> tableList = tableDao.getTableList(strNo.get("STR_NO").toString());
		
		if (tableList != null) {
		    if (tableList.size() > 0) {
		        for (int i = 0; i < tableList.size(); i++) {
		            Map<String, Object> map = tableList.get(i);
		            System.out.println(i+1 + ".인원수 : " + map.get("TBL_SEAT"));
		        }
		    } else {
		        System.out.println("테이블 데이터가 존재하지 않습니다.");
		    }
		} else {
		    System.out.println("데이터를 가져오는 데 문제가 발생했습니다.");
		}
		
		int num = ScanUtil.nextInt("조회할 테이블 번호 선택 >> ");
		selectedTable = tableList.get(num-1);
		
		return View.TABLE_MGMT_DETAIL;
	}

	// 테이블 상세 조회
	public View tableMgmtDetail() {
		View view = null;
		PrintUtil.printTitle("테이블 상세");
		
		System.out.println("인원수 : " + selectedTable.get("TBL_SEAT"));
		
		 // 메뉴 분기처리
		System.out.println();
		System.out.println("0. 뒤로가기");
		System.out.println("1. 테이블 수정");
		System.out.println("2. 테이블 삭제");
		int num = ScanUtil.nextInt("▶ 입력 >> ");
		switch(num) {
			case 0:
				view = View.TABLE_MGMT;
				break;
			case 1:
				view = View.TABLE_MGMT_UPDATE;
				break;
			case 2:
				view = View.TABLE_MGMT_DELETE;
				break;
		}
		return view;
	}
	
	// 테이블 수정
	public View tableMgmtUpdate() {
		PrintUtil.printTitle("테이블 수정");
		String setString = "";
		if(selectedTable != null) {
			int strSeat = ScanUtil.nextInt("▶ 인원수 입력 >> ");
			setString = " TBL_SEAT = '"+strSeat+"' ";
		}
		
		List<Object> param = new ArrayList<Object>();
		param.add(selectedTable.get("TBL_NO"));
		
		int result = tableDao.updateTable(setString, param);
		System.out.println(result);
		if(result > 0) {
			System.out.println("테이블이 정상적으로 수정되었습니다.");
		} else {
			System.out.println("테이블 수정에 실패했습니다.");
		}
		
		return View.TABLE_MGMT;
	}
	
	// 테이블 삭제
	public View tableMgmtDelete() {
		PrintUtil.printTitle("테이블 삭제");
		
		if(selectedTable != null) {
			int result = tableDao.deleteTable(selectedTable.get("TBL_NO").toString());
			if(result > 0) {
				System.out.println("테이블이 정상적으로 삭제되었습니다.");
			} else {
				System.out.println("테이블 삭제에 실패했습니다.");
			}
		}
		
		return View.TABLE_MGMT;
	}
}
