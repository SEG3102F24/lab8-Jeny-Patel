package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller

import org.springframework.stereotype.Component
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.Argument
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import seg3x02.employeeGql.resolvers.types.UpdateEmployeeInput
import java.util.*

@Component
@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    // Query to get all employees
    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    // Query to get a specific employee by ID
    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeeRepository.findById(id).orElse(null)

    //add a new employee
    @MutationMapping
    fun addEmployee(@Argument input: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = input.name,
            dateOfBirth = input.dateOfBirth,
            city = input.city,
            salary = input.salary,
            gender = input.gender,
            email = input.email
        )
        employee.id = UUID.randomUUID().toString()  // Assign a new ID
        return employeeRepository.save(employee)
    }

    //update an employee's details
    @MutationMapping
    fun updateEmployee(@Argument id: String, @Argument input: UpdateEmployeeInput): Employee? {
        val existingEmployee = employeeRepository.findById(id).orElse(null) ?: return null
        existingEmployee.apply {
            input.name?.let { name = it }
            input.dateOfBirth?.let { dateOfBirth = it }
            input.city?.let { city = it }
            input.salary?.let { salary = it }
            input.gender?.let { gender = it }
            input.email?.let { email = it }
        }
        return employeeRepository.save(existingEmployee)
    }

    //delete an employee
    @MutationMapping
    fun deleteEmployee(@Argument id: String): Boolean {
        return if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
