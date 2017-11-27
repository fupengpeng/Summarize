package com.fpp.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fpp.base.BaseServlet;

/**
 * 
 * @Title: UserServlet
 * @Description: �û�����servlet
 * @Company: ɽ���ŵ�������Ϣ�������޹�˾
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017��11��27�� ����3:54:45
 */
public class UserServlet extends BaseServlet {

	/**
	 * 
	 * @Description: ��¼ http://localhost:8080/Summarize/UserServlet?method=login
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
		// 1.��ȡ���ͻ��˴��ݹ������û��˺ź�����
		String name = request.getParameter("account");
		String password = request.getParameter("password");
		// 2.�ж��û��Ƿ��¼�ɹ�
		if ("zhangsan".equals(name) && "123456".equals(password)) {
			System.out.println("��¼�ɹ�");
			return "denglu chenggong";
		} else {
			System.out.println("��¼ʧ��");
			return "denglu shibai";
		}

	}
	
	/**
	 * 
	 * @Description: ע��   http://localhost:8080/Summarize/UserServlet?method=register&account=zhangsan&phonenumber=17712345678
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
		//1.��ȡ�����ݹ���������
		String name = request.getParameter("account");
		String phonenumber = request.getParameter("phonenumber");
		//2.���������ݣ�����ע��ɹ�
		return "zhuce chenggong";
	}

}
