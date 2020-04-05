package com.trandung.elearn.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.trandung.elearn.domain.Word} entity.
 */
public class WordDTO implements Serializable {

    private Long id;

    private String name;

    private String definition;

    private String pronounce;

    private String form;

    private String example;

    private String collocations;

    private String synonym;


    private Long sessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getCollocations() {
        return collocations;
    }

    public void setCollocations(String collocations) {
        this.collocations = collocations;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WordDTO wordDTO = (WordDTO) o;
        if (wordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WordDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", definition='" + getDefinition() + "'" +
            ", pronounce='" + getPronounce() + "'" +
            ", form='" + getForm() + "'" +
            ", example='" + getExample() + "'" +
            ", collocations='" + getCollocations() + "'" +
            ", synonym='" + getSynonym() + "'" +
            ", session=" + getSessionId() +
            "}";
    }
}
