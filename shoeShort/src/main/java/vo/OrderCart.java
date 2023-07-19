package vo;

import java.util.*;

public class OrderCart {
// 장바구니에 담긴 하나의 상품 정보를 저장할 클래스
	// t_order_cart 테이블의 컬럼 정보들
	private int oc_idx, ps_idx, oc_cnt;
	private String mi_id, pi_id, oc_date;
	
	// 장바구니 화면에서 보여줄 정보들
	private int pi_price, ps_stock, ps_size;
	private double pi_dc;
	private String pi_name, pi_img1;
	private ArrayList<ProductStock> stockList;
	
	public int getOc_idx() {
		return oc_idx;
	}
	public void setOc_idx(int oc_idx) {
		this.oc_idx = oc_idx;
	}
	public int getPs_idx() {
		return ps_idx;
	}
	public void setPs_idx(int ps_idx) {
		this.ps_idx = ps_idx;
	}
	public int getOc_cnt() {
		return oc_cnt;
	}
	public void setOc_cnt(int oc_cnt) {
		this.oc_cnt = oc_cnt;
	}
	public String getMi_id() {
		return mi_id;
	}
	public void setMi_id(String mi_id) {
		this.mi_id = mi_id;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getOc_date() {
		return oc_date;
	}
	public void setOc_date(String oc_date) {
		this.oc_date = oc_date;
	}
	public int getPi_price() {
		return pi_price;
	}
	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}
	public int getPs_stock() {
		return ps_stock;
	}
	public void setPs_stock(int ps_stock) {
		this.ps_stock = ps_stock;
	}
	public int getPs_size() {
		return ps_size;
	}
	public void setPs_size(int ps_size) {
		this.ps_size = ps_size;
	}
	public double getPi_dc() {
		return pi_dc;
	}
	public void setPi_dc(double pi_dc) {
		this.pi_dc = pi_dc;
	}
	public String getPi_name() {
		return pi_name;
	}
	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public ArrayList<ProductStock> getStockList() {
		return stockList;
	}
	public void setStockList(ArrayList<ProductStock> stockList) {
		this.stockList = stockList;
	}
}
