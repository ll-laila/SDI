<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="feature">
        <h2 class="jh-entity-heading" data-cy="featureDetailsHeading">
          <span v-text="t$('sdiApp.feature.detail.title')"></span> {{ feature.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.feature.name')"></span>
          </dt>
          <dd>
            <span>{{ feature.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.code')"></span>
          </dt>
          <dd>
            <span>{{ feature.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.apiVersion')"></span>
          </dt>
          <dd>
            <span>{{ feature.apiVersion }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.description')"></span>
          </dt>
          <dd>
            <span>{{ feature.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.notes')"></span>
          </dt>
          <dd>
            <span>{{ feature.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ feature.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ feature.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.feature.module')"></span>
          </dt>
          <dd>
            <div v-if="feature.module">
              <router-link :to="{ name: 'ModuleView', params: { moduleId: feature.module.id } }">{{ feature.module.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="feature.id" :to="{ name: 'FeatureEdit', params: { featureId: feature.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./feature-details.component.ts"></script>
