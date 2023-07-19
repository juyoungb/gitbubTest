package vo;

public class ProductStock {
// 특정 상품의 옵션(사이즈)별 재고량을 저장할 클래스
	private int ps_idx, ps_size, ps_stock, ps_sale;
	public int getPs_idx() {
		return ps_idx;
	}
	public void setPs_idx(int ps_idx) {
		this.ps_idx = ps_idx;
	}
	public int getPs_size() {
		return ps_size;
	}
	public void setPs_size(int ps_size) {
		this.ps_size = ps_size;
	}
	public int getPs_stock() {
		return ps_stock;
	}
	public void setPs_stock(int ps_stock) {
		this.ps_stock = ps_stock;
	}
	public int getPs_sale() {
		return ps_sale;
	}
	public void setPs_sale(int ps_sale) {
		this.ps_sale = ps_sale;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getPs_isview() {
		return ps_isview;
	}
	public void setPs_isview(String ps_isview) {
		this.ps_isview = ps_isview;
	}
	private String pi_id, ps_isview;
}
