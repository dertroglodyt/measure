package io.kotlintest.provided

import io.kotlintest.Description
import io.kotlintest.TestResult
import io.kotlintest.extensions.TestListener
import de.hdc.measure.m
import de.hdc.measure.s
import io.kotlintest.AbstractProjectConfig

object TimerListener : TestListener {

  private var started = 0 m s

  override fun beforeTest(description: Description) {
    started = System.currentTimeMillis() m s
  }

  override fun afterTest(description: Description, result: TestResult) {
    println("Duration of ${description.name} = ${(System.currentTimeMillis() m s) - started}")
  }

}

class ProjectConfig: AbstractProjectConfig() {
  override fun listeners(): List<TestListener> = listOf(TimerListener)
  override fun parallelism(): Int = 1
}