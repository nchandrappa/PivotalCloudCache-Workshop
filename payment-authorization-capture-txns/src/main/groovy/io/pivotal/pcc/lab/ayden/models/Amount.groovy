package io.pivotal.pcc.lab.ayden.models

import javax.persistence.Embeddable

@Embeddable
class Amount {
  String value
  String currency
}