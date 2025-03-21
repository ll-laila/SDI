package com.sdi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductDeployement.
 */
@Entity
@Table(name = "product_deployement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDeployement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "ref_contract")
    private String refContract;

    @Column(name = "start_deployement_date")
    private LocalDate startDeployementDate;

    @Column(name = "end_deployement_date")
    private LocalDate endDeployementDate;

    @Column(name = "crea_date")
    private LocalDate creaDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productDeployement")
    @JsonIgnoreProperties(value = { "module", "productDeployement", "custoLevel" }, allowSetters = true)
    private Set<ModuleDeployement> moduleDeployements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements", "country", "size", "clientType", "certifs" }, allowSetters = true)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "modules", "productDeployements", "productline" }, allowSetters = true)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private DeployementType deployementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private HA ha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private HSM hsm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private DB db;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private HOST host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private ApplicationServer applicationServer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productDeployements" }, allowSetters = true)
    private OS os;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductDeployement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ProductDeployement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefContract() {
        return this.refContract;
    }

    public ProductDeployement refContract(String refContract) {
        this.setRefContract(refContract);
        return this;
    }

    public void setRefContract(String refContract) {
        this.refContract = refContract;
    }

    public LocalDate getStartDeployementDate() {
        return this.startDeployementDate;
    }

    public ProductDeployement startDeployementDate(LocalDate startDeployementDate) {
        this.setStartDeployementDate(startDeployementDate);
        return this;
    }

    public void setStartDeployementDate(LocalDate startDeployementDate) {
        this.startDeployementDate = startDeployementDate;
    }

    public LocalDate getEndDeployementDate() {
        return this.endDeployementDate;
    }

    public ProductDeployement endDeployementDate(LocalDate endDeployementDate) {
        this.setEndDeployementDate(endDeployementDate);
        return this;
    }

    public void setEndDeployementDate(LocalDate endDeployementDate) {
        this.endDeployementDate = endDeployementDate;
    }

    public LocalDate getCreaDate() {
        return this.creaDate;
    }

    public ProductDeployement creaDate(LocalDate creaDate) {
        this.setCreaDate(creaDate);
        return this;
    }

    public void setCreaDate(LocalDate creaDate) {
        this.creaDate = creaDate;
    }

    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    public ProductDeployement updateDate(LocalDate updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public ProductDeployement notes(String notes) {
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
            this.moduleDeployements.forEach(i -> i.setProductDeployement(null));
        }
        if (moduleDeployements != null) {
            moduleDeployements.forEach(i -> i.setProductDeployement(this));
        }
        this.moduleDeployements = moduleDeployements;
    }

    public ProductDeployement moduleDeployements(Set<ModuleDeployement> moduleDeployements) {
        this.setModuleDeployements(moduleDeployements);
        return this;
    }

    public ProductDeployement addModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.add(moduleDeployement);
        moduleDeployement.setProductDeployement(this);
        return this;
    }

    public ProductDeployement removeModuleDeployement(ModuleDeployement moduleDeployement) {
        this.moduleDeployements.remove(moduleDeployement);
        moduleDeployement.setProductDeployement(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ProductDeployement client(Client client) {
        this.setClient(client);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDeployement product(Product product) {
        this.setProduct(product);
        return this;
    }

    public DeployementType getDeployementType() {
        return this.deployementType;
    }

    public void setDeployementType(DeployementType deployementType) {
        this.deployementType = deployementType;
    }

    public ProductDeployement deployementType(DeployementType deployementType) {
        this.setDeployementType(deployementType);
        return this;
    }

    public HA getHa() {
        return this.ha;
    }

    public void setHa(HA hA) {
        this.ha = hA;
    }

    public ProductDeployement ha(HA hA) {
        this.setHa(hA);
        return this;
    }

    public HSM getHsm() {
        return this.hsm;
    }

    public void setHsm(HSM hSM) {
        this.hsm = hSM;
    }

    public ProductDeployement hsm(HSM hSM) {
        this.setHsm(hSM);
        return this;
    }

    public DB getDb() {
        return this.db;
    }

    public void setDb(DB dB) {
        this.db = dB;
    }

    public ProductDeployement db(DB dB) {
        this.setDb(dB);
        return this;
    }

    public HOST getHost() {
        return this.host;
    }

    public void setHost(HOST hOST) {
        this.host = hOST;
    }

    public ProductDeployement host(HOST hOST) {
        this.setHost(hOST);
        return this;
    }

    public ApplicationServer getApplicationServer() {
        return this.applicationServer;
    }

    public void setApplicationServer(ApplicationServer applicationServer) {
        this.applicationServer = applicationServer;
    }

    public ProductDeployement applicationServer(ApplicationServer applicationServer) {
        this.setApplicationServer(applicationServer);
        return this;
    }

    public OS getOs() {
        return this.os;
    }

    public void setOs(OS oS) {
        this.os = oS;
    }

    public ProductDeployement os(OS oS) {
        this.setOs(oS);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDeployement)) {
            return false;
        }
        return getId() != null && getId().equals(((ProductDeployement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDeployement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", refContract='" + getRefContract() + "'" +
            ", startDeployementDate='" + getStartDeployementDate() + "'" +
            ", endDeployementDate='" + getEndDeployementDate() + "'" +
            ", creaDate='" + getCreaDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
