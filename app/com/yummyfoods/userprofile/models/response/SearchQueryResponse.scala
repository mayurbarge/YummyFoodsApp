package com.yummyfoods.userprofile.models.response

import com.yummyfoods.restaurant.domain.Restaurant

case class SearchQueryResponse(hits: Int, restaurants: List[Restaurant])

