package util;

import java.util.Scanner;

public class ScanUtil {
	static Scanner sc = new Scanner(System.in);
	public static String nextLine(String text) {
		System.out.print(text);
		return sc.nextLine();
	}
	public static int nextInt(String text) {
		System.out.print(text);
		while(true) {
			try {
				int result = Integer.parseInt(sc.nextLine());
				return result;
			}catch (NumberFormatException e) {
				System.out.println("잘못 입력하셨습니다.");
				return 0;
			}
		}
	}
}
