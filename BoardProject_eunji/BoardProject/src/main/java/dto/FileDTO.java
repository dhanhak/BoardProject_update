package dto;

public class FileDTO {
	private int id;
	private String oriName;
	private String sysName;
	private int parent_seq;
	
	
	public FileDTO() {
		super();
	}
	public FileDTO(int id, String oriName, String sysName, int parent_seq) {
		super();
		this.id = id;
		this.oriName = oriName;
		this.sysName = sysName;
		this.parent_seq = parent_seq;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOriName() {
		return oriName;
	}
	public void setOriName(String oriName) {
		this.oriName = oriName;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public int getParent_seq() {
		return parent_seq;
	}
	public void setParent_seq(int parent_seq) {
		this.parent_seq = parent_seq;
	}
	
}
