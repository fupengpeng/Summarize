package com.fpp.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Title: BaseServlet
 * @Description: 抽象类，不需要在web.xml中进行配置
 * @Company: 山东九点连线信息技术有限公司
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017年11月27日 上午11:35:15
 */
public abstract class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// 1、获得执行的方法名
		String methodName = request.getParameter("method");
		// 默认方法
		if (methodName == null) {
			methodName = "execute";
		}

		System.out.println("BaseServlet : 本次所执行方法 :  " + methodName);

		try {
			// 2、通过反射获得当前运行类中指定方法,形式参数
			Method executeMethod = this.getClass().getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			// 3、反射执行方法
			String result = (String) executeMethod.invoke(this, request,
					response);
			// 4、将json数据返回
			response.getWriter().write(result);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("未知方法  : " + methodName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("服务器异常", e);
		}
	}

}
