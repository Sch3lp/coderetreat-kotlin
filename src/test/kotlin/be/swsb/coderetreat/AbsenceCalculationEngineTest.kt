package be.swsb.coderetreat

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
                .isEqualTo(8 hoursOf Work)
        }
        @Test
        fun `when employee adds absences, then sees 8 hours of work on July 16th`() {
            val report = addAbsencesOn(July16)

            assertThat(report.on(July16))
                .isEqualTo(8 hoursOf Work)
        }
    }


}

fun addAbsencesOn(date: LocalDate, vararg hours: Hours): Report {
    return mapOf()
}

// report
typealias Report = Map<LocalDate, Hours>

fun Report.on(date: LocalDate) : Hours? = this[date]

enum class WorkType {
    Work
}

// helper
infix fun Int.hoursOf(worktype: WorkType) = Hours(this, worktype)

data class Hours(private val hours: Int, private val type: WorkType) {
    override fun toString() = "$hours hours of $type"
}

