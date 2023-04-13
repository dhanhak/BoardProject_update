package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import commons.EncryptionUtils;
import dto.MemberDTO;

public class MemberDAO {
	MemberDTO dto = new MemberDTO();

	private static MemberDAO instance = null;
	public synchronized static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	private MemberDAO() {} // 생성자 private으로 막아서 new 되지 않도록

	private Connection getConnection() throws Exception {
		Context iCtx = new InitialContext();
		DataSource ds = (DataSource)iCtx.lookup("java:/comp/env/jdbc/ora");
		return ds.getConnection();
	}

	public boolean isIdExist(String id) throws Exception{
		String sql = "select * from members where id=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			try(ResultSet rs = pstat.executeQuery();){
				return rs.next();
				//				if(rs.next()) {
				//					return true;
				//				}else {
				//					return false;
				//				}
			}
		}
	}

	public int joinMember(MemberDTO d) throws Exception {
		String sql = "insert into members values(?,?,?,?,?,?,?,?,sysdate)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, d.getId());
			pstat.setString(2, EncryptionUtils.sha512(d.getPw()));
			pstat.setString(3, d.getName());
			pstat.setString(4, d.getPhone());
			pstat.setString(5, d.getEmail());
			pstat.setString(6, d.getZipcode());
			pstat.setString(7, d.getAddress1());
			pstat.setString(8, d.getAddress2());

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}

	}

	public boolean loginCheck(String id, String pw) throws Exception {
		String sql = "select * from members where id=? and pw=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			pstat.setString(2, pw);
			try(ResultSet rs = pstat.executeQuery();){
				return rs.next();
			}
		}
	}

	public int memberDelete(String id) throws Exception {
		String sql = "delete from members where id=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public MemberDTO memberInfo(String id) throws Exception {
		String sql = "select * from members where id=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, id);
			try(ResultSet rs = pstat.executeQuery();){
				MemberDTO dto = null;
				rs.next();
				String myId = rs.getString("id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String zipcode = rs.getString("zipcode");
				String address1 = rs.getString("address1");
				String address2 = rs.getString("address2");
				Timestamp join_date = rs.getTimestamp("join_date");
				dto = new MemberDTO(myId,null,name,phone,email,zipcode,address1,address2,join_date);

				return dto;
			}
		}
	}
	
	public int updateMember(MemberDTO dto) throws Exception{
		String sql = "update members set name=?, phone=?, email=?, zipcode=?, address1=?, address2=? where id=?";
		
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getPhone());
			pstat.setString(3, dto.getEmail());
			pstat.setString(4, dto.getZipcode());
			pstat.setString(5, dto.getAddress1());
			pstat.setString(6, dto.getAddress2());
			pstat.setString(7, dto.getId());
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	
	
}
