<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="client">
        <h2 class="jh-entity-heading" data-cy="clientDetailsHeading">
          <span v-text="t$('sdiApp.client.detail.title')"></span> {{ client.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.client.clientLogo')"></span>
          </dt>
          <dd>
            <span>{{ client.clientLogo }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.name')"></span>
          </dt>
          <dd>
            <span>{{ client.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.code')"></span>
          </dt>
          <dd>
            <span>{{ client.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.mainContactName')"></span>
          </dt>
          <dd>
            <span>{{ client.mainContactName }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.mainContactEmail')"></span>
          </dt>
          <dd>
            <span>{{ client.mainContactEmail }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.currentCardHolderNumber')"></span>
          </dt>
          <dd>
            <span>{{ client.currentCardHolderNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.currentBruncheNumber')"></span>
          </dt>
          <dd>
            <span>{{ client.currentBruncheNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.currentCustomersNumber')"></span>
          </dt>
          <dd>
            <span>{{ client.currentCustomersNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.mainContactPhoneNumber')"></span>
          </dt>
          <dd>
            <span>{{ client.mainContactPhoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.url')"></span>
          </dt>
          <dd>
            <span>{{ client.url }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ client.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ client.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.notes')"></span>
          </dt>
          <dd>
            <span>{{ client.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.country')"></span>
          </dt>
          <dd>
            <div v-if="client.country">
              <router-link :to="{ name: 'CountryView', params: { countryId: client.country.id } }">{{
                client.country.countryname
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.size')"></span>
          </dt>
          <dd>
            <div v-if="client.size">
              <router-link :to="{ name: 'ClientSizeView', params: { clientSizeId: client.size.id } }">{{
                client.size.sizeCode
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.client.clientType')"></span>
          </dt>
          <dd>
            <div v-if="client.clientType">
              <router-link :to="{ name: 'ClientTypeView', params: { clientTypeId: client.clientType.id } }">{{
                client.clientType.type
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="client.id" :to="{ name: 'ClientEdit', params: { clientId: client.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./client-details.component.ts"></script>
