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
import org.springframework.stereotype.Service;
import org.testng.Assert;

@Service
public class UserFactory {
    public static final String DEFAULT_USER_FIRSTNAME = "Clarke";
    public static final String DEFAULT_USER_LASTNAME = "Griffin";
    public static final String DEFAULT_USER_ID_CARD = "11111111A";

    public Participant createDefaultUser() {
        final Participant participant = new Participant(DEFAULT_USER_ID_CARD, DEFAULT_USER_FIRSTNAME, DEFAULT_USER_LASTNAME);
        return participant;
    }

    public void checkDefaultUser(Participant participant) {
        Assert.assertEquals(participant.getIdCard(), DEFAULT_USER_ID_CARD);
        Assert.assertEquals(participant.getLastname(), DEFAULT_USER_LASTNAME);
        Assert.assertEquals(participant.getName(), DEFAULT_USER_FIRSTNAME);

    }
}
