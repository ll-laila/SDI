<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.country.home.createOrEditLabel"
          data-cy="CountryCreateUpdateHeading"
          v-text="t$('sdiApp.country.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="country.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="country.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.countryname')" for="country-countryname"></label>
            <input
              type="text"
              class="form-control"
              name="countryname"
              id="country-countryname"
              data-cy="countryname"
              :class="{ valid: !v$.countryname.$invalid, invalid: v$.countryname.$invalid }"
              v-model="v$.countryname.$model"
              required
            />
            <div v-if="v$.countryname.$anyDirty && v$.countryname.$invalid">
              <small class="form-text text-danger" v-for="error of v$.countryname.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.countrycode')" for="country-countrycode"></label>
            <input
              type="text"
              class="form-control"
              name="countrycode"
              id="country-countrycode"
              data-cy="countrycode"
              :class="{ valid: !v$.countrycode.$invalid, invalid: v$.countrycode.$invalid }"
              v-model="v$.countrycode.$model"
              required
            />
            <div v-if="v$.countrycode.$anyDirty && v$.countrycode.$invalid">
              <small class="form-text text-danger" v-for="error of v$.countrycode.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.countryFlagcode')" for="country-countryFlagcode"></label>
            <input
              type="text"
              class="form-control"
              name="countryFlagcode"
              id="country-countryFlagcode"
              data-cy="countryFlagcode"
              :class="{ valid: !v$.countryFlagcode.$invalid, invalid: v$.countryFlagcode.$invalid }"
              v-model="v$.countryFlagcode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.countryFlag')" for="country-countryFlag"></label>
            <input
              type="text"
              class="form-control"
              name="countryFlag"
              id="country-countryFlag"
              data-cy="countryFlag"
              :class="{ valid: !v$.countryFlag.$invalid, invalid: v$.countryFlag.$invalid }"
              v-model="v$.countryFlag.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.notes')" for="country-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="country-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.country.creaDate')" for="country-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="country-creaDate"
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
                id="country-creaDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.country.updateDate')" for="country-updateDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="country-updateDate"
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
                id="country-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.country.region')" for="country-region"></label>
            <select class="form-control" id="country-region" data-cy="region" name="region" v-model="country.region">
              <option :value="null"></option>
              <option
                :value="country.region && regionOption.id === country.region.id ? country.region : regionOption"
                v-for="regionOption in regions"
                :key="regionOption.id"
              >
                {{ regionOption.name }}
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
<script lang="ts" src="./country-update.component.ts"></script>
