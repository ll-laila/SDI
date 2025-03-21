<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.feature.home.createOrEditLabel"
          data-cy="FeatureCreateUpdateHeading"
          v-text="t$('sdiApp.feature.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="feature.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="feature.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.name')" for="feature-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="feature-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.code')" for="feature-code"></label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="feature-code"
              data-cy="code"
              :class="{ valid: !v$.code.$invalid, invalid: v$.code.$invalid }"
              v-model="v$.code.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.apiVersion')" for="feature-apiVersion"></label>
            <input
              type="text"
              class="form-control"
              name="apiVersion"
              id="feature-apiVersion"
              data-cy="apiVersion"
              :class="{ valid: !v$.apiVersion.$invalid, invalid: v$.apiVersion.$invalid }"
              v-model="v$.apiVersion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.description')" for="feature-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="feature-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.notes')" for="feature-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="feature-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.feature.creaDate')" for="feature-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="feature-creaDate"
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
                id="feature-creaDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.feature.updateDate')" for="feature-updateDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="feature-updateDate"
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
                id="feature-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.feature.module')" for="feature-module"></label>
            <select class="form-control" id="feature-module" data-cy="module" name="module" v-model="feature.module">
              <option :value="null"></option>
              <option
                :value="feature.module && moduleOption.id === feature.module.id ? feature.module : moduleOption"
                v-for="moduleOption in modules"
                :key="moduleOption.id"
              >
                {{ moduleOption.name }}
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
<script lang="ts" src="./feature-update.component.ts"></script>
