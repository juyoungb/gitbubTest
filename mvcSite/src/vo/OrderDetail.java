package vo;

public class OrderDetail {
//특정 주문에서  한 상품에 대한 정보를 저장하는 클래스
	private int  od_idx, ps_idx, od_cnt, od_price, od_size;  
	private String 	oi_id, pi_id, od_name, od_img;
	
	public int getOd_idx() {
		return od_idx;
	}
	public void setOd_idx(int od_idx) {
		this.od_idx = od_idx;
	}
	public int getPs_idx() {
		return ps_idx;
	}
	public void setPs_idx(int ps_idx) {
		this.ps_idx = ps_idx;
	}
	public int getOd_cnt() {
		return od_cnt;
	}
	public void setOd_cnt(int od_cnt) {
		this.od_cnt = od_cnt;
	}
	public int getOd_price() {
		return od_price;
	}
	public void setOd_price(int od_price) {
		this.od_price = od_price;
	}
	public int getOd_size() {
		return od_size;
	}
	public void setOd_size(int od_size) {
		this.od_size = od_size;
	}
	public String getOi_id() {
		return oi_id;
	}
	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getOd_name() {
		return od_name;
	}
	public void setOd_name(String od_name) {
		this.od_name = od_name;
	}
	public String getOd_img() {
		return od_img;
	}
	public void setOd_img(String od_img) {
		this.od_img = od_img;
	}
	

}
