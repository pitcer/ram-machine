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

import pl.pitcer.rammachine.instruction.argument.ArgumentFlag
import pl.pitcer.rammachine.instruction.argument.InstructionArgument

class InstructionLine(
	label: String?,
	val name: String,
	argument: String?
) {

	val label: String?
	val argument: InstructionArgument?

	init {
		this.label = label?.let(this::parseLabel)
		this.argument = argument?.let(this::parseArgument)
	}

	private fun parseLabel(label: String): String {
		return label.removeSuffix(":")
	}

	private fun parseArgument(argument: String): InstructionArgument {
		val flag = getArgumentFlag(argument)
		val prefix = flag.flag
		val argumentValue = if (prefix == null) argument else argument.removePrefix(prefix)
		return InstructionArgument(flag, argumentValue)
	}

	private fun getArgumentFlag(argument: String): ArgumentFlag {
		return ArgumentFlag.values().firstOrNull {
			val flag = it.flag
			flag != null && argument.startsWith(flag)
		} ?: ArgumentFlag.MEMORY_REFERENCE
	}
}
