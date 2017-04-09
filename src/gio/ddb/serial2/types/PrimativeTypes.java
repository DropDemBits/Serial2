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
package gio.ddb.serial2.types;

import gio.ddb.serial2.SEType;
import gio.ddb.serial2.Serializable;

/**
 * Core Primitive Types
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class PrimativeTypes {

	public static enum Primatives {
		BOOLEAN,
		BYTE,
		CHAR,
		SHORT,
		INT,
		LONG,
		FLOAT,
		DOUBLE,
	}
	
	static byte[] toBytesL(long toBytes) {
        byte[] retval = new byte[Long.BYTES];
        for(int off = 0; off < Long.BYTES; off++) {
            retval[off] = (byte)(toBytes >> (off*8) & 0xFF);
        }
        return retval;
	}
	
	static byte[] toBytesI(int toBytes) {
		byte[] retval = new byte[Integer.BYTES];
        for(int off = 0; off < Integer.BYTES; off++) {
            retval[off] = (byte)(toBytes >> (off*8) & 0xFF);
        }
        return retval;
	}
    
    static byte[] toBytes(int toBytes) {
		byte[] intBits = toBytesI(toBytes);
        byte[] retval = new byte[intBits.length + 2];
        System.arraycopy(intBits, 0, retval, 2, intBits.length);
        retval[0] = (byte) SEType.PRIMATIVE.value();
        retval[1] = (byte) Primatives.INT.ordinal();
        return retval;
	}
	
	static byte[] toBytes(long toBytes) {
		byte[] longBits = toBytesL(toBytes);
        byte[] retval = new byte[longBits.length + 2];
        System.arraycopy(longBits, 0, retval, 2, longBits.length);
        retval[0] = (byte) SEType.PRIMATIVE.value();
        retval[1] = (byte) Primatives.LONG.ordinal();
        return retval;
	}
    
    static byte[] toBytes(float toBytes) {
		byte[] floatBits = toBytesI(Float.floatToRawIntBits(toBytes));
        byte[] retval = new byte[floatBits.length + 2];
        System.arraycopy(floatBits, 0, retval, 2, floatBits.length);
        retval[0] = (byte) SEType.PRIMATIVE.value();
        retval[1] = (byte) Primatives.FLOAT.ordinal();
        return retval;
	}
	
	static byte[] toBytes(double toBytes) {
		byte[] doubleBits = toBytesL(Double.doubleToRawLongBits(toBytes));
        byte[] retval = new byte[doubleBits.length + 2];
        System.arraycopy(doubleBits, 0, retval, 2, doubleBits.length);
        retval[0] = (byte) SEType.PRIMATIVE.value();
        retval[1] = (byte) Primatives.DOUBLE.ordinal();
        return retval;
	}
	
	public static class BooleanValue implements Serializable {

		public final boolean value;

		public BooleanValue(boolean value) {
			this.value = value;
		}
        
        public BooleanValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.BOOLEAN.ordinal()) {
                this.value = value[2] != 0;
            } else this.value = false;
        }

		@Override
		public int getSize() {
			return 3;
		}

		@Override
		public byte[] getSerialized() {
			return new byte[] { (byte) SEType.PRIMATIVE.value(), (byte) Primatives.BOOLEAN.ordinal(),  !value ? (byte) 0 : (byte) 1 };
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.BOOLEAN.ordinal() << 8);
        }

	}
	
	public static class ByteValue implements Serializable {

		public final byte value;

		public ByteValue(byte value) {
			this.value = value;
		}
        
        public ByteValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.BYTE.ordinal()) {
                this.value = value[2];
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return 3;
		}

		@Override
		public byte[] getSerialized() {
			return new byte[] { (byte) SEType.PRIMATIVE.value(), (byte) Primatives.BYTE.ordinal(), value };
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.BYTE.ordinal() << 8);
        }

	}
	
	public static class ShortValue implements Serializable {

		public final short value;

		public ShortValue(short value) {
			this.value = value;
		}
        
        public ShortValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.SHORT.ordinal()) {
                this.value = (short) (Byte.toUnsignedInt(value[2]) | (Byte.toUnsignedInt(value[3]) << 8));
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Short.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return new byte[] { (byte) SEType.PRIMATIVE.value(), (byte) Primatives.SHORT.ordinal(), (byte) (value & 0xFF), (byte) (value >> 8) };
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.SHORT.ordinal() << 8);
        }

	}
	
	public static class CharValue implements Serializable {

		public final char value;

		public CharValue(char value) {
			this.value = value;
		}
        
        public CharValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.CHAR.ordinal()) {
                this.value = (char) (value[2] | value[3] << 8);
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Character.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return new byte[] { (byte) SEType.PRIMATIVE.value(), (byte) Primatives.CHAR.ordinal(), (byte) (value & 0xFF), (byte) (value >> 8) };
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.CHAR.ordinal() << 8);
        }

	}
	
	public static class IntValue implements Serializable {

		public final int value;

		public IntValue(int value) {
			this.value = value;
		}
        
        public IntValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.INT.ordinal()) {
                int final_val = 0;
                for(int i = 0; i < Integer.BYTES; i++) {
                    final_val |= Byte.toUnsignedInt(value[i+2]) << (i*8);
                }
                this.value = final_val;
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Integer.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return PrimativeTypes.toBytes(value);
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.INT.ordinal() << 8);
        }

	}
	
	public static class LongValue implements Serializable {

		public final long value;

		public LongValue(long value) {
			this.value = value;
		}
        
        public LongValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.LONG.ordinal()) {
                long final_val = 0;
                for(int i = 0; i < Long.BYTES; i++) {
                    final_val |= Byte.toUnsignedLong(value[i+2]) << (i*8);
                }
                this.value = final_val;
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Long.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return PrimativeTypes.toBytes(value);
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.LONG.ordinal() << 8);
        }

	}
	
	public static class FloatValue implements Serializable {

		public final float value;

		public FloatValue(float value) {
			this.value = value;
		}
        
        public FloatValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.FLOAT.ordinal()) {
                int final_val = 0;
                for(int i = 0; i < Float.BYTES; i++) {
                    final_val |= Byte.toUnsignedInt(value[i+2]) << (i*8);
                }
                this.value = Float.intBitsToFloat(final_val);
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Float.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return PrimativeTypes.toBytes(value);
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.FLOAT.ordinal() << 8);
        }

	}
	
	public static class DoubleValue implements Serializable {

		public final double value;

		public DoubleValue(double value) {
			this.value = value;
		}
        
        public DoubleValue(byte[] value) {
            if(value[0] == SEType.PRIMATIVE.value() && value[1] == Primatives.DOUBLE.ordinal()) {
                long final_val = 0;
                for(int i = 0; i < Double.BYTES; i++) {
                    final_val |= Byte.toUnsignedLong(value[i+2]) << (i*8);
                }
                this.value = Double.longBitsToDouble(final_val);
            } else this.value = 0;
        }

		@Override
		public int getSize() {
			return Double.BYTES+2;
		}

		@Override
		public byte[] getSerialized() {
			return PrimativeTypes.toBytes(value);
		}

        @Override
        public short getType() {
            return (short) (SEType.PRIMATIVE.value() | Primatives.DOUBLE.ordinal() << 8);
        }

	}

}
