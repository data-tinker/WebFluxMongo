package com.ttymonkey.reactivemongo.repositories

import com.ttymonkey.reactivemongo.models.Employee
import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EmployeeRepository : CoroutineCrudRepository<Employee, ObjectId> {
    fun findByCompanyId(id: String): Flow<Employee>
}
