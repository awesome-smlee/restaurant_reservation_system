package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import dao.MenuDAO;
import dao.StoreDAO;
import util.PrintUtil;
import util.ScanUtil;
import util.View;

public class MenuService {

	private static final View View = null;
	private static MenuService instance = null;
	private MenuService() {}
	public static MenuService getInstance() {
		if (instance == null) instance = new MenuService();
		return instance;
	}
	
	// dao
	MenuDAO menuDao = MenuDAO.getInstance();
	StoreDAO storeDao = StoreDAO.getInstance(); 

	// 메뉴 관리
	public View menuMgmt() {
		View view = null;
		PrintUtil.printTitle("메뉴");
		System.out.println("0. 뒤로가기");
		System.out.println("1. 메뉴 목록");
		System.out.println("2. 메뉴 등록");
		
		int num = ScanUtil.nextInt("입력 >> ");
		switch(num) {
			case 0:
				view = View.HOME;
				break;
			case 1:
				view = View.MENU_MGMT_LIST;
				break;
			case 2:
				view = View.MENU_MGMT_INSERT;
				break;
		}
		return view;
	}
	
	// 메뉴 목록
	public View menuMgmtList() {
	    PrintUtil.printTitle("메뉴 목록");
	    
	    Map user = (Map)Controller.sessionStorage.get("USERS");
	    Map<String, Object> store = storeDao.getStoreByUsersNo(user.get("USERS_NO"));
	    
	    if (store != null) {
	        String strNo = store.get("STR_NO").toString();
	        List<Map<String, Object>> menuList = menuDao.getMenu(strNo);

	        if (menuList != null && !menuList.isEmpty()) {
	            System.out.println("[" + store.get("STR_NAME") + "] 매장의 메뉴 목록");
	            for (int i = 0; i < menuList.size(); i++) {
	                Map<String, Object> menu = menuList.get(i);
	                System.out.println("메뉴 " + (i + 1) + ": " + menu.get("MENU_NAME"));
	            }
	            System.out.println();
	            
	            int num = ScanUtil.nextInt("조회할 메뉴 선택 >> ");
	            
	            if (num >= 1 && num <= menuList.size()) {
	                Controller.sessionStorage.put("SELECTED_MENU", menuList.get(num - 1));
	                return View.MENU_MGMT_DETAIL;
	            } else {
	                System.out.println("올바른 메뉴 번호를 입력하세요.");
	            }
	        } else {
	            System.out.println("메뉴가 존재하지 않습니다.");
	        }
	    } else {
	        System.out.println("매장 정보를 가져오는 데 문제가 발생했습니다.");
	    }
	    
	    return View.STORE;
	}

	// 메뉴 상세 조회
	public View menuMgmtDetail() {
	    PrintUtil.printTitle("메뉴 조회");
	    
	    Map selectedMenu = (Map) Controller.sessionStorage.get("SELECTED_MENU");
	    List<Map<String, Object>> getMenu = menuDao.getMenuList(selectedMenu.get("MENU_NAME").toString());
	    
	    if (getMenu != null) {
	    	for(int i=0; i<getMenu.size(); i++) {
	    		Map<String, Object> menu = getMenu.get(i);
	    		System.out.println("메뉴명: " + menu.get("MENU_NAME"));
	    		System.out.println("메뉴 설명: " + menu.get("MENU_DESC"));
	    		System.out.println("메뉴 가격: " + menu.get("MENU_PRICE") + "원");
	    	}
	    	
	    } else {
	        System.out.println("선택한 메뉴 정보를 가져오는 데 문제가 발생했습니다.");
	    }
	    
	    return View.STORE;
	}

	
	// 메뉴 등록 
	public View menuMgmtInsert() {
		PrintUtil.printTitle("메뉴 등록");
		String strName = "";
		String menu = "";
		
		Map user = (Map)Controller.sessionStorage.get("USERS");
		Map<String, Object> getStr = storeDao.getStoreByUsersNo(user.get("USERS_NO"));
		
		while(true) {
			menu = ScanUtil.nextLine("메뉴명 >> ");
			int check = menuDao.duplCheckMenuName(menu, getStr.get("STR_NO").toString());
			if(check > 0) {
				System.out.println("중복된 메뉴입니다. 다른 메뉴명을 입력해주세요.");
			} else {
				System.out.println("등록 가능한 메뉴입니다.");
				break;
			}
		}
		
		String desc = ScanUtil.nextLine("메뉴 소개 >> ");
		int price = ScanUtil.nextInt("가격 >> ");
		
		List<Object> param = new ArrayList<Object>();
		param.add(menu);
		param.add(desc);
		param.add(price);
		param.add(getStr.get("STR_NO"));
		
		int result = menuDao.createStore(param);
		if(result > 0) {
			System.out.println("메뉴가 정상적으로 등록되었습니다.");
		} else {
			System.out.println("메뉴 등록에 실패했습니다. 다시 입력해주세요.");
			return View.MENU_MGMT_INSERT;
		}
		
		return View.STORE;
	}
	
