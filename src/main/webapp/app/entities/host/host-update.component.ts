import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import HOSTService from './host.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { HOST, type IHOST } from '@/shared/model/host.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HOSTUpdate',
  setup() {
    const hOSTService = inject('hOSTService', () => new HOSTService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const hOST: Ref<IHOST> = ref(new HOST());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveHOST = async hOSTId => {
      try {
        const res = await hOSTService().find(hOSTId);
        hOST.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.hOSTId) {
      retrieveHOST(route.params.hOSTId);
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
    const v$ = useVuelidate(validationRules, hOST as any);
    v$.value.$validate();

    return {
      hOSTService,
      alertService,
      hOST,
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
      if (this.hOST.id) {
        this.hOSTService()
          .update(this.hOST)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.hOST.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.hOSTService()
          .create(this.hOST)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.hOST.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
