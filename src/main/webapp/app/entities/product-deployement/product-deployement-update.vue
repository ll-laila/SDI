<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.productDeployement.home.createOrEditLabel"
          data-cy="ProductDeployementCreateUpdateHeading"
          v-text="t$('sdiApp.productDeployement.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="productDeployement.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="productDeployement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.code')" for="product-deployement-code"></label>
            <input
              type="text"
              class="form-control"
              name="code"
              id="product-deployement-code"
              data-cy="code"
              :class="{ valid: !v$.code.$invalid, invalid: v$.code.$invalid }"
              v-model="v$.code.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.productDeployement.refContract')"
              for="product-deployement-refContract"
            ></label>
            <input
              type="text"
              class="form-control"
              name="refContract"
              id="product-deployement-refContract"
              data-cy="refContract"
              :class="{ valid: !v$.refContract.$invalid, invalid: v$.refContract.$invalid }"
              v-model="v$.refContract.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.productDeployement.startDeployementDate')"
              for="product-deployement-startDeployementDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="product-deployement-startDeployementDate"
                  v-model="v$.startDeployementDate.$model"
                  name="startDeployementDate"
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
                id="product-deployement-startDeployementDate"
                data-cy="startDeployementDate"
                type="text"
                class="form-control"
                name="startDeployementDate"
                :class="{ valid: !v$.startDeployementDate.$invalid, invalid: v$.startDeployementDate.$invalid }"
                v-model="v$.startDeployementDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.productDeployement.endDeployementDate')"
              for="product-deployement-endDeployementDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="product-deployement-endDeployementDate"
                  v-model="v$.endDeployementDate.$model"
                  name="endDeployementDate"
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
                id="product-deployement-endDeployementDate"
                data-cy="endDeployementDate"
                type="text"
                class="form-control"
                name="endDeployementDate"
                :class="{ valid: !v$.endDeployementDate.$invalid, invalid: v$.endDeployementDate.$invalid }"
                v-model="v$.endDeployementDate.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.creaDate')" for="product-deployement-creaDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="product-deployement-creaDate"
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
                id="product-deployement-creaDate"
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
              v-text="t$('sdiApp.productDeployement.updateDate')"
              for="product-deployement-updateDate"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="product-deployement-updateDate"
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
                id="product-deployement-updateDate"
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
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.notes')" for="product-deployement-notes"></label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="product-deployement-notes"
              data-cy="notes"
              :class="{ valid: !v$.notes.$invalid, invalid: v$.notes.$invalid }"
              v-model="v$.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.client')" for="product-deployement-client"></label>
            <select class="form-control" id="product-deployement-client" data-cy="client" name="client" v-model="productDeployement.client">
              <option :value="null"></option>
              <option
                :value="
                  productDeployement.client && clientOption.id === productDeployement.client.id ? productDeployement.client : clientOption
                "
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.code }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.product')" for="product-deployement-product"></label>
            <select
              class="form-control"
              id="product-deployement-product"
              data-cy="product"
              name="product"
              v-model="productDeployement.product"
            >
              <option :value="null"></option>
              <option
                :value="
                  productDeployement.product && productOption.id === productDeployement.product.id
                    ? productDeployement.product
                    : productOption
                "
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.productDeployement.deployementType')"
              for="product-deployement-deployementType"
            ></label>
            <select
              class="form-control"
              id="product-deployement-deployementType"
              data-cy="deployementType"
              name="deployementType"
              v-model="productDeployement.deployementType"
            >
              <option :value="null"></option>
              <option
                :value="
                  productDeployement.deployementType && deployementTypeOption.id === productDeployement.deployementType.id
                    ? productDeployement.deployementType
                    : deployementTypeOption
                "
                v-for="deployementTypeOption in deployementTypes"
                :key="deployementTypeOption.id"
              >
                {{ deployementTypeOption.type }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.ha')" for="product-deployement-ha"></label>
            <select class="form-control" id="product-deployement-ha" data-cy="ha" name="ha" v-model="productDeployement.ha">
              <option :value="null"></option>
              <option
                :value="productDeployement.ha && hAOption.id === productDeployement.ha.id ? productDeployement.ha : hAOption"
                v-for="hAOption in hAS"
                :key="hAOption.id"
              >
                {{ hAOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.hsm')" for="product-deployement-hsm"></label>
            <select class="form-control" id="product-deployement-hsm" data-cy="hsm" name="hsm" v-model="productDeployement.hsm">
              <option :value="null"></option>
              <option
                :value="productDeployement.hsm && hSMOption.id === productDeployement.hsm.id ? productDeployement.hsm : hSMOption"
                v-for="hSMOption in hSMS"
                :key="hSMOption.id"
              >
                {{ hSMOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.db')" for="product-deployement-db"></label>
            <select class="form-control" id="product-deployement-db" data-cy="db" name="db" v-model="productDeployement.db">
              <option :value="null"></option>
              <option
                :value="productDeployement.db && dBOption.id === productDeployement.db.id ? productDeployement.db : dBOption"
                v-for="dBOption in dBS"
                :key="dBOption.id"
              >
                {{ dBOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.host')" for="product-deployement-host"></label>
            <select class="form-control" id="product-deployement-host" data-cy="host" name="host" v-model="productDeployement.host">
              <option :value="null"></option>
              <option
                :value="productDeployement.host && hOSTOption.id === productDeployement.host.id ? productDeployement.host : hOSTOption"
                v-for="hOSTOption in hOSTS"
                :key="hOSTOption.id"
              >
                {{ hOSTOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sdiApp.productDeployement.applicationServer')"
              for="product-deployement-applicationServer"
            ></label>
            <select
              class="form-control"
              id="product-deployement-applicationServer"
              data-cy="applicationServer"
              name="applicationServer"
              v-model="productDeployement.applicationServer"
            >
              <option :value="null"></option>
              <option
                :value="
                  productDeployement.applicationServer && applicationServerOption.id === productDeployement.applicationServer.id
                    ? productDeployement.applicationServer
                    : applicationServerOption
                "
                v-for="applicationServerOption in applicationServers"
                :key="applicationServerOption.id"
              >
                {{ applicationServerOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.productDeployement.os')" for="product-deployement-os"></label>
            <select class="form-control" id="product-deployement-os" data-cy="os" name="os" v-model="productDeployement.os">
              <option :value="null"></option>
              <option
                :value="productDeployement.os && oSOption.id === productDeployement.os.id ? productDeployement.os : oSOption"
                v-for="oSOption in oS"
                :key="oSOption.id"
              >
                {{ oSOption.name }}
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
<script lang="ts" src="./product-deployement-update.component.ts"></script>
