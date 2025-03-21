package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FeatureDeployement.
 */
@Entity
@Table(name = "feature_deployement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeatureDeployement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "customisation_description")
    private String customisationDescription;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "featureDeployements", "module" }, allowSetters = true)
    private Feature feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "moduleDeployements", "featureDeployements" }, allowSetters = true)
    private CustomisationLevel custoLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeatureDeployement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public FeatureDeployement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCustomisationDescription() {
        return this.customisationDescription;
    }

    public FeatureDeployement customisationDescription(String customisationDescription) {
        this.setCustomisationDescription(customisationDescription);
        return this;
    }

    public void setCustomisationDescription(String customisationDescription) {
        this.customisationDescription = customisationDescription;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public FeatureDeployement creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public FeatureDeployement updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public FeatureDeployement notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Feature getFeature() {
        return this.feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public FeatureDeployement feature(Feature feature) {
        this.setFeature(feature);
        return this;
    }

    public CustomisationLevel getCustoLevel() {
        return this.custoLevel;
    }

    public void setCustoLevel(CustomisationLevel customisationLevel) {
        this.custoLevel = customisationLevel;
    }

    public FeatureDeployement custoLevel(CustomisationLevel customisationLevel) {
        this.setCustoLevel(customisationLevel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeatureDeployement)) {
            return false;
        }
        return getId() != null && getId().equals(((FeatureDeployement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeatureDeployement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", customisationDescription='" + getCustomisationDescription() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
