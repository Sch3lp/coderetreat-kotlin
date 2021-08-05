package be.swsb.coderetreat

import be.swsb.coderetreat.Hours.OverruledHours
import be.swsb.coderetreat.Hours.StandardHours
import be.swsb.coderetreat.HoursType.OverruledWorkType
import be.swsb.coderetreat.HoursType.WorkType
import be.swsb.coderetreat.HoursType.OverruledWorkType.OverruledWork
import be.swsb.coderetreat.HoursType.WorkType.PaidLeave
import be.swsb.coderetreat.HoursType.WorkType.Work
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
            val report = absencesOn(July16)

            assertThat(report.on(July16))
                .containsExactly(8 hoursOf Work)
        }

        @Test
        fun `when employee adds absences, then sees 8 hours of work on July 16th`() {
            val report = absencesOn(July16) {
                add(8 hoursOf PaidLeave)
            }

            assertThat(report.on(July16))
                .containsExactly(
                    0 hoursOf Work,
                    8 hoursOf PaidLeave,
                    8 hoursOf OverruledWork
                )
        }
    }


}

fun absencesOn(date: LocalDate, addAbsences: MutableList<StandardHours>.() -> Unit) : Report {
    val absences = mutableListOf<StandardHours>()
    absences.addAbsences()
    return absencesOn(date, absences.toList())
}

fun absencesOn(date: LocalDate, hours: List<StandardHours> = emptyList()): Report {
    val fulltimeDaily = 8 hoursOf Work

    val newSchedule = mutableListOf<Hours>()
    hours.forEach {
        val (changedDaily, appliedHours, overruledHours) = fulltimeDaily.subtract(it)
        newSchedule.add(changedDaily)
        newSchedule.add(appliedHours)
        newSchedule.add(overruledHours)
    }

    return mapOf(date to newSchedule)
}

// report
typealias Report = Map<LocalDate, List<Hours>>

fun Report.on(date: LocalDate): List<Hours> = this[date] ?: emptyList()


// helper
infix fun Int.hoursOf(worktype: WorkType) = StandardHours(this, worktype)
infix fun Int.hoursOf(overruledWorkType: OverruledWorkType) = OverruledHours(this, overruledWorkType)

sealed class HoursType {
    sealed class WorkType: HoursType() {
        object Work : WorkType()
        object PaidLeave : WorkType()
        object SickLeave : WorkType()
        object PublicHoliday : WorkType()
    }
    sealed class OverruledWorkType: HoursType() {
        object OverruledWork : OverruledWorkType()
        object OverruledPaidLeave : OverruledWorkType()
        object OverruledSickLeave : OverruledWorkType()
    }

    override fun toString(): String = this::class.java.simpleName
}

sealed class Hours(val hours: Int, val type: HoursType) {
    data class StandardHours(private val _hours: Int, private val _type: WorkType): Hours(_hours, _type){
        override fun toString() = super.toString()
        fun subtract(other: StandardHours): Triple<StandardHours, StandardHours, OverruledHours> {
            val resultingHours = if (this._hours <= other._hours) 0 else this._hours - other._hours
            val appliedHours = if (this._hours >= other._hours) other._hours else other._hours - this._hours
            val overruledHours = if (this._hours <= resultingHours) this._hours else this._hours - resultingHours
            val changed = this.copy(_hours = resultingHours)
            val applied = other.copy(_hours = appliedHours)
            val overruled = OverruledHours(overruledHours, OverruledWork)
            return Triple(changed, applied, overruled)
        }
    }
    data class OverruledHours(private val _hours: Int, private val _type: OverruledWorkType): Hours(_hours, _type) {
        override fun toString() = super.toString()
    }
    override fun toString() = "$hours hours of $type"
}


