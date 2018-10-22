package io.pivotal.pcc.lab.ayden.models

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  Long id

  Amount amount
  String reference
  String merchantAccount

  Card card

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