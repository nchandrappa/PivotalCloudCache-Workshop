package io.pivotal.pad.workshop.paymentservices.service;

import org.springframework.stereotype.Service;

import io.pivotal.pad.workshop.paymentservices.domain.TransactionRules;
import io.pivotal.pad.workshop.paymentservices.repo.TransactionRulesRepo;

@Service
public class ReferenceDataService {

	TransactionRulesRepo transactionRulesRepo;

	public ReferenceDataService(TransactionRulesRepo transactionRulesRepo) {
		this.transactionRulesRepo = transactionRulesRepo;
	}

	private volatile boolean cacheMiss = false;

	public boolean isCacheMiss() {
		boolean isCacheMiss = this.cacheMiss;
		this.cacheMiss = false;
		return isCacheMiss;
	}

	protected void setCacheMiss() {
		this.cacheMiss = true;
	}

	public TransactionRules getRulesByID(Integer id) {

		TransactionRules sampleRule = transactionRulesRepo.findById(id).get();

		return sampleRule;
	}

}
