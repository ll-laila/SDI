<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="actionRequest">
        <h2 class="jh-entity-heading" data-cy="actionRequestDetailsHeading">
          <span v-text="t$('sdiApp.actionRequest.detail.title')"></span> {{ actionRequest.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sdiApp.actionRequest.entityType')"></span>
          </dt>
          <dd>
            <span>{{ actionRequest.entityType }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.newData')"></span>
          </dt>
          <dd>
            <div class="d-flex align-items-center">
              <span class="text-muted text-truncate" style="max-width: 300px">
                {{ getJsonPreview(actionRequest.newData) }}
              </span>
              <button class="btn btn-sm btn-outline-primary ml-2" @click="openJsonModal()" title="View JSON data">
                <font-awesome-icon icon="eye"></font-awesome-icon>
              </button>
            </div>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.status')"></span>
          </dt>
          <dd>
            <span
              :class="getStatusClass(actionRequest.status)"
              class="status-badge"
              v-text="t$('sdiApp.ActionRequestStatus.' + actionRequest.status)"
            ></span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ actionRequest.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.approvedBy')"></span>
          </dt>
          <dd>
            <span>{{ actionRequest.approvedBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="actionRequest.createdAt">{{ formatDateLong(actionRequest.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('sdiApp.actionRequest.updatedAt')"></span>
          </dt>
          <dd>
            <span v-if="actionRequest.updatedAt">{{ formatDateLong(actionRequest.updatedAt) }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="actionRequest.id && actionRequest.status !== 'APPROVED'"
          :to="{ name: 'ActionRequestEdit', params: { actionRequestId: actionRequest.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>

    <!-- JSON Data Modal -->
    <b-modal ref="jsonModal" id="json-modal" size="lg" title="" hide-footer>
      <div class="modal-body">
        <div v-if="actionRequest">
          <h5 class="mb-3">{{ actionRequest.entityType }} - ID: {{ actionRequest.id }}</h5>
          <div class="json-container">
            <pre class="json-formatter">{{ formatJson(actionRequest.newData) }}</pre>
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

<script lang="ts" src="./action-request-details.component.ts"></script>

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
