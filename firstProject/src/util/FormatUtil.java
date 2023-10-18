package util;

public class FormatUtil {

	// 시간포맷을 00:00 형태로 반환
	public static String formatTime(String time) {
	    if (time != null && time.length() == 4) {
	        String formattedTime = time.substring(0, 2) + ":" + time.substring(2, 4);
	        return formattedTime;
	    }
	    return time; // 원래 형식 그대로 반환 (오류 처리)
	}
}
