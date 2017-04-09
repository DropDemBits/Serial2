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
 * Base class for default serial types
 * 
 * @author DropDemBits
 */
public abstract class SEBase implements Serializable {
	
	public static final byte[] DBSIG = "SDB2".getBytes();
	public static final byte[] HDEND = "HDRE".getBytes();
	public static int HEADER_SIZE = DBSIG.length + 4 + Short.BYTES + HDEND.length;
	public static int BASE_SIZE = Short.BYTES + Integer.BYTES;
	
    /**
     * Gets the size of the string when serialized
     * @param str The string to get the size of
     * @return The size of the string when serialized
     */
	public static int getStringSize(String str) {
		return str.getBytes().length+1+Short.BYTES;
	}
    
    /**
     * Parses / deserializes a string from a byte array
     * @param string The bytes from a serialized string
     * @return The parsed / deserialized string
     */
    public static String parseString(byte[] string) {
        ByteArrayParser parser = new ByteArrayParser(string);
        if(parser.getShort() >= string.length) return "";
        
        parser.index = parser.getShort()+2;
        if(parser.getByte() != 0) return "";
        
        parser.index = 0;
        short length = parser.getShort();
        
        return new String(string, 2, length);
    }
	
}
