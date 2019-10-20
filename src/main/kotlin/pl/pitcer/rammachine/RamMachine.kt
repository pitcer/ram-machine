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
import pl.pitcer.rammachine.instruction.result.HaltResult
import pl.pitcer.rammachine.instruction.result.JumpResult

class RamMachine(
	private val inputTape: List<Int>
) {

	private val memory: MutableList<Int> = mutableListOf(1024)
	private val outputTape: MutableList<Int> = mutableListOf()
	private var inputTapeIndex = 0

	fun run(instructions: List<Instruction>, startIndex: Int = 0): List<Int> {
		for (index in startIndex..instructions.lastIndex) {
			val instruction = instructions[index]
			val result = instruction.make()
			when (result) {
				is JumpResult -> {
					val label = result.label
					val labeledInstructionIndex = getLabeledInstructionIndex(instructions, label)
					if (labeledInstructionIndex != null) {
						return run(instructions, labeledInstructionIndex)
					}
				}
				is HaltResult -> return this.outputTape
			}
		}
		return this.outputTape
	}

	private fun getLabeledInstructionIndex(instructions: List<Instruction>, label: String): Int? {
		for ((index, instruction) in instructions.withIndex()) {
			if (instruction.label == label) {
				return index
			}
		}
		return null
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

	fun getFromMemoryIndirect(indirectIndex: Int): Int {
		val index = getFromMemory(indirectIndex)
		return getFromMemory(index)
	}

	fun getFromMemory(index: Int): Int {
		return this.memory[index]
	}

	fun putInMemory(index: Int, value: Int) {
		this.memory.add(index, value)
	}
}
