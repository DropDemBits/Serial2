/*
 * Copyright (C) 2017 DropDemBits <r3usrlnd@gmail.com>
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sandbox;

import java.io.FileOutputStream;

import gio.ddb.serial2.SEBlock;
import gio.ddb.serial2.SEList;
import gio.ddb.serial2.SEString;

/**
 * Generic testing class
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class TMain {

	public static void printArray(byte[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(Integer.toHexString(Byte.toUnsignedInt(array[i])) + " ");
			if(i % 16 == 0 && i != 0) System.out.print('\n');
		}
	}
	
	public static void main(String[] args) {
		SEBlock block = new SEBlock();
		block.setValue("Recursion", block);
		block.setValue("Boolean", true);
		block.setValue("Byte", (byte)1);
		block.setValue("Short", (short)2);
		block.setValue("Char", 'A');
		block.setValue("Integer", 0xBEEFBEEF);
		block.setValue("Long", 0x7EDCBA9876543210L);
		block.setValue("Float", 3.1415926535897932384626433832795f);
		block.setValue("Double", 3.1415926535897932384626433832795d);
        SEList list = new SEList();
        list.setValue(0, true);
		list.setValue(1, (byte)0xFF);
		list.setValue(2, (short)0xFFFF);
		list.setValue(3, (char)0xFFFF);
		list.setValue(4, 0xFFFFFFFF);
		list.setValue(5, 0xFFFFFFFFFFFFFFFFL);
		list.setValue(6, Float.intBitsToFloat(0xFFFFFFFF));
		list.setValue(7, Math.PI*2d);
        list.setValue(6, 3.1415926535897932384626433832795f);
		list.setValue(7, 3.1415926535897932384626433832795d);
		block.setValue("Crash", list);
        
        SEBlock des = new SEBlock(block.getSerialized());
		System.out.println(des.getList("Crash").getDouble(7));
		
        try {
			FileOutputStream out = new FileOutputStream("test0.sdb2");
			out.write(block.getSerialized());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
