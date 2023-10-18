package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.MenuDAO;
import util.JDBCUtil;
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
	
	MenuDAO menuDao = MenuDAO.getInstance();

	// 메뉴 조회
	
	// 메뉴 등록
	public View createMenu() {
		PrintUtil.printTitle("메뉴 등록");
		String strName = "";
		String menuName = "";
		
		// 매장명과 메뉴명 유효성 및 중복 체크
		while(true) {
			try {
				strName = ScanUtil.nextLine("매장명 >> ");
				Map<String, Object> getStrName = menuDao.getUserInfo(strName);
				
				if(getStrName != null) {
					menuName = ScanUtil.nextLine("메뉴명 >> ");
					break;
				} else {
					System.out.println("메뉴 등록에 실패했습니다. 다시 입력해주세요.");
				}
				
			} catch (NullPointerException e) {
				System.out.println("잘못된 매장명입니다. 다시 입력해주세요.");
			}
		}
		
		String desc = ScanUtil.nextLine("메뉴 소개 >> ");
		int price = ScanUtil.nextInt("가격 >> ");
		
		List<Object> param = new ArrayList<Object>();
		param.add(menuName);
		param.add(desc);
		param.add(price);
		
		int result = menuDao.createStore(param);
		if(result > 0) {
			System.out.println("메뉴가 정상적으로 등록되었습니다.");
		} else {
			System.out.println("메뉴 등록에 실패했습니다. 다시 입력해주세요.");
			return View.STORE_MENU_INSERT;
		}
		
		return View.STORE;
	}
	
	// 메뉴 수정 
	public View updateMenu() {
		PrintUtil.printTitle("메뉴 정보 수정");
		String setString = "";
		String yesNo = "";
		String menuName = "";

		String strName = ScanUtil.nextLine("매장명 >> ");
		Map<String, Object> getName = menuDao.getUserInfo(strName);
		Object menuFromMap = getName.get("MENU_NAME"); // 메뉴명

		// 매장명과 메뉴명 유효성 및 중복 체크
		if(getName != null) {
			try {
				while(true) {
					yesNo = ScanUtil.nextLine("메뉴명을 수정하시겠습니까? (y/n) >> ");
					if(yesNo.equalsIgnoreCase("y")) {
						while(true) {
							menuName = ScanUtil.nextLine("기존 메뉴명 >> ");
							if(menuName.equals(menuFromMap.toString())) {
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
		
		List<Object> param = new ArrayList<Object>();
		param.add(strName);
		param.add(menuName);
		
		int result = menuDao.updateMenu(setString, param);
		
		if(result > 0) {
			System.out.println("메뉴 정보가 정상적으로 수정되었습니다.");
		} else {
			System.out.println("매장 정보 수정에 실패했습니다. 다시 입력해주세요.");
		}
		
		return View.STORE;
	}
	
}
