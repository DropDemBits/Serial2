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

import gio.ddb.serial2.types.PrimativeTypes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * List for iterative accesses 
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class SEList extends SEBase {

    /**
     * The current calculated size. Changes with each call to recalculateSize()
     */
    private int calcSize = 0;
    /**
     * The array of values
     */
    protected final List<Serializable> valueMap;
	
    /**
     * Creates a new SEList
     */
    public SEList() {
        valueMap = new ArrayList<>();
    }
    
    /**
     * Creates a new SEList from a byte array
     * @param src The byte array to deserialize a SEList from
     */
    SEList(byte[] src) {
        valueMap = new ArrayList<>();
        deserialize(src);
    }
    
    /**
     * Sets a boolean value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, boolean value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.BooleanValue(value));
        else valueMap.set(index, new PrimativeTypes.BooleanValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a byte value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, byte value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.ByteValue(value));
        else valueMap.set(index, new PrimativeTypes.ByteValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a short value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, short value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.ShortValue(value));
        else valueMap.set(index, new PrimativeTypes.ShortValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a char value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, char value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.CharValue(value));
        else valueMap.set(index, new PrimativeTypes.CharValue(value));
		recalculateSize();
	}
	
    /**
     * Sets an integer value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, int value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.IntValue(value));
        else valueMap.set(index, new PrimativeTypes.IntValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a long value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, long value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.LongValue(value));
        else valueMap.set(index, new PrimativeTypes.LongValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a float value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, float value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.FloatValue(value));
        else valueMap.set(index, new PrimativeTypes.FloatValue(value));
		recalculateSize();
	}
	
    /**
     * Sets a double value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
	public void setValue(Integer index, double value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new PrimativeTypes.DoubleValue(value));
        else valueMap.set(index, new PrimativeTypes.DoubleValue(value));
		recalculateSize();
	}
    
    /**
     * Sets a string value at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
    public void setValue(Integer index, String value) {
        if(index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, new SEString(value));
        else valueMap.set(index, new SEString(value));
		recalculateSize();
    }
	
    /**
     * Sets an SEBlock at the current index
     * @param index The index to set the value to
     * @param block The block to set
     */
    public void setValue(Integer index, SEBlock block) {
		if(index == null || index < 0) return;
		block.hasParent = true;
		if(valueMap.get(index) == null) valueMap.add(index, block);
        else valueMap.set(index, block);
	}
    
    /**
     * Sets an SEList at the current index
     * Will not do anything if adding a list that is equal to the current list
     * @param index The index to set the value to
     * @param list The list to set
     */
    public void setValue(Integer index, SEList list) {
        if(list == this || index == null || index < 0) return;
		if(valueMap.get(index) == null) valueMap.add(index, list);
        else valueMap.set(index, list);
	}
    
    /**
     * Sets a generic Serializable object at the current index
     * @param index The index to set the value to
     * @param value The value to set
     */
    public void setValue(Integer index, Serializable value) {
        if(value == null || index == null || index < 0) return;
        if(value.getClass() == SEBlock.class) setValue(index, (SEBlock)value);
        if(valueMap.get(index) == null) valueMap.add(index, value);
        else valueMap.set(index, value);
		recalculateSize();
    }
	
    /**
     * Gets the block at the specified index
     * @param index The index to get the block from
     * @return A block, or null if nothing was at the index
     */
	public SEBlock getBlock(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != SEBlock.class)
			return null;
		else
			return (SEBlock) valueMap.get(index);
	}
    
    /**
     * Gets the list at the specified index
     * @param index The index to get the list from
     * @return A list, or null if nothing was at the index
     */
    public SEList getList(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != SEList.class)
			return null;
		else
			return (SEList) valueMap.get(index);
	}
    
    /**
     * Gets the boolean at the specified index
     * @param index The index to get the boolean from
     * @return A boolean, or false if nothing was at the index
     */
	public boolean getBoolean(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.BooleanValue.class)
			return false;
		else
			return ((PrimativeTypes.BooleanValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the byte at the specified index
     * @param index The index to get the byte from
     * @return A byte, or zero if nothing was at the index
     */
	public byte getByte(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.ByteValue.class)
			return (byte)0;
		else
			return ((PrimativeTypes.ByteValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the char at the specified index
     * @param index The index to get the char from
     * @return A char, or '\0' (NULL) if nothing was at the index
     */
	public char getChar(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.CharValue.class)
			return '\0';
		else
			return ((PrimativeTypes.CharValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the short at the specified index
     * @param index The index to get the short from
     * @return A short, or zero if nothing was at the index
     */
	public short getShort(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.ShortValue.class)
			return (short)0;
		else
			return ((PrimativeTypes.ShortValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the integer at the specified index
     * @param index The index to get the integer from
     * @return An integer, or zero if nothing was at the index
     */
	public int getInt(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.IntValue.class)
			return 0;
		else
			return ((PrimativeTypes.IntValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the block at the specified index
     * @param index The index to get the long from
     * @return A long, or zero if nothing was at the index
     */
	public long getLong(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.LongValue.class)
			return 0L;
		else
			return ((PrimativeTypes.LongValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the float at the specified index
     * @param index The index to get the float from
     * @return A float, or zero if nothing was at the index
     */
	public float getFloat(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.FloatValue.class)
			return 0f;
		else
			return ((PrimativeTypes.FloatValue) valueMap.get(index)).value;
	}
	
    /**
     * Gets the double at the specified index
     * @param index The index to get the double from
     * @return A double, or zero if nothing was at the index
     */
	public double getDouble(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != PrimativeTypes.DoubleValue.class)
			return 0d;
		else
			return ((PrimativeTypes.DoubleValue) valueMap.get(index)).value;
	}
    
    /**
     * Gets the string at the specified index
     * @param index The index to get the string from
     * @return A string, or an empty string if nothing was at the index
     */
    public String getString(Integer index) {
		if(index == null || index < 0 || index > valueMap.size() || valueMap.get(index) == null || valueMap.get(index).getClass() != SEString.class)
			return "";
		else
			return ((SEString) valueMap.get(index)).value;
	}
    
    @Override
    public short getType() {
        return SEType.LIST.value();
    }
    
    /**
     * Recalculates calcSize
     */
    protected void recalculateSize() {
        calcSize = SEBase.BASE_SIZE + Integer.BYTES;
		valueMap.forEach(new Consumer<Serializable>() {
            @Override
			public void accept(Serializable value) {
				calcSize += value.getSize() + Short.BYTES;
			};
		});
    }
    
    @Override
    public int getSize() {
        if(calcSize == 0) recalculateSize();
        return calcSize;
    }

    @Override
    public byte[] getSerialized() {
        ByteArrayParser dest = new ByteArrayParser(getSize());
        //Type
        dest.addShort(getType());
        
        //Size
        dest.addInt(getSize());
        
        //Length
        dest.addInt(valueMap.size());
        
        for(int i = 0; i < valueMap.size(); i++) {
            if(valueMap.get(i) == null) {
                dest.addByte((byte)0);
                continue;
            }
            byte[] bytes = ((Serializable) valueMap.get(i)).getSerialized();
            
            //Index (Replaces name)
            dest.addShort((short)i);
            
            for(int j = 0; j < bytes.length; j++)
                dest.addByte(bytes[j]);
        }
        
        return dest.toBytes();
    }
    
    private void deserialize(byte[] barray) {
        ByteArrayParser src = new ByteArrayParser(barray);
        
        if(src.getShort() != SEType.LIST.value()) return;
        src.skip(2);
        
        //int size = src.getInt();
        src.skip(4);
        
        int length = src.getInt();
        src.skip(4);
        
        for(int i = 0; i < length; i++) {
            //Don't bother with index
            src.skip(2);
            
            if(src.getByte() == 0) {
                src.skip(1);
                continue;
            }
            
            //Do thing
            short type = src.getShort();
            src.skip(2);
            int serialSize = src.getInt();
            src.skip(-2);
            
            if(type == SEType.ROOT_BLOCK.value()) {
                //Malformed? Just die anyways
                return;
            } else if(type == SEType.BLOCK.value()) {
                setValue(i, new SEBlock(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else if(type == SEType.LIST.value()) {
                setValue(i, new SEList(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else if((type & 0xFF) == SEType.PRIMATIVE.value()) {
                switch(PrimativeTypes.Primatives.values()[type >> 8]) {
                    case BOOLEAN: setValue(i, new PrimativeTypes.BooleanValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Byte.BYTES))); src.skip(Byte.BYTES+2); break;
                    case BYTE: setValue(i, new PrimativeTypes.ByteValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Byte.BYTES))); src.skip(Byte.BYTES+2); break;
                    case SHORT: setValue(i, new PrimativeTypes.ShortValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Short.BYTES))); src.skip(Short.BYTES+2); break;
                    case CHAR: setValue(i, new PrimativeTypes.CharValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Character.BYTES))); src.skip(Character.BYTES+2); break;
                    case INT: setValue(i, new PrimativeTypes.IntValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Integer.BYTES))); src.skip(Integer.BYTES+2); break;
                    case FLOAT: setValue(i, new PrimativeTypes.FloatValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Float.BYTES))); src.skip(Float.BYTES+2); break;
                    case LONG: setValue(i, new PrimativeTypes.LongValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Long.BYTES))); src.skip(Long.BYTES+2); break;
                    case DOUBLE: setValue(i, new PrimativeTypes.DoubleValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Double.BYTES))); src.skip(Double.BYTES+2); break;
                }
            } else if(type == SEType.STRING.value()) {
                setValue(i, new SEString(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else {
                //Just skip
                src.skip(serialSize);
            }
        }
    }
    
}
