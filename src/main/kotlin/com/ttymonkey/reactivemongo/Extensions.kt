package com.ttymonkey.reactivemongo

import com.ttymonkey.reactivemongo.dto.CompanyResponse
import com.ttymonkey.reactivemongo.dto.EmployeeResponse
import com.ttymonkey.reactivemongo.models.Company
import com.ttymonkey.reactivemongo.models.Employee

fun Company.toResponse() =
    CompanyResponse(
        id = this.id!!,
        name = this.name,
        address = this.address,
    )

fun Employee.toResponse() =
    EmployeeResponse(
        id = this.id!!.toHexString(),
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        company = this.company?.toResponse(),
    )
