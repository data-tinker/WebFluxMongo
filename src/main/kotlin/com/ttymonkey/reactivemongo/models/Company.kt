package com.ttymonkey.reactivemongo.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document
data class Company(
    @Id
    val id: String? = null,
    var name: String,
    @Field("company_address")
    val address: String
)
