package be.swsb.coderetreat

import be.swsb.coderetreat.WorkType.Work
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.jvm.JvmInline


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
typealias Report = Map<LocalDate, Hours>

fun Report.on(date: LocalDate) : Hours? = this[date]

enum class WorkType {
    Work
}

// helper
infix fun Int.hoursOf(worktype: WorkType) = Hours(this to worktype)

@JvmInline
value class Hours(private val value: Pair<Int, WorkType>) {
    val hours: Int
        get() = value.first
    val type: WorkType
        get() = value.second

    override fun toString() = "$hours hours of $type"
}

