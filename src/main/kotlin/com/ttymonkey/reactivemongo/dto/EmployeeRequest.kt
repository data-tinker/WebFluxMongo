package com.ttymonkey.reactivemongo.dto

data class EmployeeRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val companyId: String?,
)
