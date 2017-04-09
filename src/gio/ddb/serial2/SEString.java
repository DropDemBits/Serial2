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

import java.util.Arrays;

/**
 * String primitive
 * @author DropDemBits <r3usrlnd@gmail.com>
 */
public class SEString extends SEBase {

    /**
     * The String value that this contains
     */
    public String value = "";
    
    /* Not accessable anywhere else */
    private SEString() {}
    
    /**
     * Creates a new SEString from a string
     * @param value The string that this will contain
     */
    SEString(String value) {
        this.value = value;
    }
    
    /**
     * Creates a new SEString from a byte array
     * @param src The array to parse
     */
    SEString(byte[] src) {
        if(src[0] != (byte)SEType.STRING.value()) return;
        value = SEBase.parseString(Arrays.copyOfRange(src, 6, src.length));
    }
    
    @Override
    public int getSize() {
        return SEBase.BASE_SIZE + SEBase.getStringSize(value);
    }

    @Override
    public byte[] getSerialized() {
        ByteArrayParser dest = new ByteArrayParser(getSize());
        
        //Common Part (Type & Size)
        dest.addShort(SEType.STRING.value());
        dest.addInt(getSize());
        
        //Data (String)
        dest.addShort((short)value.getBytes().length);
        for(int i = 0; i < value.length(); i++) {
            dest.addByte(value.getBytes()[i]);
        }
        dest.addByte((byte)0);
        
        return dest.toBytes();
    }

    @Override
    public short getType() {
        return SEType.STRING.value();
    }
    
}
