package com.softwaremagico.kt.core.providers;

/*-
 * #%L
 * Kendo Tournament Manager (Core)
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

import com.softwaremagico.kt.core.exceptions.DuplicatedUserException;
import com.softwaremagico.kt.persistence.entities.AuthenticatedUser;
import com.softwaremagico.kt.persistence.repositories.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthenticatedUserProvider {
    private final AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    public AuthenticatedUserProvider(AuthenticatedUserRepository authenticatedUserRepository) {
        this.authenticatedUserRepository = authenticatedUserRepository;
    }


    public Optional<AuthenticatedUser> findByUsername(String username) {
        return authenticatedUserRepository.findByUsername(username);
    }

    public Optional<AuthenticatedUser> findByUniqueId(String uniqueId) {
        return findByUsername(uniqueId);
    }

    public AuthenticatedUser createUser(String username, String fullName, String password) {
        if (findByUsername(username).isPresent()) {
            throw new DuplicatedUserException(this.getClass(), "Username exists!");
        }

        final AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUsername(username);
        authenticatedUser.setFullName(fullName);
        authenticatedUser.setPassword(password);

        return createUser(authenticatedUser);
    }

    public AuthenticatedUser createUser(AuthenticatedUser authenticatedUser) {
        return authenticatedUserRepository.save(authenticatedUser);
    }

    public List<AuthenticatedUser> findAll() {
        return authenticatedUserRepository.findAll();
    }

    public void delete(AuthenticatedUser authenticatedUser) {
        authenticatedUserRepository.delete(authenticatedUser);
    }

}
