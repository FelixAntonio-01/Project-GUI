
public class Cart {
	
	private String id = "";
	private String name = "";
	private Integer price = 0;
	private Integer qty = 0;

	
	
	



	public Cart(String id, String name, Integer price, Integer qty) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.qty = qty;
		
	}







	public Integer getQty() {
		return qty;
	}



	public void setQty(Integer qty) {
		this.qty = qty;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getPrice() {
		return price;
	}



	public void setPrice(Integer price) {
		this.price = price;
	}


	

}
