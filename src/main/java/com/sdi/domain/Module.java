package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Module.
 */
@Entity
@Table(name = "module")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    @JsonIgnoreProperties(value = { "featureDeployements", "module" }, allowSetters = true)
    private Set<Feature> features = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    @JsonIgnoreProperties(value = { "module", "productDeployement", "custoLevel" }, allowSetters = true)
    private Set<ModuleDeployement> moduleDeployements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "modules", "productDeployements", "productline" }, allowSetters = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "modules" }, allowSetters = true)
    private Domaine domaine;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Module id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Module name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Module code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public Module creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public Module updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Module notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(Set<Feature> features) {
        if (this.features != null) {
            this.features.forEach(i -> i.setModule(null));
        }
        if (features != null) {
            features.forEach(i -> i.setModule(this));
        }
        this.features = features;
    }

    public Module features(Set<Feature> features) {
        this.setFeatures(features);
        return this;
    }

    public Module addFeature(Feature feature) {
        this.features.add(feature);
        feature.setModule(this);
        return this;
    }

    public Module removeFeature(Feature feature) {
        this.features.remove(feature);
        feature.setModule(null);
        return this;
    }

    public Set<ModuleDeployement> getModuleDeployements() {
        return this.moduleDeployements;
    }

    public void setModuleDeployements(Set<ModuleDeployement> moduleDeployements) {
        if (this.moduleDeployements != null) {
            this.moduleDeployements.forEach(i -> i.setModule(null));
        }
        if (moduleDeployements != null) {
            moduleDeployements.forEach(i -> i.setModule(this));
        }
        this.moduleDeployements = moduleDeployements;
    }

    public Module moduleDeployements(Set<ModuleDeployement> moduleDeployements) {
        this.setModuleDeployements(moduleDeployements);
        return this;
    }

    public Module addModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.add(moduleDeployement);
        moduleDeployement.setModule(this);
        return this;
    }

    public Module removeModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.remove(moduleDeployement);
        moduleDeployement.setModule(null);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Module product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Domaine getDomaine() {
        return this.domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Module domaine(Domaine domaine) {
        this.setDomaine(domaine);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Module)) {
            return false;
        }
        return getId() != null && getId().equals(((Module) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
