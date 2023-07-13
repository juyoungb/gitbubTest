package vo;

public class FreeReplyGnb {
// 특정 댓글의 좋아요/싫어요 레코드를 저장하는 클래스
 	private int  frg_idx,fr_idx;
 	private String mi_id, frg_gnb, frg_date;
 	
	public int getFrg_idx() {
		return frg_idx;
	}
	public void setFrg_idx(int frg_idx) {
		this.frg_idx = frg_idx;
	}
	public int getFr_idx() {
		return fr_idx;
	}
	public void setFr_idx(int fr_idx) {
		this.fr_idx = fr_idx;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getFrg_gnb() {
		return frg_gnb;
	}
	public void setFrg_gnb(String frg_gnb) {
		this.frg_gnb = frg_gnb;
	}
	public String getFrg_date() {
		return frg_date;
	}
	public void setFrg_date(String frg_date) {
		this.frg_date = frg_date;
	}
}
