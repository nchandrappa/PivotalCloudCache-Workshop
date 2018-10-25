package io.pivotal.pad.workshop.paymentservices.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Region("TransactionRules")
public class TransactionRules {
	
	@Id
	Integer id;
	String state;
	Double salesTaxPct;
	
	public TransactionRules(Integer id, String state, double salesTaxPct) {
		this.id = id;
		this.state = state;
		this.salesTaxPct = salesTaxPct;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Double getSalesTaxPct() {
		return salesTaxPct;
	}
	public void setSalesTaxPct(Double salesTaxPct) {
		this.salesTaxPct = salesTaxPct;
	}
	@Override
	public String toString() {
		return "TransactionRules [id=" + id + ", state=" + state + ", salesTaxPct=" + salesTaxPct + "]";
	}
}
