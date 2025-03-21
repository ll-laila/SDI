<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.moduleDeployement.home.createOrEditLabel"
          data-cy="ModuleDeployementCreateUpdateHeading"
          v-text="t$('sdiApp.moduleDeployement.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="moduleDeployement.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="moduleDeployement.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.moduleDeployement.customisationDescription')"
              for="module-deployement-customisationDescription"
            ></label>
            <input
              type="text"
              class="form-control"
              name="customisationDescription"
              id="module-deployement-customisationDescription"
              data-cy="customisationDescription"
              :class="{ valid: !v$.customisationDescription.$invalid, invalid: v$.customisationDescription.$invalid }"
              v-model="v$.customisationDescription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.moduleDeployement.creaDate')" for="module-deployement-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="module-deployement-creaDate"
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
                id="module-deployement-creaDate"
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
              v-text="t$('sdiApp.moduleDeployement.updateDate')"
              for="module-deployement-updateDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="module-deployement-updateDate"
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
                id="module-deployement-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.moduleDeployement.notes')" for="module-deployement-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="module-deployement-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.moduleDeployement.module')" for="module-deployement-module"></label>
            <select class="form-control" id="module-deployement-module" data-cy="module" name="module" v-model="moduleDeployement.module">
              <option :value="null"></option>
              <option
                :value="
                  moduleDeployement.module && moduleOption.id === moduleDeployement.module.id ? moduleDeployement.module : moduleOption
                "
                v-for="moduleOption in modules"
                :key="moduleOption.id"
              >
                {{ moduleOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.moduleDeployement.productDeployement')"
              for="module-deployement-productDeployement"
            ></label>
            <select
              class="form-control"
              id="module-deployement-productDeployement"
              data-cy="productDeployement"
              name="productDeployement"
              v-model="moduleDeployement.productDeployement"
            >
              <option :value="null"></option>
              <option
                :value="
                  moduleDeployement.productDeployement && productDeployementOption.id === moduleDeployement.productDeployement.id
                    ? moduleDeployement.productDeployement
                    : productDeployementOption
                "
                v-for="productDeployementOption in productDeployements"
                :key="productDeployementOption.id"
              >
                {{ productDeployementOption.refContract }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.moduleDeployement.custoLevel')"
              for="module-deployement-custoLevel"
            ></label>
            <select
              class="form-control"
              id="module-deployement-custoLevel"
              data-cy="custoLevel"
              name="custoLevel"
              v-model="moduleDeployement.custoLevel"
            >
              <option :value="null"></option>
              <option
                :value="
                  moduleDeployement.custoLevel && customisationLevelOption.id === moduleDeployement.custoLevel.id
                    ? moduleDeployement.custoLevel
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
<script lang="ts" src="./module-deployement-update.component.ts"></script>
