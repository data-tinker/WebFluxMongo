package com.ttymonkey.reactivemongo.contollers

import com.ttymonkey.reactivemongo.dto.EmployeeRequest
import com.ttymonkey.reactivemongo.dto.EmployeeResponse
import com.ttymonkey.reactivemongo.services.EmployeeService
import com.ttymonkey.reactivemongo.toResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val employeeService: EmployeeService,
) {
    @PostMapping
    suspend fun createEmployee(@RequestBody request: EmployeeRequest): EmployeeResponse {
        return employeeService.createEmployee(request).toResponse()
    }

    @GetMapping
    fun findAllEmployees(): Flow<EmployeeResponse> {
        return employeeService.findAll()
            .map { it.toResponse() }
    }

    @GetMapping("/{id}")
    suspend fun findEmployeeById(@PathVariable id: ObjectId): EmployeeResponse {
        return employeeService.findById(id).toResponse()
    }

    @GetMapping("/company/{companyId}")
    fun findAllByCompanyId(@PathVariable companyId: String): Flow<EmployeeResponse> {
        return employeeService.findAllByCompanyId(companyId)
            .map { it.toResponse() }
    }

    @PutMapping("/{id}")
    suspend fun updateUpdateEmployee(
        @PathVariable id: ObjectId,
        @RequestBody request: EmployeeRequest,
    ): EmployeeResponse {
        return employeeService.updateEmployee(id, request).toResponse()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteEmployee(@PathVariable id: ObjectId) {
        return employeeService.deleteById(id)
    }
}
