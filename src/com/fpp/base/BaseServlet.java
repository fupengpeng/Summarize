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
 * @Description: �����࣬����Ҫ��web.xml�н�������
 * @Company: ɽ���ŵ�������Ϣ�������޹�˾
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017��11��27�� ����11:35:15
 */
public abstract class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		// 1�����ִ�еķ�����
		String methodName = request.getParameter("method");
		// Ĭ�Ϸ���
		if (methodName == null) {
			methodName = "execute";
		}

		System.out.println("BaseServlet : ������ִ�з��� :  " + methodName);

		try {
			// 2��ͨ�������õ�ǰ��������ָ������,��ʽ����
			Method executeMethod = this.getClass().getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			// 3������ִ�з���
			String result = (String) executeMethod.invoke(this, request,
					response);
			// 4����json���ݷ���
			response.getWriter().write(result);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("δ֪����  : " + methodName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�������쳣", e);
		}
	}

}
