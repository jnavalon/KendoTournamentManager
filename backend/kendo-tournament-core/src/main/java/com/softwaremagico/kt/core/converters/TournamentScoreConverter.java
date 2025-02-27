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

import com.softwaremagico.kt.core.controller.models.TournamentScoreDTO;
import com.softwaremagico.kt.core.converters.models.TournamentScoreConverterRequest;
import com.softwaremagico.kt.persistence.entities.TournamentScore;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TournamentScoreConverter extends ElementConverter<TournamentScore, TournamentScoreDTO, TournamentScoreConverterRequest> {

    @Override
    public TournamentScoreDTO convert(TournamentScoreConverterRequest from) {
        final TournamentScoreDTO tournamentScoreDTO = new TournamentScoreDTO();
        BeanUtils.copyProperties(from.getEntity(), tournamentScoreDTO);
        return tournamentScoreDTO;
    }

    @Override
    public TournamentScore reverse(TournamentScoreDTO to) {
        if (to == null) {
            return null;
        }
        final TournamentScore tournamentScore = new TournamentScore();
        BeanUtils.copyProperties(tournamentScore, tournamentScore);
        return tournamentScore;
    }
}
