<template>
  <div>
    <h2 id="page-heading" data-cy="ActionRequestHeading">
      <span v-text="t$('sdiApp.actionRequest.home.title')" id="action-request-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.actionRequest.home.refreshListLabel')"></span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && actionRequests && actionRequests.length === 0">
      <span v-text="t$('sdiApp.actionRequest.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="actionRequests && actionRequests.length > 0">
      <table class="table table-striped" aria-describedby="actionRequests">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.entityType')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.newData')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.status')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.createdBy')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.approvedBy')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.createdAt')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.actionRequest.updatedAt')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="actionRequest in actionRequests" :key="actionRequest.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ActionRequestView', params: { actionRequestId: actionRequest.id } }">{{
                actionRequest.id
              }}</router-link>
            </td>
            <td>{{ actionRequest.entityType }}</td>
            <td>
              <div class="d-flex align-items-center">
                <button class="btn btn-sm btn-outline-primary ml-2" @click="openJsonModal(actionRequest)" title="View JSON data">
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                </button>
              </div>
            </td>
            <td>
              <span
                :class="getStatusClass(actionRequest.status)"
                class="status-badge"
                v-text="t$('sdiApp.ActionRequestStatus.' + actionRequest.status)"
              ></span>
            </td>
            <td>{{ actionRequest.createdBy }}</td>
            <td>{{ actionRequest.approvedBy || t$('sdiApp.actionRequest.Unrealized') }}</td>
            <td>{{ formatDateShort(actionRequest.createdAt) || '' }}</td>
            <td>{{ formatDateShort(actionRequest.updatedAt) || '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ActionRequestView', params: { actionRequestId: actionRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  v-if="actionRequest.status !== 'APPROVED'"
                  :to="{ name: 'ActionRequestEdit', params: { actionRequestId: actionRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(actionRequest)"
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

    <!-- Delete Confirmation Modal -->
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="sdiApp.actionRequest.delete.question"
          data-cy="actionRequestDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-actionRequest-heading" v-text="t$('sdiApp.actionRequest.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-actionRequest"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeActionRequest()"
          ></button>
        </div>
      </template>
    </b-modal>

    <!-- JSON Data Modal -->
    <b-modal ref="jsonModal" id="json-modal" size="lg" hide-footer>
      <div class="modal-body">
        <div v-if="selectedActionRequest">
          <h5 class="mb-3">{{ selectedActionRequest.entityType }} - ID: {{ selectedActionRequest.id }}</h5>
          <div class="json-container">
            <pre class="json-formatter">{{ formatJson(selectedActionRequest.newData) }}</pre>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" @click="closeJsonModal" v-text="t$('sdiApp.actionRequest.close')"></button>
        <button type="button" class="btn btn-primary" @click="copyJsonToClipboard" :disabled="copySuccess">
          <span v-if="copySuccess" v-text="t$('sdiApp.actionRequest.Copied')"></span>
          <span v-else v-text="t$('sdiApp.actionRequest.Copy')"></span>
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./action-request.component.ts"></script>


<style scoped>
.json-container {
  max-height: 400px;
  overflow-y: auto;
  background-color: #f8f9fa;
  border-radius: 4px;
  border: 1px solid #dee2e6;
}

.json-formatter {
  padding: 10px;
  margin: 0;
  white-space: pre-wrap;
  font-family: monospace;
  font-size: 0.9rem;
  color: #333;
}

.text-truncate {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.status-badge {
  display: inline-block;
  padding: 0.25rem 0.5rem;
  border-radius: 0.25rem;
  font-weight: 500;
}

.status-approved {
  background-color: #d4edda;
  color: #155724;
}

.status-rejected {
  background-color: #f8d7da;
  color: #721c24;
}

.status-pending {
  background-color: #fff3cd;
  color: #856404;
}
</style>
