package dto;

public class CustomerPhoneDTO {
	private long id;
	private String phoneNumber;
	private long CustomerId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public long getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(long customerId) {
		CustomerId = customerId;
	}
	
	
}
