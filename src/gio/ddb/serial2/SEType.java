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
 * Enums For base serial types
 * @author DropDemBits
 */
public enum SEType {

	UNKNOWN,
	BLOCK,
	LIST,
	PRIMATIVE,
	ROOT_BLOCK,
    STRING;
	
    /**
     * Gets the ordinal value
     * 
     * @return The ordinal value
     */
	public short value() {
		return (short)this.ordinal();
	}
	
}
