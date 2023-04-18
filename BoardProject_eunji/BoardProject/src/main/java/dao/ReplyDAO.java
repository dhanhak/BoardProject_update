package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.ReplyDTO;

public class ReplyDAO {
	
	private static ReplyDAO instance = null;
	public synchronized static ReplyDAO getInstance() {
		if(instance == null) {
			instance = new ReplyDAO();
		}
		return instance;
	}
	
	public Connection getConnection() throws Exception {
		Context iCtx = new InitialContext();
		DataSource ds = (DataSource) iCtx.lookup("java:/comp/env/jdbc/ora");
		
		return ds.getConnection();
	}
	
	public int insertReply(String commentId, String comment, int replyseq) throws Exception {
		String sql = "INSERT INTO reply values(reply_seq.nextval,?,?,sysdate,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, commentId);
			pstat.setString(2, comment);
			pstat.setInt(3, replyseq);
			
			return pstat.executeUpdate();
		}
	}
	
	public List<ReplyDTO> selectBySeq(int seq) throws Exception{
		String sql = "SELECT * FROM REPLY WHERE PARENT_SEQ = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, seq);
			try(ResultSet rs = pstat.executeQuery();){
				List<ReplyDTO> list = new ArrayList<>();
				while(rs.next()) {
					int id = rs.getInt("id");
					String writer = rs.getString("writer");
					String contents = rs.getString("contents");
					Timestamp write_date = rs.getTimestamp("write_date");
					int parent_seq = rs.getInt("parent_seq");
					ReplyDTO dto = new ReplyDTO(id, writer, contents, write_date, parent_seq);
					list.add(dto);
				}
				return list;
			}
		}
	}

	public int deleteReply(int id) throws Exception {
		String sql = "DELETE FROM REPLY WHERE ID = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, id);
			
			return pstat.executeUpdate();
		}
		
	}
}
