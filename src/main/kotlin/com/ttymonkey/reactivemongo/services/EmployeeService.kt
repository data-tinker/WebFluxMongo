package com.ttymonkey.reactivemongo.services

import com.ttymonkey.reactivemongo.dto.EmployeeRequest
import com.ttymonkey.reactivemongo.errors.NotFoundException
import com.ttymonkey.reactivemongo.models.Employee
import com.ttymonkey.reactivemongo.repositories.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    private val companyService: CompanyService,
    private val employeeRepository: EmployeeRepository,
) {
    companion object {
        private val log = LoggerFactory.getLogger(EmployeeService::class.java)
    }

    suspend fun createEmployee(request: EmployeeRequest): Employee {
        val companyId = request.companyId

        return if (companyId == null) {
            createEmployeeWithoutCompany(request)
        } else {
            createEmployeeWithCompany(companyId, request)
        }
    }

    private suspend fun createEmployeeWithoutCompany(request: EmployeeRequest) =
        employeeRepository.save(
            Employee(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = null,
            ),
        )

    private suspend fun createEmployeeWithCompany(companyId: String, request: EmployeeRequest) =
        employeeRepository.save(
            Employee(
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = companyService.findById(companyId),
            ),
        )

    fun findAll(): Flow<Employee> =
        employeeRepository.findAll()

    fun findAllByCompanyId(id: String): Flow<Employee> =
        employeeRepository.findByCompanyId(id)

    suspend fun findById(id: ObjectId): Employee =
        employeeRepository.findById(id)
            ?: run {
                log.error("Employee with id $id not found.")
                throw NotFoundException("Employee with id $id not found.")
            }

    suspend fun updateEmployee(id: ObjectId, request: EmployeeRequest): Employee {
        val companyId = request.companyId

        return if (companyId == null) {
            updateEmployeeWithoutCompany(id, request)
        } else {
            updateEmployeeWithCompany(id, request, companyId)
        }
    }

    private suspend fun updateEmployeeWithoutCompany(id: ObjectId, request: EmployeeRequest) =
        employeeRepository.save(
            Employee(
                id = id,
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = null,
            ),
        )

    private suspend fun updateEmployeeWithCompany(
        id: ObjectId,
        request: EmployeeRequest,
        companyId: String,
    ) =
        employeeRepository.save(
            Employee(
                id = id,
                firstName = request.firstName,
                lastName = request.lastName,
                email = request.email,
                company = companyService.findById(companyId),
            ),
        )

    suspend fun deleteById(id: ObjectId) {
        return employeeRepository.delete(findById(id))
    }
}
