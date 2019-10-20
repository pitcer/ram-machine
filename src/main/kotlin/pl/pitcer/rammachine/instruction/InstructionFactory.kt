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

	fun createInstruction(instructionLine: InstructionLine): Instruction {
		val name = instructionLine.name
		val nameLowered = name.toLowerCase()
		return when (nameLowered) {
			"read" -> ReadInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"write" -> WriteInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"load" -> LoadInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"store" -> StoreInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"add" -> AddInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"sub" -> SubtractInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"mult" -> MultiplyInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"div" -> DivideInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"halt" -> HaltInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"jump" -> JumpInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"jzero" -> JumpZeroInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			"jgtz" -> JumpGreaterThanZeroInstruction(this.ramMachine, instructionLine.label, instructionLine.argument)
			else -> throw IllegalArgumentException("Unknown instruction name: '$name'")
		}
	}
}
