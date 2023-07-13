package vo;

public class FreeReply {
// 자유게시판내 하나의 댓글 정보를 저장하는 클래스
	private int fr_idx, fl_idx, fr_good, fr_bad;
	private String 	mi_id, fr_content, fr_ip, fr_isview, fr_date;
	public int getFr_idx() {
		return fr_idx;
	}
	public void setFr_idx(int fr_idx) {
		this.fr_idx = fr_idx;
	}
	public int getFl_idx() {
		return fl_idx;
	}
	public void setFl_idx(int fl_idx) {
		this.fl_idx = fl_idx;
	}
	public int getFr_good() {
		return fr_good;
	}
	public void setFr_good(int fr_good) {
		this.fr_good = fr_good;
	}
	public int getFr_bad() {
		return fr_bad;
	}
	public void setFr_bad(int fr_bad) {
		this.fr_bad = fr_bad;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getFr_content() {
		return fr_content;
	}
	public void setFr_content(String fr_content) {
		this.fr_content = fr_content;
	}
	public String getFr_ip() {
		return fr_ip;
	}
	public void setFr_ip(String fr_ip) {
		this.fr_ip = fr_ip;
	}
	public String getFr_isview() {
		return fr_isview;
	}
	public void setFr_isview(String fr_isview) {
		this.fr_isview = fr_isview;
	}
	public String getFr_date() {
		return fr_date;
	}
	public void setFr_date(String fr_date) {
		this.fr_date = fr_date;
	}


}
