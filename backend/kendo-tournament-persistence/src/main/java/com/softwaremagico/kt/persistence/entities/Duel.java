package com.softwaremagico.kt.persistence.entities;

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

import com.softwaremagico.kt.persistence.values.Score;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "duels")
public class Duel extends Element {
    private static final int POINTS_TO_WIN = 2;

    @ManyToOne
    @JoinColumn(name = "competitor1")
    private Participant competitor1;

    @ManyToOne
    @JoinColumn(name = "competitor2")
    private Participant competitor2;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "competitor_1_score")
    @Fetch(value = FetchMode.SUBSELECT)
    @Enumerated(EnumType.STRING)
    private List<Score> competitor1Score = new ArrayList<>(); // M, K, T, D, H, I

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "competitor_2_score")
    @Fetch(value = FetchMode.SUBSELECT)
    @Enumerated(EnumType.STRING)
    private List<Score> competitor2Score = new ArrayList<>(); // M, K, T, D, H, I

    @Column(name = "competitor_1_fault")
    private Boolean competitor1Fault = false;

    @Column(name = "competitor_2_fault")
    private Boolean competitor2Fault = false;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DuelType type;

    public Duel() {
        super();
        setType(DuelType.STANDARD);
    }

    public Duel(Participant competitor1, Participant competitor2) {
        this();
        setCompetitor1(competitor1);
        setCompetitor2(competitor2);
    }

    public Participant getCompetitor1() {
        return competitor1;
    }

    public void setCompetitor1(Participant competitor1) {
        this.competitor1 = competitor1;
    }

    public Participant getCompetitor2() {
        return competitor2;
    }

    public void setCompetitor2(Participant competitor2) {
        this.competitor2 = competitor2;
    }

    public void setCompetitor1Score(List<Score> competitor1Score) {
        this.competitor1Score = competitor1Score;
    }

    public void addCompetitor1Score(Score score) {
        if (this.competitor1Score == null) {
            this.competitor1Score = new ArrayList<>();
        }
        this.competitor1Score.add(score);
    }

    public void setCompetitor2Score(List<Score> competitor2Score) {
        this.competitor2Score = competitor2Score;
    }

    public void addCompetitor2Score(Score score) {
        if (this.competitor2Score == null) {
            this.competitor2Score = new ArrayList<>();
        }
        this.competitor2Score.add(score);
    }

    public Boolean getCompetitor1Fault() {
        return competitor1Fault;
    }

    public void setCompetitor1Fault(Boolean competitor1Fault) {
        this.competitor1Fault = competitor1Fault;
    }

    public Boolean getCompetitor2Fault() {
        return competitor2Fault;
    }

    public void setCompetitor2Fault(Boolean competitor2Fault) {
        this.competitor2Fault = competitor2Fault;
    }

    /**
     * Count the rounds and the score of each player to know if the duels is
     * over or not.
     *
     * @return true if the round is over.
     */
    public boolean isOver() {
        return getCompetitor1Score() >= POINTS_TO_WIN || getCompetitor2Score() >= POINTS_TO_WIN;
    }

    /**
     * Gets the winner of the duel.
     *
     * @return -1 if player of first team, 0 if draw, 1 if player of second
     * tiem.
     */
    public int getWinner() {
        return Integer.compare(getCompetitor2Score(), getCompetitor1Score());
    }

    public Integer getCompetitor1Score() {
        return (int) competitor1Score.stream().filter(Score::isValidPoint).count();
    }

    public Integer getCompetitor2Score() {
        return (int) competitor2Score.stream().filter(Score::isValidPoint).count();
    }

    public DuelType getType() {
        return type;
    }

    public void setType(DuelType type) {
        this.type = type;
    }
}
