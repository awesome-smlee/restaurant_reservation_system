package util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

	private static JDBCUtil instance = null;
	private JDBCUtil() {} 
	public static JDBCUtil getInstance() {
		if(instance == null) 
			instance = new JDBCUtil();
		return instance;
	}
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "project1st";
	private String pw = "java";
	
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	public List<Map<String, Object>> selectList(String sql, List<Object> param){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				if(result == null) result = new ArrayList<>();
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				result.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}
	public List<Map<String, Object>> selectlist(String sql, int param){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				if(result == null) result = new ArrayList<>();
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				result.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}
	
	public List<Map<String, Object>> selectList(String sql){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				if(result == null) result = new ArrayList<>();
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				result.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}
	public List<Map<String, Object>> selectList(String sql, int param){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				if(result == null) result = new ArrayList<>();
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				result.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}

	public int update(String sql, List<Object> param) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			result = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		return result;
	}
	
	public int update(String sql) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		return result;
	}
	public int update(String sql, int param) {
		int result = 0;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}
	public void updateOrdermenuResno(String sql, String resNo, String strNo) {
		
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			ps.setString(1, resNo);
			ps.setString(2, strNo);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
			}else {
				System.out.println("예약번호를 등록하는데 실패하였습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
	}
	public void updateReservList(String sql,String resNo, String resPer, String resTime, int tblNo, String resReq, String strNo, int usersNo){

		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			ps.setString(1, resNo);
			ps.setString(2, resPer);
			ps.setString(3, resTime);
			ps.setInt(4, tblNo);
			ps.setString(5, resReq);
			ps.setString(6, strNo);
			ps.setInt(7, usersNo);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("예약이 완료되었습니다. 곧 맛있는 음식을 준비해드릴게요!");
			}else {
				System.out.println("예약에 실패했습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
	}
	public void selectResNo(String sql,String resNo){

		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			ps.setString(1, resNo);
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("예약이 완료되었습니다. 곧 맛있는 음식을 준비해드릴게요!");
			}else {
				System.out.println("예약에 실패했습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
	}
	public void updateOrderlist(String sql,int menuNum,String strNo){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, menuNum);
			ps.setString(2, strNo);
			int rowsAffected = ps.executeUpdate();
				if(rowsAffected > 0) {
					System.out.println("주문이 성공적으로 완료되었습니다.");
				}else {
					System.out.println("주문 등록에 실패하였습니다.");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
	}

	public List<Map<String, Object>> updateOne(String sql, int strNum){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, strNum);
			int rowsAffected = ps.executeUpdate();
				if(rowsAffected > 0) {
					System.out.println("주문이 성공적으로 완료되었습니다.");
				}else {
					System.out.println("주문 등록에 실패하였습니다.");
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
	}
	
	public Map<String, Object> selectOne(String sql, List<Object> param){
		Map<String, Object> row = null;
		
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);			
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key,value);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		
		return row;
	}
	
	public Map<String, Object> selectOne(String sql){
		Map<String, Object> row = null;
		
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key,value);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		
		return row;
	}
	
	public List<Map<String, Object>> selectOnes(String sql, List<Object> param){
		List<Map<String, Object>> result = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				if(result == null) result = new ArrayList<>();
				Map<String, Object> row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				result.add(row);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try { rs.close(); } catch(Exception e) {}
			if(ps != null) try { ps.close(); } catch(Exception e) {}
			if(conn != null) try { conn.close(); } catch(Exception e) {}
		}
		return result;
}
	public Map<String, Object> selectOne(String sql, int param){
		Map<String, Object> row = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key,value);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		return row;
	}
	public Map<String, Object> selectOne(String sql, String resNo){
		Map<String, Object> row = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++) {
					String key = rsmd.getColumnLabel(i);
					Object value = rs.getObject(i);
					row.put(key,value);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try {  rs.close();  } catch (Exception e) { }
			if(ps != null) try {  ps.close();  } catch (Exception e) { }
			if(conn != null) try { conn.close(); } catch (Exception e) { }
		}
		return row;
	}
		
}