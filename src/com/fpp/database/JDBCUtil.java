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
 * @Description: jdbc封装工具类
 * @Company: 济宁九点连线信息技术有限公司
 * @ProjectName: Summarize
 * @author fupengpeng
 * @date 2017年10月12日 下午5:15:52
 *
 */
public class JDBCUtil {
	
	// 定义所需要的变量
	private static Connection ct = null;

	// 大多数情况下，使用的是PreparedStatement来替代Statement，目的是为了防止sql注入
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	private static CallableStatement cs = null;

	// 连接数据库的参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String password = "";
	// 读取配置文件的属性
	private static Properties pp = null;
	private static InputStream fis = null;

	// 加载驱动，只需要一次
	static {
		try {
			// 从dbinfo。properties文件中读取配置信息
			pp = new Properties();
			// 使用web项目时，使用类加载器,类加载器读取资源时，默认读取主目录是src
			fis = JDBCUtil.class.getClassLoader().getResourceAsStream(
					"dbinfo.properties");
			pp.load(fis);
			// 读取配置文件里面的参数
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

	// 得到连接
	public static Connection getConnection() {
		try {
			ct = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ct;
	}
	
	// 关闭资源的函数
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
	
	
	
	// TODO mysql的crud处理
	
	// 只有一个sql语句 用于 update/delete /insert
	// sql 格式：update 表名 set 字段名=？ where 字段=？
	// parameters
	public static void executeUpdate(String sql) {
		try {
			// 创建一个ps
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			System.out.println("sql=="+sql);
			// 执行
			ps.executeUpdate();
			//执行插入语句时，要获取自动插入的id使用如下方法
			rs = ps.getGeneratedKeys();  //获取自动生成的键值
			rs.next();
			System.out.println(rs.getInt(1));  //得到键值并输出
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}

	}
	
	// /**
	// *
	// * Description: 根据给定的uid刪除用戶----调用executeUpdate方法实现操作
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
	
	
	
	// 如果有多个sql语句 update/delete /insert [需要考虑下事务]
	public static void executeUpdate(String[] sql) {
		try {
			// 核心
			// 1.获得连接
			ct = getConnection();
			// 传入的sql（可能是多个）
			ct.setAutoCommit(false);  //设置sql语句不是自动提交
			for (int i = 0; i < sql.length; i++) {
				ps = ct.prepareStatement(sql[i]);
				ps.executeUpdate();
			}
			ct.commit();  //手动提交
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// 回滚
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}
	}

	// 统一的SELECT
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
//			 close(rs, cs, ct);    //暂时不关闭，等到调用时获取到数据后再执行关闭方法
		}
		return rs;

	}
	
	// /**
	// *
	// * Description: 根据给定的uid查询数据库数据
	// * @param uid
	// * @return
	// */
	// public User getUserByUid (String uid){
	//
	// User user = new User();
	// String sql = "select * from user where uid='"+uid+"'";
	// ResultSet rs = SqlHelper.executeQuery(sql);
	// try {
	// // 查询数据库,获取上述uid对应的数据
	// while (rs.next()) {
	// user.setId(rs.getInt(1));
	// user.setUid(rs.getString(2));
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getConnection());  //关闭资源
	// }
	// return user;
	// }
	
	
	
	
	
	// TODO oracle的crud处理
	
	// 只有一个语句 update/delete /insert
	// sql 格式：update 表名 set 字段名=？ where 字段=？
	// parameters
	public static void executeUpdateOracle(String sql, String[] parameters) {
		try {
			// 创建一个ps
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			// 给？赋值
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			// 执行
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());

		} finally {
			close(rs, ps, ct);
		}

	}

	// 如果有多个sql语句 update/delete /insert [需要考虑下事物]
	public static void executeUpdate2(String[] sql, String[][] parameters) {
		try {
			// 核心
			// 1.获得连接
			ct = getConnection();

			// 传入的sql（可能是多个）
			ct.setAutoCommit(false);  //设置sql语句不是自动提交
			for (int i = 0; i < sql.length; i++) {
				if (parameters[i] != null) {
					ps = ct.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++) {
						ps.setString(j + 1, parameters[i][j]);
					}
					ps.executeUpdate();
				}
			}
			ct.commit();  //手动提交
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// 回滚
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());

		} finally {
			close(rs, ps, ct);
		}
	}
	
	// 统一的SELECT----oracle
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
