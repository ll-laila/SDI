<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="oS">
        <h2 class="jh-entity-heading" data-cy="oSDetailsHeading"><span v-text="t$('sdiApp.oS.detail.title')"></span> {{ oS.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.oS.name')"></span>
          </dt>
          <dd>
            <span>{{ oS.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.oS.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ oS.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.oS.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ oS.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.oS.notes')"></span>
          </dt>
          <dd>
            <span>{{ oS.notes }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="oS.id" :to="{ name: 'OSEdit', params: { oSId: oS.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./os-details.component.ts"></script>
