/*
 * Copyright (C) 2019 Yuki Anzai (@yanzm)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.yanzm.sample.decorator12.improved

fun main() {
    val b1 = StringDisplay("Hello, world.")
    val b2 = SideBorder(b1, '#')
    val b3 = FullBorder(b2)
    b1.show()
    b2.show()
    b3.show()
    val b4 = SideBorder(
        FullBorder(
            FullBorder(
                SideBorder(
                    FullBorder(
                        StringDisplay("Good Morning")
                    ),
                    '*'
                )
            )
        ),
        '/'
    )
    b4.show()
}

abstract class Display {
    abstract fun getColumns(): Int
    abstract fun getRows(): Int
    abstract fun getRowText(row: Int): String
    fun show() {
        for (i in 0 until getRows()) {
            println(getRowText(i))
        }
    }
}

class StringDisplay(private val string: String) : Display() {

    override fun getColumns(): Int = string.toByteArray().size

    override fun getRows(): Int = 1

    override fun getRowText(row: Int): String = if (row == 0) string else throw IllegalStateException()
}

abstract class Border(protected val display: Display) : Display()

class SideBorder(display: Display, private val borderChar: Char) : Border(display) {

    override fun getColumns(): Int = 1 + display.getColumns() + 1

    override fun getRows(): Int = display.getRows()

    override fun getRowText(row: Int): String = "$borderChar${display.getRowText(row)}$borderChar"
}

class FullBorder(display: Display) : Border(display) {

    override fun getColumns(): Int = 1 + display.getColumns() + 1

    override fun getRows(): Int = 1 + display.getRows() + 1

    override fun getRowText(row: Int): String {
        return when (row) {
            0, display.getRows() + 1 -> "+" + makeLine('-', display.getColumns()) + "+"
            else -> "|" + display.getRowText(row - 1) + "|"
        }
    }

    private fun makeLine(ch: Char, count: Int): String {
        return StringBuilder()
            .apply {
                repeat(count) { append(ch) }
            }
            .toString()
    }
}
