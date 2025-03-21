package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ClientCertification.
 */
@Entity
@Table(name = "client_certification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientCertification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "certification")
    private String certification;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements", "country", "size", "clientType", "certifs" }, allowSetters = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "clientCertifications" }, allowSetters = true)
    private Certification certif;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientCertification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertification() {
        return this.certification;
    }

    public ClientCertification certification(String certification) {
        this.setCertification(certification);
        return this;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public LocalDate getCertificationDate() {
        return this.certificationDate;
    }

    public ClientCertification certificationDate(LocalDate certificationDate) {
        this.setCertificationDate(certificationDate);
        return this;
    }

    public void setCertificationDate(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public ClientCertification creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public ClientCertification updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public ClientCertification notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientCertification client(Client client) {
        this.setClient(client);
        return this;
    }

    public Certification getCertif() {
        return this.certif;
    }

    public void setCertif(Certification certification) {
        this.certif = certification;
    }

    public ClientCertification certif(Certification certification) {
        this.setCertif(certification);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientCertification)) {
            return false;
        }
        return getId() != null && getId().equals(((ClientCertification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientCertification{" +
            "id=" + getId() +
            ", certification='" + getCertification() + "'" +
            ", certificationDate='" + getCertificationDate() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
