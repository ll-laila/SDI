<template>
  <div>
    <h2 id="page-heading" data-cy="FeatureDeployementHeading">
      <span v-text="t$('sdiApp.featureDeployement.home.title')" id="feature-deployement-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.featureDeployement.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'FeatureDeployementCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-feature-deployement"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.featureDeployement.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && featureDeployements && featureDeployements.length === 0">
      <span v-text="t$('sdiApp.featureDeployement.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="featureDeployements && featureDeployements.length > 0">
      <table class="table table-striped" aria-describedby="featureDeployements">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.code')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.customisationDescription')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.notes')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.feature')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.featureDeployement.custoLevel')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="featureDeployement in featureDeployements" :key="featureDeployement.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FeatureDeployementView', params: { featureDeployementId: featureDeployement.id } }">{{
                featureDeployement.id
              }}</router-link>
            </td>
            <td>{{ featureDeployement.code }}</td>
            <td>{{ featureDeployement.customisationDescription }}</td>
            <td>{{ featureDeployement.creaDate }}</td>
            <td>{{ featureDeployement.updateDate }}</td>
            <td>{{ featureDeployement.notes }}</td>
            <td>
              <div v-if="featureDeployement.feature">
                <router-link :to="{ name: 'FeatureView', params: { featureId: featureDeployement.feature.id } }">{{
                  featureDeployement.feature.name
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="featureDeployement.custoLevel">
                <router-link :to="{ name: 'CustomisationLevelView', params: { customisationLevelId: featureDeployement.custoLevel.id } }">{{
                  featureDeployement.custoLevel.level
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FeatureDeployementView', params: { featureDeployementId: featureDeployement.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FeatureDeployementEdit', params: { featureDeployementId: featureDeployement.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(featureDeployement)"
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
          id="sdiApp.featureDeployement.delete.question"
          data-cy="featureDeployementDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-featureDeployement-heading" v-text="t$('sdiApp.featureDeployement.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-featureDeployement"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeFeatureDeployement()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./feature-deployement.component.ts"></script>
