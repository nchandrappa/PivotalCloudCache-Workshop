package io.pivotal.pcc.lab.ayden.services

import javax.persistence.EntityNotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

import io.pivotal.pcc.lab.ayden.models.CapturePayment
import io.pivotal.pcc.lab.ayden.models.Payment
import io.pivotal.pcc.lab.ayden.repositories.CapturePaymentRepository

@Service
class CapturePaymentService {
  @Autowired
  CapturePaymentRepository capturePaymentRepository

  List findAll() {
    capturePaymentRepository.findAll(Sort.by('reference')).asList()
  }

  CapturePayment findById(long id) {
    capturePaymentRepository.findById(id).orElse(null)
  }

  CapturePayment findByIdOrError(long id) {
    CapturePaymentRepository.findById(id).orElseThrow({
      new EntityNotFoundException()
    })
  }

  CapturePayment save(CapturePayment capturePayment) {
    capturePaymentRepository.save(capturePayment)
  }

  CapturePayment update(CapturePayment capturePayment, long id) {
    CapturePayment persisted = findByIdOrError(id)
    persisted.with {
      reference = capturePayment.reference
    }
    def toBeRemoved = []
    // find values to update & delete

    captureRepository.save(persisted)
  }

  Payment deleteById(long id) {
    def payment = findByIdOrError(id)
    paymentRepository.delete(payment)
    payment
  }
}