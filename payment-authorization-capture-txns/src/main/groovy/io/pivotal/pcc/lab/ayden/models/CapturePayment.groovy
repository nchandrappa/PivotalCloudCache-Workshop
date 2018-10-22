package io.pivotal.pcc.lab.ayden.models

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class CapturePayment {
	
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  Long id

  String originalReference
  Amount modificationAmount
  String reference
  String merchantAccount

  boolean equals(o) {
    if (this.is(o)) return true
    if (!(o instanceof Payment)) return false

    Payment payment = (Payment) o

    if (id != payment.id) return false

    return true
  }

  int hashCode() {
    return (id != null ? id.hashCode() : 0)
  }
}