package dto;

import java.util.List;

public class CustomerDTO {

	private long id;
	private String name;
	private String surname;
	private List<CustomerPhoneDTO> customerPhoneDTOList;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<CustomerPhoneDTO> getCustomerPhoneDTOList() {
		return customerPhoneDTOList;
	}

	public void setCustomerPhoneDTOList(List<CustomerPhoneDTO> customerPhoneDTOList) {
		this.customerPhoneDTOList = customerPhoneDTOList;
	}

}
