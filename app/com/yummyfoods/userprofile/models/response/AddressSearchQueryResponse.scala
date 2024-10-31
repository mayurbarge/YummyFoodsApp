package com.yummyfoods.userprofile.models.response

import com.yummyfoods.restaurant.domain.Address

case class AddressSearchQueryResponse(hits: Int, addresses: List[Address])