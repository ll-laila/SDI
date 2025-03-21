package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

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

    @Column(name = "logo")
    private String logo;

    @Column(name = "notes")
    private String notes;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnoreProperties(value = { "features", "moduleDeployements", "product", "domaine" }, allowSetters = true)
    private Set<Module> modules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonIgnoreProperties(
        value = { "moduleDeployements", "client", "product", "deployementType", "ha", "hsm", "db", "host", "applicationServer", "os" },
        allowSetters = true
    )
    private Set<ProductDeployement> productDeployements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private ProductLine productline;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return this.logo;
    }

    public Product logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNotes() {
        return this.notes;
    }

    public Product notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public Product creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public Product updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Set<Module> getModules() {
        return this.modules;
    }

    public void setModules(Set<Module> modules) {
        if (this.modules != null) {
            this.modules.forEach(i -> i.setProduct(null));
        }
        if (modules != null) {
            modules.forEach(i -> i.setProduct(this));
        }
        this.modules = modules;
    }

    public Product modules(Set<Module> modules) {
        this.setModules(modules);
        return this;
    }

    public Product addModule(Module module) {
        this.modules.add(module);
        module.setProduct(this);
        return this;
    }

    public Product removeModule(Module module) {
        this.modules.remove(module);
        module.setProduct(null);
        return this;
    }

    public Set<ProductDeployement> getProductDeployements() {
        return this.productDeployements;
    }

    public void setProductDeployements(Set<ProductDeployement> productDeployements) {
        if (this.productDeployements != null) {
            this.productDeployements.forEach(i -> i.setProduct(null));
        }
        if (productDeployements != null) {
            productDeployements.forEach(i -> i.setProduct(this));
        }
        this.productDeployements = productDeployements;
    }

    public Product productDeployements(Set<ProductDeployement> productDeployements) {
        this.setProductDeployements(productDeployements);
        return this;
    }

    public Product addProductDeployement(ProductDeployement productDeployement) {
        this.productDeployements.add(productDeployement);
        productDeployement.setProduct(this);
        return this;
    }

    public Product removeProductDeployement(ProductDeployement productDeployement) {
        this.productDeployements.remove(productDeployement);
        productDeployement.setProduct(null);
        return this;
    }

    public ProductLine getProductline() {
        return this.productline;
    }

    public void setProductline(ProductLine productLine) {
        this.productline = productLine;
    }

    public Product productline(ProductLine productLine) {
        this.setProductline(productLine);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", logo='" + getLogo() + "'" +
            ", notes='" + getNotes() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
