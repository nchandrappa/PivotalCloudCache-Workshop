package io.pivotal.pcc.lab.ayden.services

import javax.persistence.EntityNotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

import io.pivotal.pcc.lab.ayden.models.Payment
import io.pivotal.pcc.lab.ayden.repositories.PaymentRepository

@Service
class PaymentService {
  @Autowired
  PaymentRepository paymentRepository

  List findAll() {
    paymentRepository.findAll(Sort.by('reference')).asList()
  }

  Payment findById(long id) {
    paymentRepository.findById(id).orElse(null)
  }

  Payment findByIdOrError(long id) {
    paymentRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }

  Payment save(Payment payment) {
    paymentRepository.save(payment)
  }

  Payment update(Payment payment, long id) {
    Payment persisted = findByIdOrError(id)
    persisted.with {
      reference = payment.reference
    }
    def toBeRemoved = []
    // find values to update & delete

    paymentRepository.save(persisted)
  }

  Payment deleteById(long id) {
    def payment = findByIdOrError(id)
    paymentRepository.delete(payment)
    payment
  }
}