package vo;

import java.util.*;

public class ProductInfo {
	//하나의 상품정보들을 저장할 클래스
	private int pi_price, pi_cost, pi_read, pi_review, pi_sale, ai_idx, pi_admin, stock;

	private String pi_id, pcs_id, pb_id, pi_name, pi_com, pi_img1, pi_img2, pi_img3, pi_desc, pi_special, pi_isview, pi_date, pi_last;
	private String pcb_name, pcs_name, pb_name;	//상품상세보기 화면에서 사용할 분류와 브랜드명을 저장할 변수들
	private double pi_dc, pi_score;
	private ArrayList<ProductStock> stockList;	//현 상품에 속하는 옵션 및 재고량들을 저장할 ArrayList
	
	public int getPi_price() {
		return pi_price;
	}
	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}
	public int getPi_cost() {
		return pi_cost;
	}
	public void setPi_cost(int pi_cost) {
		this.pi_cost = pi_cost;
	}
	public int getPi_read() {
		return pi_read;
	}
	public void setPi_read(int pi_read) {
		this.pi_read = pi_read;
	}
	public int getPi_review() {
		return pi_review;
	}
	public void setPi_review(int pi_review) {
		this.pi_review = pi_review;
	}
	public int getPi_sale() {
		return pi_sale;
	}
	public void setPi_sale(int pi_sale) {
		this.pi_sale = pi_sale;
	}
	public int getAi_idx() {
		return ai_idx;
	}
	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}
	public int getPi_admin() {
		return pi_admin;
	}
	public void setPi_admin(int pi_admin) {
		this.pi_admin = pi_admin;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getPcs_id() {
		return pcs_id;
	}
	public void setPcs_id(String pcs_id) {
		this.pcs_id = pcs_id;
	}
	public String getPb_id() {
		return pb_id;
	}
	public void setPb_id(String pb_id) {
		this.pb_id = pb_id;
	}
	public String getPi_name() {
		return pi_name;
	}
	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}
	public String getPi_com() {
		return pi_com;
	}
	public void setPi_com(String pi_com) {
		this.pi_com = pi_com;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public String getPi_img2() {
		return pi_img2;
	}
	public void setPi_img2(String pi_img2) {
		this.pi_img2 = pi_img2;
	}
	public String getPi_img3() {
		return pi_img3;
	}
	public void setPi_img3(String pi_img3) {
		this.pi_img3 = pi_img3;
	}
	public String getPi_desc() {
		return pi_desc;
	}
	public void setPi_desc(String pi_desc) {
		this.pi_desc = pi_desc;
	}
	public String getPi_special() {
		return pi_special;
	}
	public void setPi_special(String pi_special) {
		this.pi_special = pi_special;
	}
	public String getPi_isview() {
		return pi_isview;
	}
	public void setPi_isview(String pi_isview) {
		this.pi_isview = pi_isview;
	}
	public String getPi_date() {
		return pi_date;
	}
	public void setPi_date(String pi_date) {
		this.pi_date = pi_date;
	}
	public String getPi_last() {
		return pi_last;
	}
	public void setPi_last(String pi_last) {
		this.pi_last = pi_last;
	}
	public String getPcb_name() {
		return pcb_name;
	}
	public void setPcb_name(String pcb_name) {
		this.pcb_name = pcb_name;
	}
	public String getPcs_name() {
		return pcs_name;
	}
	public void setPcs_name(String pcs_name) {
		this.pcs_name = pcs_name;
	}
	public String getPb_name() {
		return pb_name;
	}
	public void setPb_name(String pb_name) {
		this.pb_name = pb_name;
	}
	public double getPi_dc() {
		return pi_dc;
	}
	public void setPi_dc(double pi_dc) {
		this.pi_dc = pi_dc;
	}
	public double getPi_score() {
		return pi_score;
	}
	public void setPi_score(double pi_score) {
		this.pi_score = pi_score;
	}
	public ArrayList<ProductStock> getStockList() {
		return stockList;
	}
	public void setStockList(ArrayList<ProductStock> stockList) {
		this.stockList = stockList;
	}
}
