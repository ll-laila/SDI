<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="productDeployement">
        <h2 class="jh-entity-heading" data-cy="productDeployementDetailsHeading">
          <span v-text="t$('sdiApp.productDeployement.detail.title')"></span> {{ productDeployement.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.productDeployement.code')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.refContract')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.refContract }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.startDeployementDate')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.startDeployementDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.endDeployementDate')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.endDeployementDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.notes')"></span>
          </dt>
          <dd>
            <span>{{ productDeployement.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.client')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.client">
              <router-link :to="{ name: 'ClientView', params: { clientId: productDeployement.client.id } }">{{
                productDeployement.client.code
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.product')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.product">
              <router-link :to="{ name: 'ProductView', params: { productId: productDeployement.product.id } }">{{
                productDeployement.product.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.deployementType')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.deployementType">
              <router-link :to="{ name: 'DeployementTypeView', params: { deployementTypeId: productDeployement.deployementType.id } }">{{
                productDeployement.deployementType.type
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.ha')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.ha">
              <router-link :to="{ name: 'HAView', params: { hAId: productDeployement.ha.id } }">{{
                productDeployement.ha.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.hsm')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.hsm">
              <router-link :to="{ name: 'HSMView', params: { hSMId: productDeployement.hsm.id } }">{{
                productDeployement.hsm.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.db')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.db">
              <router-link :to="{ name: 'DBView', params: { dBId: productDeployement.db.id } }">{{
                productDeployement.db.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.host')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.host">
              <router-link :to="{ name: 'HOSTView', params: { hOSTId: productDeployement.host.id } }">{{
                productDeployement.host.name
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.applicationServer')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.applicationServer">
              <router-link
                :to="{ name: 'ApplicationServerView', params: { applicationServerId: productDeployement.applicationServer.id } }"
                >{{ productDeployement.applicationServer.name }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.productDeployement.os')"></span>
          </dt>
          <dd>
            <div v-if="productDeployement.os">
              <router-link :to="{ name: 'OSView', params: { oSId: productDeployement.os.id } }">{{
                productDeployement.os.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="productDeployement.id"
          :to="{ name: 'ProductDeployementEdit', params: { productDeployementId: productDeployement.id } }"
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

<script lang="ts" src="./product-deployement-details.component.ts"></script>
