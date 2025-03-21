package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A CustomisationLevel.
 */
@Entity
@Table(name = "customisation_level")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CustomisationLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "level")
    private String level;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "custoLevel")
    @JsonIgnoreProperties(value = { "module", "productDeployement", "custoLevel" }, allowSetters = true)
    private Set<ModuleDeployement> moduleDeployements = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "custoLevel")
    @JsonIgnoreProperties(value = { "feature", "custoLevel" }, allowSetters = true)
    private Set<FeatureDeployement> featureDeployements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CustomisationLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return this.level;
    }

    public CustomisationLevel level(String level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public CustomisationLevel creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public CustomisationLevel updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public CustomisationLevel notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<ModuleDeployement> getModuleDeployements() {
        return this.moduleDeployements;
    }

    public void setModuleDeployements(Set<ModuleDeployement> moduleDeployements) {
        if (this.moduleDeployements != null) {
            this.moduleDeployements.forEach(i -> i.setCustoLevel(null));
        }
        if (moduleDeployements != null) {
            moduleDeployements.forEach(i -> i.setCustoLevel(this));
        }
        this.moduleDeployements = moduleDeployements;
    }

    public CustomisationLevel moduleDeployements(Set<ModuleDeployement> moduleDeployements) {
        this.setModuleDeployements(moduleDeployements);
        return this;
    }

    public CustomisationLevel addModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.add(moduleDeployement);
        moduleDeployement.setCustoLevel(this);
        return this;
    }

    public CustomisationLevel removeModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.remove(moduleDeployement);
        moduleDeployement.setCustoLevel(null);
        return this;
    }

    public Set<FeatureDeployement> getFeatureDeployements() {
        return this.featureDeployements;
    }

    public void setFeatureDeployements(Set<FeatureDeployement> featureDeployements) {
        if (this.featureDeployements != null) {
            this.featureDeployements.forEach(i -> i.setCustoLevel(null));
        }
        if (featureDeployements != null) {
            featureDeployements.forEach(i -> i.setCustoLevel(this));
        }
        this.featureDeployements = featureDeployements;
    }

    public CustomisationLevel featureDeployements(Set<FeatureDeployement> featureDeployements) {
        this.setFeatureDeployements(featureDeployements);
        return this;
    }

    public CustomisationLevel addFeatureDeployement(FeatureDeployement featureDeployement) {
        this.featureDeployements.add(featureDeployement);
        featureDeployement.setCustoLevel(this);
        return this;
    }

    public CustomisationLevel removeFeatureDeployement(FeatureDeployement featureDeployement) {
        this.featureDeployements.remove(featureDeployement);
        featureDeployement.setCustoLevel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomisationLevel)) {
            return false;
        }
        return getId() != null && getId().equals(((CustomisationLevel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomisationLevel{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
