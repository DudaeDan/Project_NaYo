package com.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.web.domain.User;

@Component
public class NayoLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null) {
			User loginUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
			if (loginUser != null) {
				request.setAttribute("loginMember", loginUser);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && request.getAttribute("loginMember") != null) {
			modelAndView.addObject("loginMember", request.getAttribute("loginMember"));
		}
	}
}
