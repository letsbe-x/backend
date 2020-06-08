package com.hulhul.server.web.util;

import java.util.Arrays;
import java.util.List;

public class AnonymousNickNameUtils {
	public static String getNick(Long u_id, Long p_id) {

		// 25
		List<String> nick = Arrays.asList("기운찬", "기분좋은", "신바람나는", "상쾌한", "애교있는", "영리한", "자유로운", "따뜻한", "창의적인", "친절한",
				"다정한", "즐거운", "책임감있는", "당당한", "배부른", "밝은", "꼼꼼한", "멋있는", "기발한", "심심한", "잘생긴", "이쁜", "긍정적인", "정직한",
				"용감한");
		// 30
		List<String> name = Arrays.asList("사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리", "침팬치", "고릴라", "참새", "고슴도치", "강아지",
				"고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마", "원숭이", "물소", "얼룩말", "치타", "악어", "기린", "수달", "염소", "다람쥐",
				"판다", "개구리");

		Long add = u_id + p_id;
		Long mod = u_id * p_id;
		Long hash = 5381L;

		//임시로! 
		for (int k = 0; k < (add) % 10; k++) {
			hash += mod;
			hash <<= k;
		}

		Integer select = (int) (hash % 10000);
		int a = (select / 100) % nick.size();
		int b = (select % 100) % name.size();
		String text = nick.get(a) + " " + name.get(b);
		return text;
	}
}
