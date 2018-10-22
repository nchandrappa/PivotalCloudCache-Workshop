package io.pivotal.pcc.lab.ayden.controllers

import javax.transaction.Transactional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import io.pivotal.pcc.lab.ayden.models.CapturePayment
import io.pivotal.pcc.lab.ayden.models.Payment
import io.pivotal.pcc.lab.ayden.services.CapturePaymentService

@RestController
@RequestMapping('capture')
@Transactional
class CapturePaymentController {
  @Autowired
  CapturePaymentService capturePaymentService

  @GetMapping('')
  List findAll() {
    capturePaymentService.findAll()
  }

  @GetMapping('{id}')
  CapturePayment findOne(@PathVariable long id) {
    capturePaymentService.findById(id)
  }

  @PostMapping('')
  CapturePayment save(@RequestBody CapturePayment capturePayment) {
    capturePaymentService.save(capturePayment)
  }

  @PutMapping('{id}')
  CapturePayment update(@RequestBody CapturePayment capturePayment, @PathVariable long id) {
    capturePaymentService.update(capturePayment, id)
  }

  @DeleteMapping('{id}')
  Payment deleteById(@PathVariable long id) {
    capturePaymentService.deleteById(id)
  }
}