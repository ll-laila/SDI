<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.clientEvent.home.createOrEditLabel"
          data-cy="ClientEventCreateUpdateHeading"
          v-text="t$('sdiApp.clientEvent.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="clientEvent.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="clientEvent.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.event')" for="client-event-event"></label>
            <input
              type="text"
              class="form-control"
              name="event"
              id="client-event-event"
              data-cy="event"
              :class="{ valid: !v$.event.$invalid, invalid: v$.event.$invalid }"
              v-model="v$.event.$model"
              required
            />
            <div v-if="v$.event.$anyDirty && v$.event.$invalid">
              <small class="form-text text-danger" v-for="error of v$.event.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.description')" for="client-event-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="client-event-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.creaDate')" for="client-event-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="client-event-creaDate"
                  v-model="v$.creaDate.$model"
                  name="creaDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="client-event-creaDate"
                data-cy="creaDate"
                type="text"
                class="form-control"
                name="creaDate"
                :class="{ valid: !v$.creaDate.$invalid, invalid: v$.creaDate.$invalid }"
                v-model="v$.creaDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.updateDate')" for="client-event-updateDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="client-event-updateDate"
                  v-model="v$.updateDate.$model"
                  name="updateDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="client-event-updateDate"
                data-cy="updateDate"
                type="text"
                class="form-control"
                name="updateDate"
                :class="{ valid: !v$.updateDate.$invalid, invalid: v$.updateDate.$invalid }"
                v-model="v$.updateDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.notes')" for="client-event-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="client-event-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.client')" for="client-event-client"></label>
            <select class="form-control" id="client-event-client" data-cy="client" name="client" v-model="clientEvent.client">
              <option :value="null"></option>
              <option
                :value="clientEvent.client && clientOption.id === clientEvent.client.id ? clientEvent.client : clientOption"
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.code }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.clientEvent.clientEventType')" for="client-event-clientEventType"></label>
            <select
              class="form-control"
              id="client-event-clientEventType"
              data-cy="clientEventType"
              name="clientEventType"
              v-model="clientEvent.clientEventType"
            >
              <option :value="null"></option>
              <option
                :value="
                  clientEvent.clientEventType && clientEventTypeOption.id === clientEvent.clientEventType.id
                    ? clientEvent.clientEventType
                    : clientEventTypeOption
                "
                v-for="clientEventTypeOption in clientEventTypes"
                :key="clientEventTypeOption.id"
              >
                {{ clientEventTypeOption.type }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./client-event-update.component.ts"></script>
