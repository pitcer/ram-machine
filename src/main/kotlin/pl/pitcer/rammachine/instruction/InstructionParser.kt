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

package pl.pitcer.rammachine.instruction

import pl.pitcer.rammachine.RamMachine

class InstructionParser(
	private val ramMachine: RamMachine,
	private val codeLines: List<String>
) {

	private val instructionFactory = InstructionFactory(this.ramMachine)

	fun parseInstructions(): List<Instruction> {
		return this.codeLines.map {
			parseInstruction(it)
		}.toList()
	}

	private fun parseInstruction(codeLine: String): Instruction {
		val trimmed = codeLine.trimRedundantWhitespaces()
		val split = trimmed.split(" ")
		val instructionLine = getInstructionLine(split)
		return this.instructionFactory.createInstruction(instructionLine)
	}

	private fun String.trimRedundantWhitespaces(): String {
		val builder = StringBuilder()
		var previousCharacter = this[0]
		for (character in this) {
			if (character != '\t') {
				if (previousCharacter != ' ' || character != ' ') {
					builder.append(character)
				}
				previousCharacter = character
			}
		}
		return builder.toString()
	}

	private fun getInstructionLine(split: List<String>): InstructionLine {
		return when {
			split.size == 3 -> InstructionLine(split[0], split[1], split[2])
			split.size == 2 -> InstructionLine(null, split[0], split[1])
			split.size == 1 -> InstructionLine(null, split[0], null)
			else -> throw RuntimeException()
		}
	}
}
