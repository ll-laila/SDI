<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.featureDeployement.home.createOrEditLabel"
          data-cy="FeatureDeployementCreateUpdateHeading"
          v-text="t$('sdiApp.featureDeployement.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="featureDeployement.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="featureDeployement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.featureDeployement.code')" for="feature-deployement-code"></label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="feature-deployement-code"
              data-cy="code"
              :class="{ valid: !v$.code.$invalid, invalid: v$.code.$invalid }"
              v-model="v$.code.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.featureDeployement.customisationDescription')"
              for="feature-deployement-customisationDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customisationDescription"
              id="feature-deployement-customisationDescription"
              data-cy="customisationDescription"
              :class="{ valid: !v$.customisationDescription.$invalid, invalid: v$.customisationDescription.$invalid }"
              v-model="v$.customisationDescription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.featureDeployement.creaDate')" for="feature-deployement-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="feature-deployement-creaDate"
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
                id="feature-deployement-creaDate"
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
            <label
              class="form-control-label"
              v-text="t$('sdiApp.featureDeployement.updateDate')"
              for="feature-deployement-updateDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="feature-deployement-updateDate"
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
                id="feature-deployement-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.featureDeployement.notes')" for="feature-deployement-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="feature-deployement-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.featureDeployement.feature')" for="feature-deployement-feature"></label>
            <select
              class="form-control"
              id="feature-deployement-feature"
              data-cy="feature"
              name="feature"
              v-model="featureDeployement.feature"
            >
              <option :value="null"></option>
              <option
                :value="
                  featureDeployement.feature && featureOption.id === featureDeployement.feature.id
                    ? featureDeployement.feature
                    : featureOption
                "
                v-for="featureOption in features"
                :key="featureOption.id"
              >
                {{ featureOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.featureDeployement.custoLevel')"
              for="feature-deployement-custoLevel"
            ></label>
            <select
              class="form-control"
              id="feature-deployement-custoLevel"
              data-cy="custoLevel"
              name="custoLevel"
              v-model="featureDeployement.custoLevel"
            >
              <option :value="null"></option>
              <option
                :value="
                  featureDeployement.custoLevel && customisationLevelOption.id === featureDeployement.custoLevel.id
                    ? featureDeployement.custoLevel
                    : customisationLevelOption
                "
                v-for="customisationLevelOption in customisationLevels"
                :key="customisationLevelOption.id"
              >
                {{ customisationLevelOption.level }}
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
<script lang="ts" src="./feature-deployement-update.component.ts"></script>
