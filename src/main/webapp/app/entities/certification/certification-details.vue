<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="certification">
        <h2 class="jh-entity-heading" data-cy="certificationDetailsHeading">
          <span v-text="t$('sdiApp.certification.detail.title')"></span> {{ certification.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.certification.name')"></span>
          </dt>
          <dd>
            <span>{{ certification.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.certification.description')"></span>
          </dt>
          <dd>
            <span>{{ certification.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.certification.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ certification.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.certification.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ certification.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.certification.expireDate')"></span>
          </dt>
          <dd>
            <span>{{ certification.expireDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.certification.notes')"></span>
          </dt>
          <dd>
            <span>{{ certification.notes }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="certification.id"
          :to="{ name: 'CertificationEdit', params: { certificationId: certification.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./certification-details.component.ts"></script>
