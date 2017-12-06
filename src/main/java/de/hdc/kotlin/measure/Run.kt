package de.hdc.kotlin.measure

//import kotlin.reflect.full.*
import java.util.logging.Logger

/*
 * unwrap companion class to enclosing class given a Java Class
 */
fun <T: Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
  return ofClass
  //return if (ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
  //  ofClass.enclosingClass
  //} else {
  //  ofClass
  //}
}

/*
 * unwrap companion class to enclosing class given a Kotlin Class
 */
//fun <T: Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
//  return unwrapCompanionClass(ofClass.java).kotlin
//}

/*
 * Return logger for Java class, if companion object fix the name
 */
fun <R : Any> R.logger(): Lazy<Logger> {
  return lazy { Logger.getLogger(unwrapCompanionClass(this.javaClass).name) }
}

fun main(args: Array<String>) {
  val sc = Measure(5.0, UNITLESS)
  val t = Measure(2.3, TON)
  val g = Measure(23000.0, GRAM)
  val kg = Measure(5.0, KILO_GRAM)
  val kg2 = Measure(23.0, KILO_GRAM)
  //val d = Measure(11.0, DAY)

    println("g: " + g.toString())
    println("g*2.0: " + g.scalar(2.0))
    println("sc: " + sc.toString())
    println("g/sc: " + g.div(sc))
    assert(g.div(sc).toString().equals("4600.0 g"))
    println("kg: " + kg.toString())
    println("g/kg: " + g.div(kg))
    assert(g.div(kg).toString().equals("4.6"))
    println("kg2/kg: " + kg2.div(kg))
    println("kg/t: " + kg.div(t))
//    println("t: " + t.div(d))
    println(t.scalar(Measure(2.0, UNITLESS)))
    println(t.scalar(2.0))
    println()
    println("t->g " + t.convertTo(GRAM).toString())
    println(Measure(10.0, Prefix.KILO, METER))
    println(Measure(10.0, KILO_METER).times(Measure(2.0, HOUR)))
    println(Measure(10.0, KILO_METER).div(Measure(2.0, SECOND)))
    println("<" + Measure.NaN + ">")
    val n = Measure(0.0, Prefix.MILLI, GRAM)
    println(t.div(n))
    assert((t.div(n) == Measure.NaN))
}
