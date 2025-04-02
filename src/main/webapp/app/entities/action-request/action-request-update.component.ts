import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ActionRequestService from './action-request.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat, useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { ActionRequest, type IActionRequest } from '@/shared/model/action-request.model';
import { ActionRequestStatus } from '@/shared/model/enumerations/action-request-status.model';
import ProductDeployementService from '@/entities/product-deployement/product-deployement.service.ts';
import { type IProductDeployement, ProductDeployement } from '@/shared/model/product-deployement.model';
import type AccountService from '@/account/account.service.ts';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActionRequestUpdate',
  setup() {
    const accountService = inject<AccountService>('accountService');
    const actionRequestService = inject('actionRequestService', () => new ActionRequestService());
    const productDeployementService = inject('productDeployementService', () => new ProductDeployementService());
    const productDeployement: Ref<IProductDeployement> = ref(new ProductDeployement());

    const alertService = inject('alertService', () => useAlertService(), true);

    const actionRequest: Ref<IActionRequest> = ref(new ActionRequest());
    const actionRequestStatusValues: Ref<string[]> = ref(Object.keys(ActionRequestStatus));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const hasAnyAuthorityValues: Ref<any> = ref({});
    const isAdmin = ref(false);

    const previousState = () => router.go(-1);

    const retrieveActionRequest = async actionRequestId => {
      try {
        const res = await actionRequestService().find(actionRequestId);
        res.createdAt = new Date(res.createdAt);
        res.updatedAt = new Date(res.updatedAt);
        actionRequest.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.actionRequestId) {
      retrieveActionRequest(route.params.actionRequestId);
    }

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      entityType: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      newData: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      createdBy: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      approvedBy: {},
      createdAt: {},
      updatedAt: {},
    };
    const v$ = useVuelidate(validationRules, actionRequest as any);
    v$.value.$validate();


    return {
      actionRequestService,
      productDeployementService,
      productDeployement,
      accountService,
      hasAnyAuthorityValues,
      alertService,
      actionRequest,
      previousState,
      actionRequestStatusValues,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      ...useDateFormat({ entityRef: actionRequest }),
      t$,
    };
  },
  created(): void {},
  methods: {
    handleStatusChange() {
      if (this.actionRequest.status === 'APPROVED') {
        try {
          // Convertir newData en objet ProductDeployement
          const productDeployementData = JSON.parse(this.actionRequest.newData);
          this.productDeployement = Object.assign(new ProductDeployement(), productDeployementData);

          // Sauvegarder l'entité ProductDeployement
          if (this.productDeployement.id) {
            this.productDeployementService()
              .update(this.productDeployement)
              .then(param => {
                this.alertService.showInfo(this.t$('sdiApp.productDeployement.updated', { param: param.id }));
              })
              .catch(error => {
                this.alertService.showHttpError(error.response);
              });
          } else {
            this.productDeployementService()
              .create(this.productDeployement)
              .then(response => {
                this.alertService.showSuccess(this.t$('sdiApp.productDeployement.created', { param: response.id }).toString());
              })
              .catch(error => {
                this.alertService.showHttpError(error.response);
              });
          }
        } catch (error) {
          console.error('Erreur lors de la conversion de newData en ProductDeployement', error);
        }
      }
    },

    save(): void {
      this.isSaving = true;
      if (this.actionRequest.status === 'APPROVED') {
        this.handleStatusChange();
      }
      if (this.actionRequest.id) {
        this.actionRequestService()
          .update(this.actionRequest)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.actionRequest.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.actionRequestService()
          .create(this.actionRequest)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.actionRequest.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    hasAnyAuthority(authorities: any): boolean {
      this.accountService.hasAnyAuthorityAndCheckAuth(authorities).then(value => {
        if (this.hasAnyAuthorityValues[authorities] !== value) {
          this.hasAnyAuthorityValues = { ...this.hasAnyAuthorityValues, [authorities]: value };
        }
      });
      return this.hasAnyAuthorityValues[authorities] ?? false;
    },
  },
});
