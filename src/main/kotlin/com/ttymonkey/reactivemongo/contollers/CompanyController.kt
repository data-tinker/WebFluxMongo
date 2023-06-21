package com.ttymonkey.reactivemongo.contollers

import com.ttymonkey.reactivemongo.dto.CompanyRequest
import com.ttymonkey.reactivemongo.dto.CompanyResponse
import com.ttymonkey.reactivemongo.services.CompanyService
import com.ttymonkey.reactivemongo.toResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/companies")
class CompanyController(
    private val companyService: CompanyService,
) {
    @PostMapping
    suspend fun createCompany(@RequestBody request: CompanyRequest): CompanyResponse {
        return companyService.createCompany(request).toResponse()
    }

    @GetMapping
    fun findAllCompanies(): Flow<CompanyResponse> {
        return companyService.findAll()
            .map { it.toResponse() }
            .onEach { delay(2000) }
    }

    @GetMapping("/{id}")
    suspend fun findCompanyById(@PathVariable id: String): CompanyResponse {
        return companyService.findById(id).toResponse()
    }

    @PutMapping("/{id}")
    suspend fun updateCompany(
        @PathVariable id: String,
        @RequestBody request: CompanyRequest,
    ): CompanyResponse {
        return companyService.updateCompany(id, request).toResponse()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteCompany(@PathVariable id: String) {
        return companyService.deleteById(id)
    }
}
