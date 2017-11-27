package com.fpp.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



/**
 * 
 * @Title: JDBCUtil
 * @Description: jdbc��װ������
 * @Company: �����ŵ�������Ϣ�������޹�˾
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017��10��12�� ����5:15:52
 *
 */
public class JDBCUtil {
	
	// ��������Ҫ�ı���
	private static Connection ct = null;

	// ���������£�ʹ�õ���PreparedStatement�����Statement��Ŀ����Ϊ�˷�ֹsqlע��
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;

	// �������ݿ�Ĳ���
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	// ��ȡ�����ļ�������
	private static Properties pp = null;
	private static InputStream fis = null;

	// ����������ֻ��Ҫһ��
	static {
		try {
			// ��dbinfo��properties�ļ��ж�ȡ������Ϣ
			pp = new Properties();
			// ʹ��web��Ŀʱ��ʹ���������,���������ȡ��Դʱ��Ĭ�϶�ȡ��Ŀ¼��src
			fis = JDBCUtil.class.getClassLoader().getResourceAsStream(
					"dbinfo.properties");
			pp.load(fis);
			// ��ȡ�����ļ�����Ĳ���
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			password = pp.getProperty("password");
			Class.forName(driver);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fis = null;
		}
	}

	// �õ�����
	public static Connection getConnection() {
		try {
			ct = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ct;
	}
	
	// �ر���Դ�ĺ���
	public static void close(ResultSet rs, Statement ps, Connection ct) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (ct != null) {
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static Connection getCt() {
		return ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static CallableStatement getCs() {
		return cs;
	}
	
	
	
	// TODO mysql��crud����
	
	// ֻ��һ��sql��� ���� update/delete /insert
	// sql ��ʽ��update ���� set �ֶ���=�� where �ֶ�=��
	// parameters
	public static void executeUpdate(String sql) {
		try {
			// ����һ��ps
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			System.out.println("sql=="+sql);
			// ִ��
			ps.executeUpdate();
			//ִ�в������ʱ��Ҫ��ȡ�Զ������idʹ�����·���
			rs = ps.getGeneratedKeys();  //��ȡ�Զ����ɵļ�ֵ
			rs.next();
			System.out.println(rs.getInt(1));  //�õ���ֵ�����
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}

	}
	
	// /**
	// *
	// * Description: ���ݸ�����uid�h���Ñ�----����executeUpdate����ʵ�ֲ���
	// * @param uid
	// * @return
	// */
	// public boolean delUser(String uid){
	// boolean b = true;
	// String sql = "delete from user where uid = '"+uid+"'";
	// try {
	// System.out.println("uid---"+uid);
	// SqlHelper.executeUpdate(sql);
	// } catch (Exception e) {
	// b = false;
	// e.printStackTrace();
	// }
	// return b;
	// }
	
	
	
	// ����ж��sql��� update/delete /insert [��Ҫ����������]
	public static void executeUpdate(String[] sql) {
		try {
			// ����
			// 1.�������
			ct = getConnection();
			// �����sql�������Ƕ����
			ct.setAutoCommit(false);  //����sql��䲻���Զ��ύ
			for (int i = 0; i < sql.length; i++) {
				ps = ct.prepareStatement(sql[i]);
				ps.executeUpdate();
			}
			ct.commit();  //�ֶ��ύ
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// �ع�
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}
	}

	// ͳһ��SELECT
	// ResultSet->Array
	public static ResultSet executeQuery(String sql) {

		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);

			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
//			 close(rs, cs, ct);    //��ʱ���رգ��ȵ�����ʱ��ȡ�����ݺ���ִ�йرշ���
		}
		return rs;

	}
	
	// /**
	// *
	// * Description: ���ݸ�����uid��ѯ���ݿ�����
	// * @param uid
	// * @return
	// */
	// public User getUserByUid (String uid){
	//
	// User user = new User();
	// String sql = "select * from user where uid='"+uid+"'";
	// ResultSet rs = SqlHelper.executeQuery(sql);
	// try {
	// // ��ѯ���ݿ�,��ȡ����uid��Ӧ������
	// while (rs.next()) {
	// user.setId(rs.getInt(1));
	// user.setUid(rs.getString(2));
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getConnection());  //�ر���Դ
	// }
	// return user;
	// }
	
	
	
	
	
	// TODO oracle��crud����
	
	// ֻ��һ����� update/delete /insert
	// sql ��ʽ��update ���� set �ֶ���=�� where �ֶ�=��
	// parameters
	public static void executeUpdateOracle(String sql, String[] parameters) {
		try {
			// ����һ��ps
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			// ������ֵ
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			// ִ��
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());

		} finally {
			close(rs, ps, ct);
		}

	}

	// ����ж��sql��� update/delete /insert [��Ҫ����������]
	public static void executeUpdate2(String[] sql, String[][] parameters) {
		try {
			// ����
			// 1.�������
			ct = getConnection();

			// �����sql�������Ƕ����
			ct.setAutoCommit(false);  //����sql��䲻���Զ��ύ
			for (int i = 0; i < sql.length; i++) {
				if (parameters[i] != null) {
					ps = ct.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++) {
						ps.setString(j + 1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			ct.commit();  //�ֶ��ύ
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// �ع�
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());

		} finally {
			close(rs, ps, ct);
		}
	}
	
	// ͳһ��SELECT----oracle
	// ResultSet->Array
	public static ResultSet executeQueryOracle(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null && parameters.equals("")) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			 close(rs,cs,ct);
		}
		return rs;
	}
	
	
	
	
	
	
	
	
	

}
