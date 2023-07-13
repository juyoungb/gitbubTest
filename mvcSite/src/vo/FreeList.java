package vo;

public class FreeList {
//하나의 자유게시판 게시글 정보들을 저장할 클래스
	private int fl_idx, fl_reply, fl_read;
	private String fl_ismem, fl_writer,	fl_pw, fl_title, fl_content, fl_ip, fl_isview, fl_date;

	public int getFl_idx() {
		return fl_idx;
	}

	public void setFl_idx(int fl_idx) {
		this.fl_idx = fl_idx;
	}

	public int getFl_reply() {
		return fl_reply;
	}

	public void setFl_reply(int fl_reply) {
		this.fl_reply = fl_reply;
	}

	public int getFl_read() {
		return fl_read;
	}

	public void setFl_read(int fl_read) {
		this.fl_read = fl_read;
	}

	public String getFl_ismem() {
		return fl_ismem;
	}

	public void setFl_ismem(String fl_ismem) {
		this.fl_ismem = fl_ismem;
	}

	public String getFl_writer() {
		return fl_writer;
	}

	public void setFl_writer(String fl_writer) {
		this.fl_writer = fl_writer;
	}

	public String getFl_pw() {
		return fl_pw;
	}

	public void setFl_pw(String fl_pw) {
		this.fl_pw = fl_pw;
	}

	public String getFl_title() {
		return fl_title;
	}

	public void setFl_title(String fl_title) {
		this.fl_title = fl_title;
	}

	public String getFl_content() {
		return fl_content;
	}

	public void setFl_content(String fl_content) {
		this.fl_content = fl_content;
	}

	public String getFl_ip() {
		return fl_ip;
	}

	public void setFl_ip(String fl_ip) {
		this.fl_ip = fl_ip;
	}

	public String getFl_isview() {
		return fl_isview;
	}

	public void setFl_isview(String fl_isview) {
		this.fl_isview = fl_isview;
	}

	public String getFl_date() {
		return fl_date;
	}

	public void setFl_date(String fl_date) {
		this.fl_date = fl_date;
	}
	
}
