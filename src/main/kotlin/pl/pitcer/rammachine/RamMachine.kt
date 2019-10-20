/*
 * MIT License
 *
 * Copyright (c) 2019 Piotr Dobiech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.pitcer.rammachine

import pl.pitcer.rammachine.instruction.Instruction

class RamMachine(
	private val inputTape: List<Int>
) {

	private val memory: MutableList<Int> = mutableListOf(1024)
	private val outputTape: MutableList<Int> = mutableListOf()
	private var inputTapeIndex = 0

	fun run(instructions: List<Instruction>): List<Int> {
		instructions.forEach {
			it.make()
		}
		return this.outputTape
	}

	fun readFromInputTape(): Int {
		val value = this.inputTape[this.inputTapeIndex]
		this.inputTapeIndex++
		return value
	}

	fun writeToOutputTape(value: Int) {
		this.outputTape.add(value)
	}

	fun getFromAccumulator(): Int {
		return getFromMemory(0)
	}

	fun putInAccumulator(value: Int) {
		putInMemory(0, value)
	}

	fun getFromMemory(index: Int): Int {
		return this.memory[index]
	}

	fun putInMemory(index: Int, value: Int) {
		this.memory.add(index, value)
	}
}
