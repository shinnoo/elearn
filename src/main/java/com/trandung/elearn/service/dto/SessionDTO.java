package com.trandung.elearn.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.trandung.elearn.domain.Session} entity.
 */
public class SessionDTO implements Serializable {

    private Long id;

    private Integer sessionNumber;

    private Integer scores;

    private Integer wrongAnswer;

    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(Integer sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public Integer getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(Integer wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (sessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", sessionNumber=" + getSessionNumber() +
            ", scores=" + getScores() +
            ", wrongAnswer=" + getWrongAnswer() +
            "}";
    }
}
