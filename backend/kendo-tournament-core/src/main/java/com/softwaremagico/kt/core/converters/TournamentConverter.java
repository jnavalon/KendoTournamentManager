package com.softwaremagico.kt.core.converters;

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

import com.softwaremagico.kt.core.controller.models.TournamentDTO;
import com.softwaremagico.kt.core.converters.models.TournamentConverterRequest;
import com.softwaremagico.kt.core.converters.models.TournamentScoreConverterRequest;
import com.softwaremagico.kt.persistence.entities.Tournament;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TournamentConverter extends ElementConverter<Tournament, TournamentDTO, TournamentConverterRequest> {
    private final TournamentScoreConverter tournamentScoreConverter;

    @Autowired
    public TournamentConverter(TournamentScoreConverter tournamentScoreConverter) {
        this.tournamentScoreConverter = tournamentScoreConverter;
    }


    @Override
    public TournamentDTO convert(TournamentConverterRequest from) {
        final TournamentDTO tournamentDTO = new TournamentDTO();
        BeanUtils.copyProperties(from.getEntity(), tournamentDTO);
        tournamentDTO.setTournamentScoreDTO(tournamentScoreConverter.convert(
                new TournamentScoreConverterRequest(from.getEntity().getTournamentScore())));
        return tournamentDTO;
    }

    @Override
    public Tournament reverse(TournamentDTO to) {
        if (to == null) {
            return null;
        }
        final Tournament tournament = new Tournament();
        tournament.setTournamentScore(tournamentScoreConverter.reverse(to.getTournamentScoreDTO()));
        BeanUtils.copyProperties(tournament, tournament);
        return tournament;
    }
}
