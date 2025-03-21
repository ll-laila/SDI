package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Feature.
 */
@Entity
@Table(name = "feature")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "api_version")
    private String apiVersion;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feature")
    @JsonIgnoreProperties(value = { "feature", "custoLevel" }, allowSetters = true)
    private Set<FeatureDeployement> featureDeployements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "features", "moduleDeployements", "product", "domaine" }, allowSetters = true)
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Feature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Feature name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Feature code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }

    public Feature apiVersion(String apiVersion) {
        this.setApiVersion(apiVersion);
        return this;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getDescription() {
        return this.description;
    }

    public Feature description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return this.notes;
    }

    public Feature notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public Feature creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public Feature updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Set<FeatureDeployement> getFeatureDeployements() {
        return this.featureDeployements;
    }

    public void setFeatureDeployements(Set<FeatureDeployement> featureDeployements) {
        if (this.featureDeployements != null) {
            this.featureDeployements.forEach(i -> i.setFeature(null));
        }
        if (featureDeployements != null) {
            featureDeployements.forEach(i -> i.setFeature(this));
        }
        this.featureDeployements = featureDeployements;
    }

    public Feature featureDeployements(Set<FeatureDeployement> featureDeployements) {
        this.setFeatureDeployements(featureDeployements);
        return this;
    }

    public Feature addFeatureDeployement(FeatureDeployement featureDeployement) {
        this.featureDeployements.add(featureDeployement);
        featureDeployement.setFeature(this);
        return this;
    }

    public Feature removeFeatureDeployement(FeatureDeployement featureDeployement) {
        this.featureDeployements.remove(featureDeployement);
        featureDeployement.setFeature(null);
        return this;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Feature module(Module module) {
        this.setModule(module);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feature)) {
            return false;
        }
        return getId() != null && getId().equals(((Feature) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feature{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", apiVersion='" + getApiVersion() + "'" +
            ", description='" + getDescription() + "'" +
            ", notes='" + getNotes() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
