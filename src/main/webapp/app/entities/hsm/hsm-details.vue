<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="hSM">
        <h2 class="jh-entity-heading" data-cy="hSMDetailsHeading"><span v-text="t$('sdiApp.hSM.detail.title')"></span> {{ hSM.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.hSM.name')"></span>
          </dt>
          <dd>
            <span>{{ hSM.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.hSM.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ hSM.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.hSM.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ hSM.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.hSM.notes')"></span>
          </dt>
          <dd>
            <span>{{ hSM.notes }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="hSM.id" :to="{ name: 'HSMEdit', params: { hSMId: hSM.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./hsm-details.component.ts"></script>
