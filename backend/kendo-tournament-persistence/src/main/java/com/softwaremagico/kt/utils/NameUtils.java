package com.softwaremagico.kt.utils;

/*-
 * #%L
 * Kendo Tournament Manager (Persistence)
 * %%
 * Copyright (C) 2021 - 2022 Softwaremagico
 * %%
 * This software is designed by Jorge Hortelano Otero. Jorge Hortelano Otero
 * <softwaremagico@gmail.com> Valencia (Spain).
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.softwaremagico.kt.persistence.entities.Participant;

public class NameUtils {
    private static final int MAX_NAME_LENGTH = 11;
    private static final int MAX_SHORT_NAME_LENGTH = 8;

    public static String getLastnameName(Participant participant) {
        if (participant == null) {
            return " --- --- ";
        }
        return getLastnameName(participant.getLastname(), participant.getName());
    }

    public static String getLastnameName(String lastname, String name) {
        if (lastname.length() > 0 || name.length() > 0) {
            return lastname + ", " + name;
        } else {
            return " --- --- ";
        }
    }

    /**
     * Get an automatic abbreviation of the lastname with the initial letter of
     * the name.
     *
     * @return
     */
    public String getLastnameNameIni(String lastname, String name) {
        return getLastnameNameIni(lastname, name, MAX_NAME_LENGTH);
    }


    /**
     * Get an automatic abbreviation of the lastname with the initial letter or
     * complete name.
     *
     * @param maxLength
     * @param lastname
     * @param name
     * @return
     */
    public static String getLastnameNameIni(String lastname, String name, int maxLength) {
        if (lastname.length() > 0 || name.length() > 0) {
            // Short lastname.
            String lastnameShort = lastname.substring(0, Math.min(maxLength, lastname.length())).toUpperCase();
            if (lastname.length() > maxLength) {
                lastnameShort = lastnameShort.trim() + ".";
            }
            // Short lastname
            if (lastnameShort.length() < maxLength) {
                final String nameShort = name.substring(0, Math.min(maxLength - lastnameShort.length() + 1, name.length()));
                // Name cut.
                if (nameShort.length() < name.length()) {
                    return lastnameShort.trim() + ", " + nameShort.trim() + ".";
                } else {
                    // Full name.
                    return lastnameShort.trim() + ", " + nameShort.trim();
                }
            } else {
                return lastnameShort.trim() + ", " + name.substring(0, 1) + ".";
            }
        } else {
            return " --- --- ";
        }
    }

    /**
     * Get an automatic abbreviation of the lastname of the person.
     *
     * @param length
     * @return
     */
    public static String getShortLastname(String lastname, int length) {
        final String[] shortLastname = lastname.split(" ");
        if (shortLastname[0].length() > 3) {
            return shortLastname[0].substring(0, Math.min(length, shortLastname[0].length()));
        } else {
            return lastname.substring(0, Math.min(length - 1, lastname.length()));
        }
    }

    /**
     * Get an automatic abbreviation of the lastname of the person.
     *
     * @return
     */
    public static String getShortLastname(String lastname) {
        return getShortLastname(lastname, MAX_SHORT_NAME_LENGTH);
    }

    /**
     * Get an automatic abbreviation of the name with the lastname of the person.
     *
     * @return
     */
    public static String getShortLastnameName(String lastname, String name, int maxLength) {
        if (name.length() + lastname.length() == 0) {
            return "";
        }

        final float rateLastname = (name.length() + getShortLastname(lastname, 20).length()) / (float) lastname.length();
        final float rateName = (name.length() + getShortName(name, 20).length()) / (float) name.length();
        final String ret = getShortLastname(lastname, (int) (maxLength / rateLastname)).trim() + ", " + getShortName(name, (int) (maxLength / rateName));
        return ret.trim();
    }


    /**
     * Get an automatic abbreviation of the name of the person.
     *
     * @param length
     * @return
     */
    public static String getShortName(String name, int length) {
        return name.substring(0, Math.min(length, name.length()));
    }

    /**
     * Get an automatic abbreviation of the name of the person.
     *
     * @return
     */
    public static String getShortName(String name) {
        return getShortName(name, MAX_SHORT_NAME_LENGTH);
    }


    public String getAcronym(String lastname, String name) {
        String acronym = "";
        acronym += name.trim().substring(0, 1).toUpperCase();
        final String[] shortLastname = lastname.trim().split(" ");
        if (shortLastname[0].length() < 4 && shortLastname.length > 1) {
            acronym += shortLastname[0].substring(0, 1).toLowerCase();
            acronym += shortLastname[1].substring(0, 1).toUpperCase();
        } else {
            acronym += shortLastname[0].substring(0, 1).toUpperCase();
        }
        return acronym;
    }

}
