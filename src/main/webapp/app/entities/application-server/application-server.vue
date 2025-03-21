<template>
  <div>
    <h2 id="page-heading" data-cy="ApplicationServerHeading">
      <span v-text="t$('sdiApp.applicationServer.home.title')" id="application-server-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.applicationServer.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ApplicationServerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-application-server"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.applicationServer.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && applicationServers && applicationServers.length === 0">
      <span v-text="t$('sdiApp.applicationServer.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="applicationServers && applicationServers.length > 0">
      <table class="table table-striped" aria-describedby="applicationServers">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.applicationServer.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.applicationServer.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.applicationServer.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.applicationServer.notes')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="applicationServer in applicationServers" :key="applicationServer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApplicationServerView', params: { applicationServerId: applicationServer.id } }">{{
                applicationServer.id
              }}</router-link>
            </td>
            <td>{{ applicationServer.name }}</td>
            <td>{{ applicationServer.creaDate }}</td>
            <td>{{ applicationServer.updateDate }}</td>
            <td>{{ applicationServer.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ApplicationServerView', params: { applicationServerId: applicationServer.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ApplicationServerEdit', params: { applicationServerId: applicationServer.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(applicationServer)"
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
        <span
          id="sdiApp.applicationServer.delete.question"
          data-cy="applicationServerDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-applicationServer-heading" v-text="t$('sdiApp.applicationServer.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-applicationServer"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeApplicationServer()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./application-server.component.ts"></script>
