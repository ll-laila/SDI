<template>
  <div>
    <h2 id="page-heading" data-cy="HSMHeading">
      <span v-text="t$('sdiApp.hSM.home.title')" id="hsm-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.hSM.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'HSMCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-hsm">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.hSM.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && hSMS && hSMS.length === 0">
      <span v-text="t$('sdiApp.hSM.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="hSMS && hSMS.length > 0">
      <table class="table table-striped" aria-describedby="hSMS">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hSM.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hSM.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hSM.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.hSM.notes')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="hSM in hSMS" :key="hSM.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'HSMView', params: { hSMId: hSM.id } }">{{ hSM.id }}</router-link>
            </td>
            <td>{{ hSM.name }}</td>
            <td>{{ hSM.creaDate }}</td>
            <td>{{ hSM.updateDate }}</td>
            <td>{{ hSM.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'HSMView', params: { hSMId: hSM.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'HSMEdit', params: { hSMId: hSM.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(hSM)"
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
        <span id="sdiApp.hSM.delete.question" data-cy="hSMDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-hSM-heading" v-text="t$('sdiApp.hSM.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-hSM"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeHSM()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./hsm.component.ts"></script>
