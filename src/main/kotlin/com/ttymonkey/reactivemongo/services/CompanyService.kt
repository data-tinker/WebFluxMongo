package com.ttymonkey.reactivemongo.services

import com.ttymonkey.reactivemongo.dto.CompanyRequest
import com.ttymonkey.reactivemongo.errors.NotFoundException
import com.ttymonkey.reactivemongo.models.Company
import com.ttymonkey.reactivemongo.repositories.CompanyRepository
import com.ttymonkey.reactivemongo.repositories.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository,
) {

    suspend fun createCompany(request: CompanyRequest): Company =
        companyRepository.save(
            Company(
                name = request.name,
                address = request.address,
            ),
        )

    fun findAll(): Flow<Company> =
        companyRepository.findAll()

    suspend fun findById(id: String): Company =
        companyRepository.findById(id)
            ?: throw NotFoundException("Company with id $id not found.")

    suspend fun updateCompany(id: String, request: CompanyRequest): Company {
        val foundCompany = companyRepository.findById(id)

        return if (foundCompany == null) {
            throw throw NotFoundException("Company with id $id not found.")
        } else {
            val updatedCompany = companyRepository.save(
                Company(
                    id = id,
                    name = request.name,
                    address = request.address,
                ),
            )

            updateCompanyEmployees(updatedCompany).collect()

            updatedCompany
        }
    }

    suspend fun deleteById(id: String) =
        companyRepository.deleteById(id)

    private fun updateCompanyEmployees(updatedCompany: Company) =
        employeeRepository.saveAll(
            employeeRepository.findByCompanyId(updatedCompany.id!!)
                .map { it.apply { company = updatedCompany } },
        )
}
