package io.pivotal.pcc.lab.ayden.controllers

import javax.transaction.Transactional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import io.pivotal.pcc.lab.ayden.models.Payment
import io.pivotal.pcc.lab.ayden.services.PaymentService

@RestController
@RequestMapping('authorize')
@Transactional
class PaymentController {
  @Autowired
  PaymentService paymentService

  @GetMapping('')
  List findAll() {
    paymentService.findAll()
  }

  @GetMapping('{id}')
  Payment findOne(@PathVariable long id) {
    paymentService.findById(id)
  }

  @PostMapping('')
  Payment save(@RequestBody Payment payment) {
    paymentService.save(payment)
  }

  @PutMapping('{id}')
  Payment update(@RequestBody Payment payment, @PathVariable long id) {
    paymentService.update(payment, id)
  }

  @DeleteMapping('{id}')
  Payment deleteById(@PathVariable long id) {
    paymentService.deleteById(id)
  }
}