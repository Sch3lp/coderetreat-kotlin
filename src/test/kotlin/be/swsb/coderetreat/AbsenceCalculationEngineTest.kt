package be.swsb.coderetreat

import be.swsb.coderetreat.WorkType.PaidLeave
import be.swsb.coderetreat.WorkType.Work
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate


private val July16 = LocalDate.of(2018, 7, 16)

class AbsenceCalculationEngineTest {

    @Nested
    inner class `Given employee is expected to work 8 hours on July 16th` {
        @Test
        fun `when employee didn't add absences, then sees 8 hours of work on July 16th`() {
            val report = addAbsencesOn(July16)

            assertThat(report.on(July16))
                .containsExactly(8 hoursOf Work)
        }

        @Test
        fun `when employee adds absences, then sees 8 hours of work on July 16th`() {
            val report = addAbsencesOn(July16, 8 hoursOf PaidLeave)

            assertThat(report.on(July16))
                .containsExactly(
                    0 hoursOf Work,
                    8 hoursOf PaidLeave
                )
        }
    }


}

fun addAbsencesOn(date: LocalDate, vararg hours: Hours): Report {
    return mapOf(date to listOf(8 hoursOf Work))
}

// report
typealias Report = Map<LocalDate, List<Hours>>

fun Report.on(date: LocalDate): List<Hours> = this[date] ?: emptyList()

enum class WorkType {
    Work,
    PaidLeave,
    SickLeave,
    PublicHoliday
}

// helper
infix fun Int.hoursOf(worktype: WorkType) = Hours(this, worktype)

data class Hours(private val hours: Int, private val type: WorkType) {
    override fun toString() = "$hours hours of $type"
}

