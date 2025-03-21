package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ClientEvent.
 */
@Entity
@Table(name = "client_event")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "event", nullable = false)
    private String event;

    @Column(name = "description")
    private String description;

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
    @JsonIgnoreProperties(value = { "clientEvents" }, allowSetters = true)
    private ClientEventType clientEventType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return this.event;
    }

    public ClientEvent event(String event) {
        this.setEvent(event);
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return this.description;
    }

    public ClientEvent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public ClientEvent creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public ClientEvent updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public ClientEvent notes(String notes) {
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

    public ClientEvent client(Client client) {
        this.setClient(client);
        return this;
    }

    public ClientEventType getClientEventType() {
        return this.clientEventType;
    }

    public void setClientEventType(ClientEventType clientEventType) {
        this.clientEventType = clientEventType;
    }

    public ClientEvent clientEventType(ClientEventType clientEventType) {
        this.setClientEventType(clientEventType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientEvent)) {
            return false;
        }
        return getId() != null && getId().equals(((ClientEvent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientEvent{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", description='" + getDescription() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
