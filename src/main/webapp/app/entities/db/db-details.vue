<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dB">
        <h2 class="jh-entity-heading" data-cy="dBDetailsHeading"><span v-text="t$('sdiApp.dB.detail.title')"></span> {{ dB.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.dB.name')"></span>
          </dt>
          <dd>
            <span>{{ dB.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.dB.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ dB.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.dB.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ dB.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.dB.notes')"></span>
          </dt>
          <dd>
            <span>{{ dB.notes }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="dB.id" :to="{ name: 'DBEdit', params: { dBId: dB.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./db-details.component.ts"></script>
