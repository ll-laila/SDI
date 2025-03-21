<template>
  <div>
    <h2 id="page-heading" data-cy="HOSTHeading">
      <span v-text="t$('sdiApp.hOST.home.title')" id="host-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.hOST.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'HOSTCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-host">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.hOST.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hOSTS && hOSTS.length === 0">
      <span v-text="t$('sdiApp.hOST.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="hOSTS && hOSTS.length > 0">
      <table class="table table-striped" aria-describedby="hOSTS">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hOST.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hOST.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hOST.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hOST.notes')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hOST in hOSTS" :key="hOST.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HOSTView', params: { hOSTId: hOST.id } }">{{ hOST.id }}</router-link>
            </td>
            <td>{{ hOST.name }}</td>
            <td>{{ hOST.creaDate }}</td>
            <td>{{ hOST.updateDate }}</td>
            <td>{{ hOST.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HOSTView', params: { hOSTId: hOST.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HOSTEdit', params: { hOSTId: hOST.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(hOST)"
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
        <span id="sdiApp.hOST.delete.question" data-cy="hOSTDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-hOST-heading" v-text="t$('sdiApp.hOST.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-hOST"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeHOST()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./host.component.ts"></script>
