/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.github.pedrovgs.androidgameboyemulator.core.processor.isa;

import com.github.pedrovgs.androidgameboyemulator.InstructionTest;
import com.github.pedrovgs.androidgameboyemulator.core.processor.Register;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class Load8BitImmPCPlusSPIntoHLTest extends InstructionTest {

  @Test public void shouldIncrementProgramCounter() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    Instruction instruction = new Load8BitImmPCPlusSPIntoHL(z80, mmu);

    instruction.execute();

    assertEquals(ANY_16BIT_REGISTER_VALUE + 1, z80.getProgramCounter());
  }

  @Test public void shouldUse3CyclesAsLastInstructionExecutionTime() {
    Instruction instruction = new Load8BitImmPCPlusSPIntoHL(z80, mmu);

    instruction.execute();

    assertEquals(3, z80.getLastInstructionExecutionTime());
  }

  @Test public void shouldLoadTheContentOfTheMemoryPointedByPCPlusTheSPIntoTheRegisterHL() {
    z80.setProgramCounter(ANY_16BIT_REGISTER_VALUE);
    when(mmu.readByte(ANY_16BIT_REGISTER_VALUE)).thenReturn(ANY_MEMORY_BYTE_VALUE);
    z80.setStackPointer(ANY_STACK_POINTER_VALUE);
    Instruction instruction = new Load8BitImmPCPlusSPIntoHL(z80, mmu);

    instruction.execute();

    int expectedValue = ANY_STACK_POINTER_VALUE + ANY_MEMORY_BYTE_VALUE;
    assertEquals(expectedValue, z80.get16BitRegisterValue(Register.HL));
  }

  @Test public void shouldSetZFlagToZero() {
    Instruction instruction = new Load8BitImmPCPlusSPIntoHL(z80, mmu);

    instruction.execute();

    assertEquals(false, z80.isFlagZEnabled());
  }

  @Test public void shouldSetFlagNToZero() {
    Instruction instruction = new Load8BitImmPCPlusSPIntoHL(z80, mmu);

    instruction.execute();

    assertEquals(false, z80.isFlagNEnabled());
  }
}
