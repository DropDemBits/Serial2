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
package gio.ddb.serial2;

/**
 * Utility for converting data types to bytes and vise versa
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class ByteArrayParser {

    /* Byte array containing the data */
	private byte[] bytes;
    
    /**
     * The current index pointing to the byte array
     */
	public int index;
	
    /**
     * Creates a new ByteArrayParser from another one
     * @param other The other ByteArrayParser to create this from
     */
	public ByteArrayParser(ByteArrayParser other) {
		bytes = other.bytes;
		index = other.index;
	}
	
    /**
     * Creates a new ByteArrayParser from a byte array
     * @param src The byte array containing the data
     */
	public ByteArrayParser(byte[] src) {
		bytes = src;
	}
	
    /**
     * Creates a new ByteArrayParser, setting the size of the byte array based on size
     * @param size The length of the byte array
     */
	public ByteArrayParser(int size) {
		bytes = new byte[size];
	}
	
    /**
     * Skips a number of bytes
     * Will not do anything if the sum of skip is out of bounds
     * @param toSkip The number to skip the amount of bytes by (Can be negative)
     * @return True if successful, false if an error occurred
     */
	public boolean skip(int toSkip) {
		if(toSkip + index < 0) {
            if(Serializable.DEBUG) System.err.println("ERR: Skipping made index less than zero");
            index = 0;
            return false;
        }
        else if(toSkip + index >= bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Skip greater than length");
            index = bytes.length;
            return false;
        }
		else index += toSkip;
        return true;
	}
	
    /**
     * Appends a boolean
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param bool The boolean value to append
     */
	public void addBoolean(boolean bool) {
		if(index+1 >= bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Truncating value");
            return;
        }
		bytes[index++] = (byte) (bool ? 1 : 0);
	}
	
    /**
     * Appends a byte
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param b The byte value to append
     */
	public void addByte(byte b) {
		if(index+1 > bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Truncating value");
            return;
        }
		bytes[index++] = b;
	}
	
    /**
     * Appends a char
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param c The char value to append
     */
	public void addChar(char c) {
		addByte((byte)c);
	}
	
    /**
     * Appends a short
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param s The short value to append
     */
	public void addShort(short s) {
		if(index+2 > bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Truncating value");
            return;
        }
		bytes[index++] = (byte)(s & 0xFF);
		bytes[index++] = (byte)(s >> 8);
	}
	
    /**
     * Appends a integer
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param i The integer value to append
     */
	public void addInt(int i) {
		if(index+Integer.BYTES > bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Truncating value");
            return;
        }
		
		for(int off = 0; off < Integer.BYTES; off++) {
			bytes[index+off] = (byte) ((i >> (off*8)) & 0xFF);
		}
		
		index += Integer.BYTES;
	}
	
    /**
     * Appends a long
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param l The long value to append
     */
	public void addLong(long l) {
		if(index+Long.BYTES > bytes.length) {
            if(Serializable.DEBUG) System.err.println("ERR: Truncating value");
            return;
        }
		
		for(int off = 0; off < Long.BYTES; off++) {
			bytes[index+off] = (byte) ((l >> (off*8)) & 0xFF);
		}
		
		index += Long.BYTES;
	}
	
    /**
     * Appends a float
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param f The float value to append
     */
	public void addFloat(float f) {
		addInt(Float.floatToRawIntBits(f));
	}
	
    /**
     * Appends a double
     * Will not do anything if appending causes the index to be greater than or equal to the length
     * @param d The double value to append
     */
	public void addDouble(double d) {
		addLong(Double.doubleToRawLongBits(d));
	}
	
    /**
     * Gets the boolean at the current index
     * Will not change the current index
     * @return The boolean value at the current index
     */
	public boolean getBoolean() {
		return bytes[index] == 1;
	}
    
    /**
     * Gets the byte at the current index
     * Will not change the current index
     * @return The byte value at the current index
     */
	public byte getByte() {
		return bytes[index];
	}
	
    /**
     * Gets the char at the current index
     * Will not change the current index
     * @return The char value at the current index
     */
	public char getChar() {
		return (char)bytes[index];
	}
	
    /**
     * Gets the short at the current index
     * Will not change the current index
     * @return The short value at the current index
     */
	public short getShort() {
		return (short)(bytes[index] | bytes[index+1] << 8);
	}
	
    /**
     * Gets the integer at the current index
     * Will not change the current index
     * @return The integer value at the current index
     */
	public int getInt() {
		int retval = 0;
		
		for(int off = 0; off < Integer.BYTES; off++) {
			retval |= bytes[index+off] << off*8;
		}
		
		return retval;
	}
	
    /**
     * Gets the long at the current index
     * Will not change the current index
     * @return The long value at the current index
     */
	public long getLong() {
		Long retval = 0L;
		
		for(int off = 0; off < Long.BYTES; off++) {
			retval |= bytes[index+off] << off*8;
		}
		
		return retval;
	}
	
    /**
     * Gets the float at the current index
     * Will not change the current index
     * @return The float value at the current index
     */
	public float getFloat() {
		return Float.intBitsToFloat(getInt());
	}
	
    /**
     * Gets the double at the current index
     * Will not change the current index
     * @return The double value at the current index
     */
	public double getDouble() {
		return Double.longBitsToDouble(getLong());
	}
	
    /**
     * Just returns the current data
     * @return The data in the byte array
     */
	public byte[] toBytes() {
		return bytes;
	}
	
}