	// 메뉴 수정 
	public View menuMgmtUpdate() {
		PrintUtil.printTitle("메뉴 정보 수정");
		String setString = "";
		String yesNo = "";
		String menuName = "";

		Map user = (Map)Controller.sessionStorage.get("USERS");
		List<Map<String, Object>> getMenu = menuDao.getMenuListAll(user.get("USERS_NO").toString());
		String getMenuName = (String) getMenu.get(1).get("MENU_NAME");

		if(getMenu != null) {
			try {
				while(true) {
					yesNo = ScanUtil.nextLine("메뉴명을 수정하시겠습니까? (y/n) >> ");
					if(yesNo.equalsIgnoreCase("y")) {
						while(true) {
							menuName = ScanUtil.nextLine("기존 메뉴명 >> ");
							if(menuName.equals(getMenuName)) {
								System.out.println("수정이 가능한 메뉴입니다.");
								String newMenuName = ScanUtil.nextLine("변경할 메뉴명 >> ");
								setString += "MENU_NAME = '"+newMenuName+"', ";
								break;
							} else {
								System.out.println("메뉴명을 잘못 입력하셨습니다. 다시 입력해주세요.");
							}
						}
						break;
					} else if (yesNo.equalsIgnoreCase("n")) {
						break;
					} else {
						System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
					}
				}
			} catch (Exception e) {
				System.out.println("메뉴명을 다시 입력해주세요.");
			}
		}
		
		// 메뉴 소개 수정
		yesNo = ScanUtil.nextLine("메뉴 소개를 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			String desc = ScanUtil.nextLine("메뉴 소개 >> ");
			setString += " MENU_DESC = '"+desc+"', ";
		}
		
		// 가격 수정
		yesNo = ScanUtil.nextLine("메뉴 가격을 수정하시겠습니까? (y/n) >> ");
		if (yesNo.equalsIgnoreCase("y")) {
			int price = ScanUtil.nextInt("메뉴 가격 >> ");
			setString += " MENU_PRICE = '"+price+"' , ";
		} 
		
		setString = setString.substring(0, setString.length() - 2);
		
		
		Map<String, Object> getStr = storeDao.getStoreByUsersNo(user.get("USERS_NO"));
		
		List<Object> param = new ArrayList<Object>();
		param.add(getStr);
		param.add(menuName);
		
		int result = menuDao.updateMenu(setString, param);
		
		if(result > 0) {
			System.out.println("메뉴 정보가 정상적으로 수정되었습니다.");
		} else {
			System.out.println("매장 정보 수정에 실패했습니다. 다시 입력해주세요.");
		}
		
		return View.STORE;
	}
	
	// 메뉴 삭제
	public View menuMgmtDelete() {
		PrintUtil.printTitle("메뉴 삭제");
		
		String menuName = ScanUtil.nextLine("삭제할 메뉴명 >> ");
		int result = menuDao.deleteMenu(menuName);
		
		if(result > 0) {
			System.out.println("메뉴가 정상적으로 삭제되었습니다.");
		} else {
			System.out.println("메뉴 삭제에 실패했습니다.");
		}
		
		return View;
	}
}
