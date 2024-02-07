package pt.pprojects.domain

interface UseCaseInterface<Type, Params> {
    fun execute(refresh: Boolean = false, params: Params): Type
}