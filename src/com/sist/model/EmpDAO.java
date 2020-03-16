package com.sist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Data Access Object : 데이터 접근 객체 - DB에 접속하는 객체
public class EmpDAO {

	Connection con = null; // DB 연동 객체
	PreparedStatement pstmt = null; // DB에 SQL문을 전송하는 객체
	ResultSet rs = null;

	public EmpDAO() { // 기본 생성자
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "genie";
		String password = "1234";

		try {
			// 1. 드라이버 로딩
			Class.forName(driver);

			// 2. DB와 연동
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// EMP 테이블의 전체 레코드를 조회하는 메서드
	public List<EmpDTO> select() {
		List<EmpDTO> list = new ArrayList<EmpDTO>();

		try {
			String sql = "select * from emp order by empno";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 실제로 DB에서 sql문을 실행하는 메서드

			while (rs.next()) {
				EmpDTO dto = new EmpDTO();
				dto.setEmpno(rs.getInt("empno"));
				dto.setEname(rs.getString("ename"));
				dto.setJob(rs.getString("job"));
				dto.setMgr(rs.getInt("mgr"));
				dto.setHiredate(rs.getString("hiredate"));
				dto.setSal(rs.getInt("sal"));
				dto.setComm(rs.getInt("comm"));
				dto.setDeptno(rs.getInt("deptno"));
				list.add(dto);
			}

			// open 객체 닫기
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	} // 셀렉트 메서드의 끝

	// DEPT 테이블의 전체 목록을 조회하는 메서드

	public ArrayList<DeptDTO> dept() {
		ArrayList<DeptDTO> list = new ArrayList<DeptDTO>();

		try {
			String sql = "select * from dept order by deptno";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DeptDTO dto = new DeptDTO();
				dto.setDeptno(rs.getInt("deptno"));
				dto.setDname(rs.getString("dname"));
				dto.setLoc(rs.getString("loc"));
				list.add(dto);

			}
			// open된 객체 닫기
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	} // dept 메서드의 end
	
	// EMP 테이블에 레코드를 추가하는 메서드
	public int insert(EmpDTO dto) {// 데이터 추가 변경 등은 반환형이 int
		int result = 0;
		
		try {
			String sql = "insert into emp values(?,?,?,?,sysdate,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getEmpno());
			pstmt.setString(2, dto.getEname());
			pstmt.setString(3, dto.getJob());
			pstmt.setInt(4, dto.getMgr());
			pstmt.setInt(5, dto.getSal());
			pstmt.setInt(6, dto.getComm());
			pstmt.setInt(7, dto.getDeptno());
			
			result = pstmt.executeUpdate();
			
			// open된 객체 닫기
			pstmt.close(); con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	} // insert 메서드의 end

}
