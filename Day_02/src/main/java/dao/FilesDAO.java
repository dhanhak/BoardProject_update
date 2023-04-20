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

import dto.FilesDTO;

public class FilesDAO {
	private static FilesDAO instance = null;
	public synchronized static FilesDAO getInstance() {
		if(instance == null) {
			instance = new FilesDAO();
		}
		return instance;
	}

	public Connection getConnection() throws Exception {
		Context iCtx = new InitialContext();
		DataSource ds = (DataSource)iCtx.lookup("java:/comp/env/jdbc/ora");
		return ds.getConnection();
	}
	
	public int insert(FilesDTO dto) throws Exception {
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

	public List<FilesDTO> list() throws Exception {
		String sql = "SELECT * FROM FILES";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery()){
			List<FilesDTO> list = new ArrayList<>();
			while(rs.next()) {
					int id = rs.getInt("id");
					String oriName = rs.getString("oriName");
					String sysName = rs.getString("sysName");
					int parent_seq = rs.getInt("parent_seq");
					FilesDTO dto = new FilesDTO(id ,oriName, sysName, parent_seq);
					list.add(dto);
				}
			return list;
		}
	}
	
}
