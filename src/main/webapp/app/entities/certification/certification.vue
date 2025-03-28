<template>
  <div>
    <h2 id="page-heading" data-cy="CertificationHeading">
      <span v-text="t$('sdiApp.certification.home.title')" id="certification-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sdiApp.certification.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CertificationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-certification"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sdiApp.certification.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && certifications && certifications.length === 0">
      <span v-text="t$('sdiApp.certification.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="certifications && certifications.length > 0">
      <table class="table table-striped" aria-describedby="certifications">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.name')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.description')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.creaDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.updateDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.expireDate')"></span></th>
            <th scope="row"><span v-text="t$('sdiApp.certification.notes')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="certification in certifications" :key="certification.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CertificationView', params: { certificationId: certification.id } }">{{
                certification.id
              }}</router-link>
            </td>
            <td>{{ certification.name }}</td>
            <td>{{ certification.description }}</td>
            <td>{{ certification.creaDate }}</td>
            <td>{{ certification.updateDate }}</td>
            <td>{{ certification.expireDate }}</td>
            <td>{{ certification.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'CertificationView', params: { certificationId: certification.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'CertificationEdit', params: { certificationId: certification.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(certification)"
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
          id="sdiApp.certification.delete.question"
          data-cy="certificationDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-certification-heading" v-text="t$('sdiApp.certification.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-certification"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeCertification()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./certification.component.ts"></script>
