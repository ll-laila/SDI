<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="sdiApp.actionRequest.home.createOrEditLabel"
          data-cy="ActionRequestCreateUpdateHeading"
          v-text="t$('sdiApp.actionRequest.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="actionRequest.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="actionRequest.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.entityType')" for="action-request-entityType"></label>
            <input
              type="text"
              class="form-control"
              name="entityType"
              id="action-request-entityType"
              data-cy="entityType"
              :class="{ valid: !v$.entityType.$invalid, invalid: v$.entityType.$invalid }"
              v-model="v$.entityType.$model"
              required
            />
            <div v-if="v$.entityType.$anyDirty && v$.entityType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.entityType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.newData')" for="action-request-newData"></label>
            <textarea
              class="form-control"
              name="newData"
              id="action-request-newData"
              data-cy="newData"
              :class="{ valid: !v$.newData.$invalid, invalid: v$.newData.$invalid }"
              v-model="v$.newData.$model"
              required
            ></textarea>
            <div v-if="v$.newData.$anyDirty && v$.newData.$invalid">
              <small class="form-text text-danger" v-for="error of v$.newData.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group" v-if="hasAnyAuthority('ROLE_ADMIN')">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.status')" for="action-request-status"></label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              id="action-request-status"
              data-cy="status"
              required
            >
              <option
                v-for="actionRequestStatus in actionRequestStatusValues"
                :key="actionRequestStatus"
                :value="actionRequestStatus"
                :label="t$('sdiApp.ActionRequestStatus.' + actionRequestStatus)"
              >
                {{ actionRequestStatus }}
              </option>
            </select>
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.createdBy')" for="action-request-createdBy"></label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="action-request-createdBy"
              data-cy="createdBy"
              :class="{ valid: !v$.createdBy.$invalid, invalid: v$.createdBy.$invalid }"
              v-model="v$.createdBy.$model"
              readonly
            />
            <div v-if="v$.createdBy.$anyDirty && v$.createdBy.$invalid">
              <small class="form-text text-danger" v-for="error of v$.createdBy.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.approvedBy')" for="action-request-approvedBy"></label>
            <input
              type="text"
              class="form-control"
              name="approvedBy"
              id="action-request-approvedBy"
              data-cy="approvedBy"
              :class="{ valid: !v$.approvedBy.$invalid, invalid: v$.approvedBy.$invalid }"
              v-model="v$.approvedBy.$model"
              readonly
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.createdAt')" for="action-request-createdAt"></label>
            <div class="d-flex">
              <input
                id="action-request-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !v$.createdAt.$invalid, invalid: v$.createdAt.$invalid }"
                :value="convertDateTimeFromServer(v$.createdAt.$model)"
                @change="updateInstantField('createdAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sdiApp.actionRequest.updatedAt')" for="action-request-updatedAt"></label>
            <div class="d-flex">
              <input
                id="action-request-updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !v$.updatedAt.$invalid, invalid: v$.updatedAt.$invalid }"
                :value="convertDateTimeFromServer(v$.updatedAt.$model)"
                @change="updateInstantField('updatedAt', $event)"
              />
            </div>
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
<script lang="ts" src="./action-request-update.component.ts"></script>
