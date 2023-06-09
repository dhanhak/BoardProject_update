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
import javax.sql.DataSource;

import dto.BoardDTO;
import statics.Settings;

public class BoardDAO {

	private static BoardDAO instance = null;
	public synchronized static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}

	public Connection getConnection() throws Exception {
		Context iCtx = new InitialContext();
		DataSource ds = (DataSource)iCtx.lookup("java:/comp/env/jdbc/ora");
		return ds.getConnection();
	}

	public int getBoardSeq() throws Exception {
		String sql = "SELECT BOARD_SEQ.nextval FROM DUAL";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();){
			rs.next();
			return rs.getInt(1);
		}
	}

	public int insertContent(int boardseq, String writer, String title, String contents) throws Exception {
		String sql = "insert into board values(?,?,?,?,0,sysdate)";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, boardseq);
			pstat.setString(2, writer);
			pstat.setString(3, title);
			pstat.setString(4, contents);

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public List<BoardDTO> selectContent() throws Exception {
		String sql = "select * from board order by 1 desc";

		List<BoardDTO> result = new ArrayList<>();
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);
				ResultSet rs = pstat.executeQuery();){
			while(rs.next()) {
				int seq = rs.getInt("seq");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				int view_count = rs.getInt("view_count");
				Timestamp write_date = rs.getTimestamp("write_date");

				result.add(new BoardDTO(seq,writer,title,null,view_count,write_date));
			}
			return result;
		}
	}

	public BoardDTO selectContentBySeq(int searchSeq) throws Exception {
		String sql = "select * from board where seq=?";
		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, searchSeq);
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				int seq = rs.getInt("seq");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				int view_count = rs.getInt("view_count");
				Timestamp write_date = rs.getTimestamp("write_date");

				return new BoardDTO(seq,writer,title,contents,view_count,write_date);
			}
		}
	}

	public int deleteContentBySeq(int deleteSeq) throws Exception {
		String sql = "delete from board where seq=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, deleteSeq);

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	public int updateContentBySeq(int updateSeq,String title,String contents) throws Exception {
		String sql = "update board set title=?,contents=?,write_date=sysdate where seq=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, title);
			pstat.setString(2, contents);
			pstat.setInt(3, updateSeq);

			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	// 1 select * from board order by seq desc;
	public List<BoardDTO> selectBound(int start, int end) throws Exception{
		String sql = "select * from(select board.*, row_number() over(order by seq desc) rn from board)where rn BETWEEN ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, start);
			pstat.setInt(2, end);
			try(ResultSet rs = pstat.executeQuery();){
				List<BoardDTO> list = new ArrayList<BoardDTO>();
				while(rs.next()) {
					int seq = rs.getInt("seq");
					String writer = rs.getString("writer");
					String title = rs.getString("title");
					String contents = rs.getString("contents");
					int view_count = rs.getInt("view_count");
					Timestamp write_date = rs.getTimestamp("write_date");
					BoardDTO dto = new BoardDTO(seq, writer,title, contents, view_count, write_date );
					list.add(dto);
				}
				return list;
			}
		}
	}

	public int updateViewCount(int searchSeq) throws Exception {
		String sql = "update board set view_count=(select view_count from board where seq=?)+1 where seq=?";

		try(
				Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setInt(1, searchSeq);
			pstat.setInt(2, searchSeq);
			int result = pstat.executeUpdate();
			con.commit();
			return result;
		}
	}

	private int getRecordCount(String sel, String search) throws Exception {
		String sql = "SELECT count(*) FROM BOARD where "+ sel +" like ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "%"+search+"%");
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				return rs.getInt(1);
			}
		}
	}
	
	private int getRecordCommonCount() throws Exception {
		String sql = "SELECT count(*) FROM BOARD";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			try(ResultSet rs = pstat.executeQuery();){
				rs.next();
				return rs.getInt(1);
			}
		}
	}

	public List<String> getPageNavi(int currentPage, String sel, String search) throws Exception{
		// 네비게이터를 만들기 위해 필요한 초기 정보
		int recordTotalCount = 0;
		if(sel==null && search==null) {
			recordTotalCount = getRecordCommonCount();  	// 1. 전체 글의 개수
		}else {
			recordTotalCount = getRecordCount(sel, search);
		}
//		System.out.println(recordTotalCount);
		int recordCountPerPage = Settings.BOARD_RECORD_COUNT_PER_PAGE;	// 2. 페이지당 보여줄 글의 개수
		int naviCountPerPage = Settings.BOARD_NAVI_COUNT_PER_PAGE;		// 3. 페이지당 보여줄 네비게이터의 개수

		int pageTotalCount;			// 4. 1번과 2번 항복에 의해 총 페이지의 개수가 정해짐

		if(recordTotalCount % recordCountPerPage > 0) {		// 
			pageTotalCount = recordTotalCount / recordCountPerPage + 1;
		}else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		// 전체 글의 개수를 페이지당 보여줄 글의 개수로 나누었을 때, 나머지가 발생하면 페이지 +1

		if(currentPage < 1) {		// 방어코드
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (((currentPage-1) / naviCountPerPage) * naviCountPerPage) +1;	
		int endNavi = startNavi+(naviCountPerPage-1);

		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;

		if(startNavi == 1) {needPrev = false;}
		if(endNavi == pageTotalCount) {needNext = false;}

		List<String> list = new ArrayList<String>();

		if(needPrev) {
			list.add("<");
		}
		for(int i=startNavi; i <= endNavi; i++) {
			list.add(""+i);
		}
		if(needNext) {
			list.add(">");
		}
		return list;
	}

	public List<BoardDTO> searchBoard(int start, int end, String sel,  String search) throws Exception {
		String sql = "select * from(select board.*, row_number() over(order by seq desc) rn from board where "+sel+" like ?)where rn BETWEEN ? and ?";
		try(Connection con = this.getConnection();
				PreparedStatement pstat = con.prepareStatement(sql);){
			pstat.setString(1, "%" +search + "%" );
			pstat.setInt(2, start);
			pstat.setInt(3, end);
			try(ResultSet rs = pstat.executeQuery()){
				List<BoardDTO> list = new ArrayList<>();
				while(rs.next()) {
					int id = rs.getInt("seq");
					String writer = rs.getString("writer");
					String title = rs.getString("title");
					String contents = rs.getString("contents");

					int view_count = rs.getInt("view_count");
					Timestamp write_date = rs.getTimestamp("write_date");
					BoardDTO dto = new BoardDTO(id, writer, title, contents, view_count, write_date);
					list.add(dto);
				}
				return list;
				
			}
		}
	}

}
