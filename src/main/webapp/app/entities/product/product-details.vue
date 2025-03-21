<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="product">
        <h2 class="jh-entity-heading" data-cy="productDetailsHeading">
          <span v-text="t$('sdiApp.product.detail.title')"></span> {{ product.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.product.name')"></span>
          </dt>
          <dd>
            <span>{{ product.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.code')"></span>
          </dt>
          <dd>
            <span>{{ product.code }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.logo')"></span>
          </dt>
          <dd>
            <span>{{ product.logo }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.notes')"></span>
          </dt>
          <dd>
            <span>{{ product.notes }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.creaDate')"></span>
          </dt>
          <dd>
            <span>{{ product.creaDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.updateDate')"></span>
          </dt>
          <dd>
            <span>{{ product.updateDate }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.product.productline')"></span>
          </dt>
          <dd>
            <div v-if="product.productline">
              <router-link :to="{ name: 'ProductLineView', params: { productLineId: product.productline.id } }">{{
                product.productline.name
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="product.id" :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./product-details.component.ts"></script>
