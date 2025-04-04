package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A HSM.
 */
@Entity
@Table(name = "hsm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HSM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hsm")
    @JsonIgnoreProperties(
        value = { "moduleDeployements", "client", "product", "deployementType", "ha", "hsm", "db", "host", "applicationServer", "os" },
        allowSetters = true
    )
    private Set<ProductDeployement> productDeployements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HSM id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public HSM name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public HSM creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public HSM updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public HSM notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<ProductDeployement> getProductDeployements() {
        return this.productDeployements;
    }

    public void setProductDeployements(Set<ProductDeployement> productDeployements) {
        if (this.productDeployements != null) {
            this.productDeployements.forEach(i -> i.setHsm(null));
        }
        if (productDeployements != null) {
            productDeployements.forEach(i -> i.setHsm(this));
        }
        this.productDeployements = productDeployements;
    }

    public HSM productDeployements(Set<ProductDeployement> productDeployements) {
        this.setProductDeployements(productDeployements);
        return this;
    }

    public HSM addProductDeployement(ProductDeployement productDeployement) {
        this.productDeployements.add(productDeployement);
        productDeployement.setHsm(this);
        return this;
    }

    public HSM removeProductDeployement(ProductDeployement productDeployement) {
        this.productDeployements.remove(productDeployement);
        productDeployement.setHsm(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HSM)) {
            return false;
        }
        return getId() != null && getId().equals(((HSM) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HSM{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
