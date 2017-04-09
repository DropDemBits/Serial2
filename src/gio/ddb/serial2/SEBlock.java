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
import gio.ddb.serial2.types.PrimativeTypes.Primatives;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Block for the base of all DB structures
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class SEBlock extends SEBase {
	
    public static final byte[] FILE_FOOTER = "THIS IS THE END!".getBytes();
	/**
     * Calculated size (modified with each recalculateSize() call)
     */
    private int calcSize = 0;
    /**
     * The next offset into the string table
     */
    private int currentOffset = 0;
    /**
     * The amount of times getSerialized() is called. Used to prevent recursive serializations
     */
    private int serializationAccesses = 0;
    /**
     * Whether this block has a parent. Used to determine if the is the root block or not
     */
	protected boolean hasParent = false;
    /**
     * The map of values
     */
	protected Map<String, Serializable> valueMap;
    /**
     * The map of offsets into the string table
     */
    private final Map<String, Integer> offsetMap;

    /**
     * Creates a new SEBlock
     */
    public SEBlock() {
        this.valueMap = new LinkedHashMap<>();
        this.offsetMap = new LinkedHashMap<>();
    }
    
    /**
     * Creates a new SEBlock from a serialized version
     * @param src The array to get the data from
     */
    public SEBlock(byte[] src) {
        this.valueMap = new LinkedHashMap<>();
        this.offsetMap = new LinkedHashMap<>();
        deserialize(src);
    }
    
    /**
     * Adds an SEBlock
     * Will not add if an attempt is made to add an SEBlock that is the current SEBlock
     * @param name The name to map the block to
     * @param block The block to add
     */
	public void setValue(String name, SEBlock block) {
		if(block == this) return;
		block.hasParent = true;
		valueMap.put(name, block);
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
        recalculateSize();
	}
    
    /**
     * Adds an SEList
     * @param name The name to map the list to
     * @param list The list to add
     */
    public void setValue(String name, SEList list) {
		valueMap.put(name, list);
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
        recalculateSize();
	}
	
    /**
     * Adds a boolean
     * @param name The name to map the value to
     * @param value The value to add
     */
    public void setValue(String name, boolean value) {
		valueMap.put(name, new PrimativeTypes.BooleanValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a boolean
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, byte value) {
		valueMap.put(name, new PrimativeTypes.ByteValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a short
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, short value) {
		valueMap.put(name, new PrimativeTypes.ShortValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a char
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, char value) {
		valueMap.put(name, new PrimativeTypes.CharValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds an integer
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, int value) {
		valueMap.put(name, new PrimativeTypes.IntValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a long
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, long value) {
		valueMap.put(name, new PrimativeTypes.LongValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a float
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, float value) {
		valueMap.put(name, new PrimativeTypes.FloatValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
	
    /**
     * Adds a double
     * @param name The name to map the value to
     * @param value The value to add
     */
	public void setValue(String name, double value) {
		valueMap.put(name, new PrimativeTypes.DoubleValue(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
    
    /**
     * Adds a string
     * @param name The name to map the value to
     * @param value The value to add
     */
    public void setValue(String name, String value) {
		valueMap.put(name, new SEString(value));
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
	}
    
    /**
     * Adds a generic Serializable object
     * @param name The name to map the value to
     * @param value The value to add
     */
    public void setValue(String name, Serializable value) {
        if(value == null || name.isEmpty()) return;
        if(value.getClass() == SEBlock.class) setValue(name, (SEBlock)value);
        valueMap.put(name, value);
        offsetMap.put(name, currentOffset);
        currentOffset += SEBase.getStringSize(name);
		recalculateSize();
    }
	
    /**
     * Gets a boolean from a string
     * @param name The name to get the value from
     * @return A boolean value, or false if none was found
     */
	public boolean getBoolean(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.BooleanValue.class)
			return false;
		else
			return ((PrimativeTypes.BooleanValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a byte from a string
     * @param name The name to get the value from
     * @return A byte value, or false if none was found
     */
	public byte getByte(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.ByteValue.class)
			return (byte)0;
		else
			return ((PrimativeTypes.ByteValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a char from a string
     * @param name The name to get the value from
     * @return A char value, or false if none was found
     */
	public char getChar(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.CharValue.class)
			return '\0';
		else
			return ((PrimativeTypes.CharValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a short from a string
     * @param name The name to get the value from
     * @return A short value, or false if none was found
     */
	public short getShort(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.ShortValue.class)
			return (short)0;
		else
			return ((PrimativeTypes.ShortValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets an integer from a string
     * @param name The name to get the value from
     * @return An integer value, or false if none was found
     */
	public int getInt(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.IntValue.class)
			return 0;
		else
			return ((PrimativeTypes.IntValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a long from a string
     * @param name The name to get the value from
     * @return A long value, or false if none was found
     */
	public long getLong(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.LongValue.class)
			return 0L;
		else
			return ((PrimativeTypes.LongValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a float from a string
     * @param name The name to get the value from
     * @return A float value, or false if none was found
     */
	public float getFloat(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.FloatValue.class)
			return 0f;
		else
			return ((PrimativeTypes.FloatValue) valueMap.get(name)).value;
	}
	
    /**
     * Gets a double from a string
     * @param name The name to get the value from
     * @return A double value, or false if none was found
     */
	public double getDouble(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != PrimativeTypes.DoubleValue.class)
			return 0d;
		else
			return ((PrimativeTypes.DoubleValue) valueMap.get(name)).value;
	}
    
    /**
     * Gets a string from a string
     * @param name The name to get the value from
     * @return A string value, or an empty string if none was found
     */
    public String getString(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != SEString.class)
			return "";
		else
			return ((SEString) valueMap.get(name)).value;
	}
    
    /**
     * Gets an SEBlock from a string
     * @param name The name to get the value from
     * @return An SEBlock value, or null if none was found
     */
	public SEBlock getBlock(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != SEBlock.class)
			return null;
		else
			return (SEBlock) valueMap.get(name);
	}
    
    /**
     * Gets an SEList from a string
     * @param name The name to get the value from
     * @return An SEList value, or null if none was found
     */
    public SEList getList(String name) {
		if(valueMap.get(name) == null || valueMap.get(name).getClass() != SEList.class)
			return null;
		else
			return (SEList) valueMap.get(name);
	}
	
	@Override
	public short getType() {
		return hasParent ? SEType.BLOCK.value() : SEType.ROOT_BLOCK.value();
	}
    
    /**
     * Recalculates calcSize
     */
	protected void recalculateSize() {
		calcSize = SEBase.BASE_SIZE + Integer.BYTES;
		valueMap.forEach(new BiConsumer<String, Serializable>() {
            @Override
			public void accept(String name, Serializable value) {
				calcSize += SEBase.getStringSize(name) + value.getSize() + Integer.BYTES;
			}
		});
	}
	
	@Override
	public int getSize() {
        if(calcSize == 0) recalculateSize();
		return calcSize + (hasParent ? 0 : SEBase.HEADER_SIZE + FILE_FOOTER.length);
	}

	@Override
	public byte[] getSerialized() {
		serializationAccesses++;
        if(serializationAccesses > 1) return new byte[0];
		ByteArrayParser dest = new ByteArrayParser(getSize());
		int headerSize = 0;
		int stringSize = 0;
			
		for(String str : valueMap.keySet()) {
			stringSize += SEBase.getStringSize(str);
		}
        
		if(getType() == SEType.ROOT_BLOCK.value()) {
			
			//Signature
			for(int i = 0; i < SEBase.DBSIG.length; i++)
				dest.addByte(SEBase.DBSIG[i]);
			
			//Compression check
			dest.skip(4);
			
            //Flags (None yet)
            dest.addShort((short)0);
            
			//Header end
			for(int i = 0; i < SEBase.HDEND.length; i++)
				dest.addByte(SEBase.HDEND[i]);
		}
		
		//Type
		dest.addShort(getType());
		
		//Size of data
		dest.addInt(getSize() - headerSize - (Short.BYTES + Integer.BYTES));
		
        //Skip strings
		dest.addInt(stringSize + dest.index);
		
		for(String str : valueMap.keySet()) {
			byte[] bytes = str.getBytes();
			dest.addShort((short)bytes.length);
			
            for(short i = 0; i < bytes.length; i++) {
			   dest.addByte(bytes[i]);
			}
			
			dest.addByte((byte)0);
		}
        
		//Begin data
		for(Map.Entry<String, Serializable> entry : valueMap.entrySet()) {
            byte[] bytes = entry.getValue().getSerialized();
            
            dest.addInt(offsetMap.get(entry.getKey()));
            for(int i = 0; i < bytes.length; i++)
                dest.addByte(bytes[i]);
        }
        
        for(int i = 0; i < FILE_FOOTER.length && !hasParent; i++) {
            dest.addByte(FILE_FOOTER[i]);
        }
        
        serializationAccesses--;
		return dest.toBytes();
	}
    
    /**
     * Deserializes an SEBlock from an array
     * @param barray The array to deserialize
     */
    private void deserialize(byte[] barray) {
        ByteArrayParser src = new ByteArrayParser(barray);
        boolean isHeader = false;
        
        for(int i = 0; i < DBSIG.length; i++) {
            if(src.getByte() == DBSIG[i]) {
                src.skip(1);
                isHeader = true;
            } else isHeader = false;
        }
        
        if(isHeader) {
            if(src.getInt() != 0) return;
            src.skip(4);
            
            //Flags
            src.skip(Short.BYTES);
            
            for(int i = 0; i < HDEND.length; i++) {
                if(src.getByte() == HDEND[i]) {
                    src.skip(1);
                } else {
                    src.index = 0;
                    break;
                }
            }
            
            int oldIndex = src.index;
            
            src.index = barray.length - FILE_FOOTER.length;
            for(int i = 0; i < FILE_FOOTER.length; i++) {
                if(src.getByte() == FILE_FOOTER[i]) {
                    if(!src.skip(1) && i < FILE_FOOTER.length-1) {
                        if(Serializable.DEBUG) System.err.println("ERR: Truncated DB");
                        return;
                    }
                } else {
                    if(Serializable.DEBUG) System.err.println("ERR: Invalid DB");
                    return;
                }
            }
            src.index = oldIndex;
        } else hasParent = true;
        
        if(src.getShort() != getType()) {
            System.out.println(src.getShort());
            return;
        }
        
        //Baseline Size
        src.skip(Short.BYTES);
        
        int size = src.getInt();
        src.skip(Integer.BYTES);
        
        //String skip
        int stringsStart = src.index + 4;
        src.index = src.getInt();
        src.skip(4);
        int stringsLength = src.index;
        
        //Deserialize Data
        while(src.index < barray.length - (hasParent ? 0 : FILE_FOOTER.length)) {
            String key = SEBase.parseString(Arrays.copyOfRange(src.toBytes(), src.getInt()+stringsStart, stringsLength));
            src.skip(4);
            
            if(key.isEmpty()) {
                src.skip(src.getInt());
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
                setValue(key, new SEBlock(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else if(type == SEType.LIST.value()) {
                setValue(key, new SEList(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else if((type & 0xFF) == SEType.PRIMATIVE.value()) {
                switch(Primatives.values()[type >> 8]) {
                    case BOOLEAN: setValue(key, new PrimativeTypes.BooleanValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Byte.BYTES))); src.skip(Byte.BYTES+2); break;
                    case BYTE: setValue(key, new PrimativeTypes.ByteValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Byte.BYTES))); src.skip(Byte.BYTES+2); break;
                    case SHORT: setValue(key, new PrimativeTypes.ShortValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Short.BYTES))); src.skip(Short.BYTES+2); break;
                    case CHAR: setValue(key, new PrimativeTypes.CharValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Character.BYTES))); src.skip(Character.BYTES+2); break;
                    case INT: setValue(key, new PrimativeTypes.IntValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Integer.BYTES))); src.skip(Integer.BYTES+2); break;
                    case FLOAT: setValue(key, new PrimativeTypes.FloatValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Float.BYTES))); src.skip(Float.BYTES+2); break;
                    case LONG: setValue(key, new PrimativeTypes.LongValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Long.BYTES))); src.skip(Long.BYTES+2); break;
                    case DOUBLE: setValue(key, new PrimativeTypes.DoubleValue(Arrays.copyOfRange(src.toBytes(), src.index, src.index+2+Double.BYTES))); src.skip(Double.BYTES+2); break;
                }
            } else if(type == SEType.STRING.value()) {
                setValue(key, new SEString(Arrays.copyOfRange(src.toBytes(), src.index, src.index+serialSize)));
                src.skip(serialSize);
            } else {
                //Just skip
                src.skip(serialSize);
            }
        }
    }

}
