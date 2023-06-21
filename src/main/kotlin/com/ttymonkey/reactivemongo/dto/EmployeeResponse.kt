package com.ttymonkey.reactivemongo.dto

data class EmployeeResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val company: CompanyResponse?,
)
