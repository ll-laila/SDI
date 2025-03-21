<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="moduleDeployement">
        <h2 class="jh-entity-heading" data-cy="moduleDeployementDetailsHeading">
          <span v-text="t$('sdiApp.moduleDeployement.detail.title')"></span> {{ moduleDeployement.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.customisationDescription')"></span>
          </dt>
          <dd>
            <span>{{ moduleDeployement.customisationDescription }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ moduleDeployement.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ moduleDeployement.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.notes')"></span>
          </dt>
          <dd>
            <span>{{ moduleDeployement.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.module')"></span>
          </dt>
          <dd>
            <div v-if="moduleDeployement.module">
              <router-link :to="{ name: 'ModuleView', params: { moduleId: moduleDeployement.module.id } }">{{
                moduleDeployement.module.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.productDeployement')"></span>
          </dt>
          <dd>
            <div v-if="moduleDeployement.productDeployement">
              <router-link
                :to="{ name: 'ProductDeployementView', params: { productDeployementId: moduleDeployement.productDeployement.id } }"
                >{{ moduleDeployement.productDeployement.refContract }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.moduleDeployement.custoLevel')"></span>
          </dt>
          <dd>
            <div v-if="moduleDeployement.custoLevel">
              <router-link :to="{ name: 'CustomisationLevelView', params: { customisationLevelId: moduleDeployement.custoLevel.id } }">{{
                moduleDeployement.custoLevel.level
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="moduleDeployement.id"
          :to="{ name: 'ModuleDeployementEdit', params: { moduleDeployementId: moduleDeployement.id } }"
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

<script lang="ts" src="./module-deployement-details.component.ts"></script>
