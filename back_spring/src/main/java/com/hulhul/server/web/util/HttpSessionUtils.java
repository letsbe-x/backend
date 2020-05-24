package com.hulhul.server.web.util;

import javax.servlet.http.HttpSession;

import com.hulhul.server.domain.user.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "user-session-key";

	public static boolean isLoginUser(HttpSession session) {
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionUser == null) {
			return false;
		}
		return true;
	}

	public static User getUserFormSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}

		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}