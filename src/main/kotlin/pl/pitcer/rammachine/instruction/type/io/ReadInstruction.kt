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

package pl.pitcer.rammachine.instruction.type.io

import pl.pitcer.rammachine.RamMachine
import pl.pitcer.rammachine.instruction.Instruction
import pl.pitcer.rammachine.instruction.argument.ArgumentFlag
import pl.pitcer.rammachine.instruction.argument.InstructionArgument
import pl.pitcer.rammachine.instruction.result.InstructionResult
import pl.pitcer.rammachine.instruction.result.OkResult

class ReadInstruction(
	override val ramMachine: RamMachine,
	override val label: String?,
	override val argument: InstructionArgument?
) : Instruction {

	override val name: String = "read"

	override fun make(): InstructionResult {
		val inputTapeValue = this.ramMachine.readFromInputTape()
		val argument = this.argument ?: throw RuntimeException()
		val argumentValue = argument.value.toInt()
		val value = when (argument.flag) {
			ArgumentFlag.MEMORY_REFERENCE -> argumentValue
			ArgumentFlag.VALUE -> throw RuntimeException()
			ArgumentFlag.INDIRECT_ADDRESSING -> this.ramMachine.getFromMemory(argumentValue)
		}
		this.ramMachine.putInMemory(value, inputTapeValue)
		return OkResult()
	}
}
