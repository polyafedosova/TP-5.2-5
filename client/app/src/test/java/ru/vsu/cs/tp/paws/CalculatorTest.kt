package ru.vsu.cs.tp.paws

import org.junit.Test
import org.junit.Assert.*

class CalculatorTest {
    private fun calculateEat(age: Int, move: Int, mass: Double): Double {

        var ageCoef = 1.0
        var moveCoef = 1.0
        when (age) {
            0 -> ageCoef = 3.2
            1 -> ageCoef = 2.0
            2 -> ageCoef = 1.0
        }

        when (move) {
            0 -> moveCoef = 0.8
            1 -> moveCoef = 2.0
            2 -> moveCoef = 3.2
        }
        return mass * 30 + ageCoef * 5 + moveCoef * 10
    }

    @Test
    fun testAns() {
        val delta = 0.0001
        val result = calculateEat(1,1,5.0)
        assertEquals(180.0, result, delta)
    }
}