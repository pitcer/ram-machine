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
import pl.pitcer.rammachine.instruction.argument.ArgumentFlag
import pl.pitcer.rammachine.instruction.argument.InstructionArgument

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
		val split = codeLine.split(" ")
		val instructionLine = getInstructionLine(split)
		return this.instructionFactory.createInstruction(instructionLine)
	}

	private fun getInstructionLine(split: List<String>): InstructionLine {
		val (label, name, argument) = if (split.size == 3) {
			Triple(split[0], split[1], split[2])
		} else {
			Triple(null, split[0], split[1])
		}
		val instructionArgument = parseArgument(argument)
		return InstructionLine(label, name, instructionArgument)
	}

	private fun parseArgument(argument: String): InstructionArgument {
		val flag = ArgumentFlag.getArgumentFlag(argument)
		val argumentWithoutFlag = argument.removePrefix(flag.flag)
		return InstructionArgument(flag, argumentWithoutFlag)
	}
}
