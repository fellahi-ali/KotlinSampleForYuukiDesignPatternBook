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

package net.yanzm.sample.templatemethod3.improved

fun main() {
    val d1 = CharDisplay('H')
    val d2 = StringDisplay("Hello, world.")
    val d3 = StringDisplay("こんにちは。")
    d1.display()
    d2.display()
    d3.display()
}

abstract class AbstractDisplay {
    protected abstract fun open()
    protected abstract fun print()
    protected abstract fun close()

    fun display() {
        open()
        repeat(5) { print() }
        close()
    }
}

class CharDisplay(private val char: Char) : AbstractDisplay() {

    override fun open() {
        print("<<")
    }

    override fun print() {
        print(char)
    }

    override fun close() {
        println(">>")
    }
}

class StringDisplay(private val string: String) : AbstractDisplay() {

    private val width = string.toByteArray().size

    override fun open() {
        printLine()
    }

    override fun print() {
        println("|$string|")
    }

    override fun close() {
        printLine()
    }

    private fun printLine() {
        print("+")
        repeat(width) { print("-") }
        println("+")
    }
}
