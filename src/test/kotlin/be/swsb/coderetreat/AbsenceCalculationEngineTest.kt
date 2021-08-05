package be.swsb.coderetreat

import be.swsb.coderetreat.WorkType.Work
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


private val July16 = LocalDate.of(2018, 7, 16)

class AbsenceCalculationEngineTest {

    @Test
    fun `Given employee is expected to work 8 hours on July 16th, then sees 8 hours of work on July 16th`() {
        val report = calculate(July16)

        assertThat(report.on(July16))
            .isEqualTo(8 hoursOf Work)
    }

}

fun calculate(date: LocalDate): Report {
    return mapOf()
}

// report
typealias Report = Map<LocalDate,Pair<Int, WorkType>>

fun Report.on(date: LocalDate) : Pair<Int, WorkType>? = this[date]

enum class WorkType {
    Work
}

// helper
infix fun Int.hoursOf(worktype: WorkType) = this to worktype

fun Pair<Int, WorkType>.toString() = "$first hours of $second"