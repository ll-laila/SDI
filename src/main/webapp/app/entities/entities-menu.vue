<template>
  <div>
    <b-dropdown-item to="/deployement-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.deployementType')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/customisation-level">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.customisationLevel')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.product')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product-line">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.productLine')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/module">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.module')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/domaine">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.domaine')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/feature">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.feature')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product-deployement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.productDeployement')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/module-deployement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.moduleDeployement')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/feature-deployement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.featureDeployement')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/region">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.region')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-certification">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.clientCertification')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-size">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.clientSize')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.client')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.clientType')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/certification">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.certification')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-event">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.clientEvent')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/client-event-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.clientEventType')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.country')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/ha">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.ha')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/db">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.db')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/host">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.host')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/os">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.os')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/hsm">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.hsm')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/application-server">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.applicationServer')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/action-request">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.actionRequest')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
