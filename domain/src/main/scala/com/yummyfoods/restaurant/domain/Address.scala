package com.yummyfoods.restaurant.domain

case class Address(
                    addressId: String,
                    street: String,
                    addressLine1: String,
                    addressLine2: String,
                    city: String,
                    state: String,
                    postalCode: Int,
                    countryCode: String
                  )
