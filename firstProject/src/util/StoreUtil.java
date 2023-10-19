package util;

import java.util.Arrays;
import java.util.List;

public class StoreUtil {
	
	public static List<String> storeTypeList = Arrays.asList("한식", "중식", "일식", "양식", "분식", "기타");

	// 매장 분류 목록 
	public static void printStoreList() {
		for(int i=0; i<storeTypeList.size(); i++) {
			System.out.println(i+1 + ". " + storeTypeList.get(i));
		}
	}
	// 매장 분류
	public static String getStoreType(int i) {
		String type = null;
		try {
			type = storeTypeList.get(i);
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("유형을 다시 선택해주세요.");
		}
		return type;
	}
}
