<template>
  <div>
    <h2 id="page-heading" data-cy="OSHeading">
      <span v-text="t$('sdiApp.oS.home.title')" id="os-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.oS.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'OSCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-os">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.oS.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && oS && oS.length === 0">
      <span v-text="t$('sdiApp.oS.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="oS && oS.length > 0">
      <table class="table table-striped" aria-describedby="oS">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.oS.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.oS.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.oS.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.oS.notes')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="oS in oS" :key="oS.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OSView', params: { oSId: oS.id } }">{{ oS.id }}</router-link>
            </td>
            <td>{{ oS.name }}</td>
            <td>{{ oS.creaDate }}</td>
            <td>{{ oS.updateDate }}</td>
            <td>{{ oS.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OSView', params: { oSId: oS.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OSEdit', params: { oSId: oS.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(oS)"
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
        <span id="sdiApp.oS.delete.question" data-cy="oSDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-oS-heading" v-text="t$('sdiApp.oS.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-oS"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeOS()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./os.component.ts"></script>
