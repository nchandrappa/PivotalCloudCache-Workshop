package io.pivotal.pad.workshop.paymentservices.repo;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.pivotal.pad.workshop.paymentservices.domain.TransactionRules;

@RepositoryRestResource(path = "transactionrules")
public interface TransactionRulesRepo extends GemfireRepository<TransactionRules, Integer> {

}
