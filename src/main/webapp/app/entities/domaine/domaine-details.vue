<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="domaine">
        <h2 class="jh-entity-heading" data-cy="domaineDetailsHeading">
          <span v-text="t$('sdiApp.domaine.detail.title')"></span> {{ domaine.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.domaine.name')"></span>
          </dt>
          <dd>
            <span>{{ domaine.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.domaine.code')"></span>
          </dt>
          <dd>
            <span>{{ domaine.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.domaine.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ domaine.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.domaine.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ domaine.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.domaine.notes')"></span>
          </dt>
          <dd>
            <span>{{ domaine.notes }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="domaine.id" :to="{ name: 'DomaineEdit', params: { domaineId: domaine.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./domaine-details.component.ts"></script>
