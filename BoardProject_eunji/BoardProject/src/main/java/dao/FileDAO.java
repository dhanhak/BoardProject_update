package dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.FileDTO;

public class FileDAO {
	private static FileDAO instance = null;
	public synchronized static FileDAO getInstance() {
		if(instance == null) {
			instance = new FileDAO();
		}
		return instance;
	}

	public Connection getConnection() throws Exception {
		Context iCtx = new InitialContext();
		DataSource ds = (DataSource)iCtx.lookup("java:/comp/env/jdbc/ora");
		return ds.getConnection();
	}

	public int insert(FileDTO dto) throws Exception {
		String sql = "INSERT INTO FILES VALUES(files_seq.nextval,?,?,?)";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, dto.getOriName());
			pstat.setString(2, dto.getSysName());
			pstat.setInt(3, dto.getParent_seq());

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public FileDTO list(int searchSeq) throws Exception {
		String sql = "SELECT * FROM FILES WHERE PARENT_SEQ = ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, searchSeq);
			try(ResultSet rs = pstat.executeQuery();){
				if (rs.next()) {
					int id = rs.getInt("id");
					String oriName = rs.getString("oriName");
					String sysName = rs.getString("sysName");
					int parent_seq = rs.getInt("parent_seq");
					return new FileDTO(id ,oriName, sysName, parent_seq);	
				} else {
					return new FileDTO(0, "", "", 0);
				}
				
			}
		}
	}

}
