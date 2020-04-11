package com.trandung.elearn.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_number")
    private Integer sessionNumber;

    @Column(name = "scores")
    private Integer scores;

    @Column(name = "wrong_answer")
    private Integer wrongAnswer;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Word> words = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public Session sessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
        return this;
    }

    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Integer getScores() {
        return scores;
    }

    public Session scores(Integer scores) {
        this.scores = scores;
        return this;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public Session status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getWrongAnswer() {
        return wrongAnswer;
    }

    public Session wrongAnswer(Integer wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
        return this;
    }

    public void setWrongAnswer(Integer wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    public Set<Word> getWords() {
        return words;
    }

    public Session words(Set<Word> words) {
        this.words = words;
        return this;
    }

    public Session addWord(Word word) {
        this.words.add(word);
        word.setSession(this);
        return this;
    }

    public Session removeWord(Word word) {
        this.words.remove(word);
        word.setSession(null);
        return this;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", sessionNumber=" + getSessionNumber() +
            ", scores=" + getScores() +
            ", wrongAnswer=" + getWrongAnswer() +
            "}";
    }
}
