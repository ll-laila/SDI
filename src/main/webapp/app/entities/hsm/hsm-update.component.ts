import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import HSMService from './hsm.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { HSM, type IHSM } from '@/shared/model/hsm.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HSMUpdate',
  setup() {
    const hSMService = inject('hSMService', () => new HSMService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const hSM: Ref<IHSM> = ref(new HSM());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveHSM = async hSMId => {
      try {
        const res = await hSMService().find(hSMId);
        hSM.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.hSMId) {
      retrieveHSM(route.params.hSMId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      creaDate: {},
      updateDate: {},
      notes: {},
      productDeployements: {},
    };
    const v$ = useVuelidate(validationRules, hSM as any);
    v$.value.$validate();

    return {
      hSMService,
      alertService,
      hSM,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.hSM.id) {
        this.hSMService()
          .update(this.hSM)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.hSM.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.hSMService()
          .create(this.hSM)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.hSM.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
