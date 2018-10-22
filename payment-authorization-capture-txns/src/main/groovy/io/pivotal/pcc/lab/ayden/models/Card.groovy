package io.pivotal.pcc.lab.ayden.models

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Embeddable
class Card {
  String Number
  String expiryMonth
  String expiryYear
  String cvc
  String holderName
}