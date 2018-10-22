package io.pivotal.pcc.lab.ayden.repositories

import org.springframework.data.repository.PagingAndSortingRepository
import io.pivotal.pcc.lab.ayden.models.Payment

interface PaymentRepository extends PagingAndSortingRepository<Payment, Long> {}