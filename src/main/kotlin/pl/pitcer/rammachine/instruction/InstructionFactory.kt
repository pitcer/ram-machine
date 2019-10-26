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
import pl.pitcer.rammachine.instruction.type.HaltInstruction
import pl.pitcer.rammachine.instruction.type.accumulator.LoadInstruction
import pl.pitcer.rammachine.instruction.type.accumulator.StoreInstruction
import pl.pitcer.rammachine.instruction.type.arithmetic.AddInstruction
import pl.pitcer.rammachine.instruction.type.arithmetic.DivideInstruction
import pl.pitcer.rammachine.instruction.type.arithmetic.MultiplyInstruction
import pl.pitcer.rammachine.instruction.type.arithmetic.SubtractInstruction
import pl.pitcer.rammachine.instruction.type.io.ReadInstruction
import pl.pitcer.rammachine.instruction.type.io.WriteInstruction
import pl.pitcer.rammachine.instruction.type.jump.JumpGreaterThanZeroInstruction
import pl.pitcer.rammachine.instruction.type.jump.JumpInstruction
import pl.pitcer.rammachine.instruction.type.jump.JumpZeroInstruction

class InstructionFactory(
	private val ramMachine: RamMachine
) {

	fun createInstruction(line: InstructionLine): Instruction {
		val name = line.name
		val nameLowered = name.toLowerCase()
		return when (nameLowered) {
			"read" -> ReadInstruction(this.ramMachine, line.label, line.argument)
			"write" -> WriteInstruction(this.ramMachine, line.label, line.argument)
			"load" -> LoadInstruction(this.ramMachine, line.label, line.argument)
			"store" -> StoreInstruction(this.ramMachine, line.label, line.argument)
			"add" -> AddInstruction(this.ramMachine, line.label, line.argument)
			"sub" -> SubtractInstruction(this.ramMachine, line.label, line.argument)
			"mult" -> MultiplyInstruction(this.ramMachine, line.label, line.argument)
			"div" -> DivideInstruction(this.ramMachine, line.label, line.argument)
			"halt" -> HaltInstruction(this.ramMachine, line.label, line.argument)
			"jump" -> JumpInstruction(this.ramMachine, line.label, line.argument)
			"jzero" -> JumpZeroInstruction(this.ramMachine, line.label, line.argument)
			"jgtz" -> JumpGreaterThanZeroInstruction(this.ramMachine, line.label, line.argument)
			else -> throw IllegalArgumentException("Unknown instruction name: '$name'")
		}
	}
}
