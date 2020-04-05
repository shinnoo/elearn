package com.trandung.elearn.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Word.
 */
@Entity
@Table(name = "word")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "definition")
    private String definition;

    @Column(name = "pronounce")
    private String pronounce;

    @Column(name = "form")
    private String form;

    @Column(name = "example")
    private String example;

    @Column(name = "collocations")
    private String collocations;

    @Column(name = "synonym")
    private String synonym;

    @ManyToOne
    @JsonIgnoreProperties("words")
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Word name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public Word definition(String definition) {
        this.definition = definition;
        return this;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPronounce() {
        return pronounce;
    }

    public Word pronounce(String pronounce) {
        this.pronounce = pronounce;
        return this;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getForm() {
        return form;
    }

    public Word form(String form) {
        this.form = form;
        return this;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getExample() {
        return example;
    }

    public Word example(String example) {
        this.example = example;
        return this;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getCollocations() {
        return collocations;
    }

    public Word collocations(String collocations) {
        this.collocations = collocations;
        return this;
    }

    public void setCollocations(String collocations) {
        this.collocations = collocations;
    }

    public String getSynonym() {
        return synonym;
    }

    public Word synonym(String synonym) {
        this.synonym = synonym;
        return this;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public Session getSession() {
        return session;
    }

    public Word session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Word)) {
            return false;
        }
        return id != null && id.equals(((Word) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Word{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", definition='" + getDefinition() + "'" +
            ", pronounce='" + getPronounce() + "'" +
            ", form='" + getForm() + "'" +
            ", example='" + getExample() + "'" +
            ", collocations='" + getCollocations() + "'" +
            ", synonym='" + getSynonym() + "'" +
            "}";
    }
}
