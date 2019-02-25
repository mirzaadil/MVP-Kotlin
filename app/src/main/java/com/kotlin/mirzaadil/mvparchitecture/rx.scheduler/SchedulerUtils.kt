package com.kotlin.mirzaadil.mvparchitecture.rx.scheduler

/**
 * @author Mirza Adil
 * desc:
 */

object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
