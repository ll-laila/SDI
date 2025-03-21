<template>
  <div>
    <h2 id="page-heading" data-cy="ProductHeading">
      <span v-text="t$('sdiApp.product.home.title')" id="product-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.product.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ProductCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-product"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.product.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && products && products.length === 0">
      <span v-text="t$('sdiApp.product.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="products && products.length > 0">
      <table class="table table-striped" aria-describedby="products">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.code')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.logo')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.notes')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.product.productline')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="product in products" :key="product.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProductView', params: { productId: product.id } }">{{ product.id }}</router-link>
            </td>
            <td>{{ product.name }}</td>
            <td>{{ product.code }}</td>
            <td>{{ product.logo }}</td>
            <td>{{ product.notes }}</td>
            <td>{{ product.creaDate }}</td>
            <td>{{ product.updateDate }}</td>
            <td>
              <div v-if="product.productline">
                <router-link :to="{ name: 'ProductLineView', params: { productLineId: product.productline.id } }">{{
                  product.productline.name
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProductView', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProductEdit', params: { productId: product.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(product)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="sdiApp.product.delete.question" data-cy="productDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-product-heading" v-text="t$('sdiApp.product.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-product"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeProduct()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./product.component.ts"></script>
