package seg3x02.employeeGql.resolvers.types

data class UpdateEmployeeInput(
    val name: String?,
    val dateOfBirth: String?,
    val city: String?,
    val salary: Float?,
    val gender: String?,
    val email: String?
)
