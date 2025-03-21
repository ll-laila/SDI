<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.client.home.createOrEditLabel"
          data-cy="ClientCreateUpdateHeading"
          v-text="t$('sdiApp.client.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="client.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="client.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.clientLogo')" for="client-clientLogo"></label>
            <input
              type="text"
              class="form-control"
              name="clientLogo"
              id="client-clientLogo"
              data-cy="clientLogo"
              :class="{ valid: !v$.clientLogo.$invalid, invalid: v$.clientLogo.$invalid }"
              v-model="v$.clientLogo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.name')" for="client-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="client-name"
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
            <label class="form-control-label" v-text="t$('sdiApp.client.code')" for="client-code"></label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="client-code"
              data-cy="code"
              :class="{ valid: !v$.code.$invalid, invalid: v$.code.$invalid }"
              v-model="v$.code.$model"
              required
            />
            <div v-if="v$.code.$anyDirty && v$.code.$invalid">
              <small class="form-text text-danger" v-for="error of v$.code.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.mainContactName')" for="client-mainContactName"></label>
            <input
              type="text"
              class="form-control"
              name="mainContactName"
              id="client-mainContactName"
              data-cy="mainContactName"
              :class="{ valid: !v$.mainContactName.$invalid, invalid: v$.mainContactName.$invalid }"
              v-model="v$.mainContactName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.mainContactEmail')" for="client-mainContactEmail"></label>
            <input
              type="text"
              class="form-control"
              name="mainContactEmail"
              id="client-mainContactEmail"
              data-cy="mainContactEmail"
              :class="{ valid: !v$.mainContactEmail.$invalid, invalid: v$.mainContactEmail.$invalid }"
              v-model="v$.mainContactEmail.$model"
            />
            <div v-if="v$.mainContactEmail.$anyDirty && v$.mainContactEmail.$invalid">
              <small class="form-text text-danger" v-for="error of v$.mainContactEmail.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.client.currentCardHolderNumber')"
              for="client-currentCardHolderNumber"
            ></label>
            <input
              type="number"
              class="form-control"
              name="currentCardHolderNumber"
              id="client-currentCardHolderNumber"
              data-cy="currentCardHolderNumber"
              :class="{ valid: !v$.currentCardHolderNumber.$invalid, invalid: v$.currentCardHolderNumber.$invalid }"
              v-model.number="v$.currentCardHolderNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.currentBruncheNumber')" for="client-currentBruncheNumber"></label>
            <input
              type="number"
              class="form-control"
              name="currentBruncheNumber"
              id="client-currentBruncheNumber"
              data-cy="currentBruncheNumber"
              :class="{ valid: !v$.currentBruncheNumber.$invalid, invalid: v$.currentBruncheNumber.$invalid }"
              v-model.number="v$.currentBruncheNumber.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.client.currentCustomersNumber')"
              for="client-currentCustomersNumber"
            ></label>
            <input
              type="number"
              class="form-control"
              name="currentCustomersNumber"
              id="client-currentCustomersNumber"
              data-cy="currentCustomersNumber"
              :class="{ valid: !v$.currentCustomersNumber.$invalid, invalid: v$.currentCustomersNumber.$invalid }"
              v-model.number="v$.currentCustomersNumber.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.client.mainContactPhoneNumber')"
              for="client-mainContactPhoneNumber"
            ></label>
            <input
              type="text"
              class="form-control"
              name="mainContactPhoneNumber"
              id="client-mainContactPhoneNumber"
              data-cy="mainContactPhoneNumber"
              :class="{ valid: !v$.mainContactPhoneNumber.$invalid, invalid: v$.mainContactPhoneNumber.$invalid }"
              v-model="v$.mainContactPhoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.url')" for="client-url"></label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="client-url"
              data-cy="url"
              :class="{ valid: !v$.url.$invalid, invalid: v$.url.$invalid }"
              v-model="v$.url.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.creaDate')" for="client-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="client-creaDate"
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
                id="client-creaDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.client.updateDate')" for="client-updateDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="client-updateDate"
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
                id="client-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.client.notes')" for="client-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="client-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.country')" for="client-country"></label>
            <select class="form-control" id="client-country" data-cy="country" name="country" v-model="client.country">
              <option :value="null"></option>
              <option
                :value="client.country && countryOption.id === client.country.id ? client.country : countryOption"
                v-for="countryOption in countries"
                :key="countryOption.id"
              >
                {{ countryOption.countryname }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.size')" for="client-size"></label>
            <select class="form-control" id="client-size" data-cy="size" name="size" v-model="client.size">
              <option :value="null"></option>
              <option
                :value="client.size && clientSizeOption.id === client.size.id ? client.size : clientSizeOption"
                v-for="clientSizeOption in clientSizes"
                :key="clientSizeOption.id"
              >
                {{ clientSizeOption.sizeCode }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.client.clientType')" for="client-clientType"></label>
            <select class="form-control" id="client-clientType" data-cy="clientType" name="clientType" v-model="client.clientType">
              <option :value="null"></option>
              <option
                :value="client.clientType && clientTypeOption.id === client.clientType.id ? client.clientType : clientTypeOption"
                v-for="clientTypeOption in clientTypes"
                :key="clientTypeOption.id"
              >
                {{ clientTypeOption.type }}
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
<script lang="ts" src="./client-update.component.ts"></script>
