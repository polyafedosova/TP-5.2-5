package dto

import java.math.BigDecimal

data class VetclinicSortDto (
    val vetclinicDto: VetclinicDtoGet,
    val minPrice: BigDecimal
        )

