package com.fpp.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fpp.base.BaseServlet;

/**
 * 
 * @Title: UserServlet
 * @Description: 用户管理servlet
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017年11月27日 下午3:54:45
 */
public class UserServlet extends BaseServlet {

	/**
	 * 
	 * @Description: 登录 http://localhost:8080/Summarize/UserServlet?method=login
	 *               &account=zhangsan&password=123456
	 * @Title: login
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *             String
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取到客户端传递过来的用户账号和密码
		String name = request.getParameter("account");
		String password = request.getParameter("password");
		// 2.判断用户是否登录成功
		if ("zhangsan".equals(name) && "123456".equals(password)) {
			System.out.println("登录成功");
			return "denglu chenggong";
		} else {
			System.out.println("登录失败");
			return "denglu shibai";
		}

	}
	
	/**
	 * 
	 * @Description: 注册   http://localhost:8080/Summarize/UserServlet?method=register&account=zhangsan&phonenumber=17712345678
	 * @Title: register 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * String
	 */
	public String register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取到传递过来的数据
		String name = request.getParameter("account");
		String phonenumber = request.getParameter("phonenumber");
		//2.保存至数据，返回注册成功
		return "zhuce chenggong";
	}

}
