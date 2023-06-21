package com.ttymonkey.reactivemongo.repositories

import com.ttymonkey.reactivemongo.models.Company
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CompanyRepository : CoroutineCrudRepository<Company, String>
