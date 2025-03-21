import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DBService from './db.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { DB, type IDB } from '@/shared/model/db.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DBUpdate',
  setup() {
    const dBService = inject('dBService', () => new DBService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dB: Ref<IDB> = ref(new DB());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDB = async dBId => {
      try {
        const res = await dBService().find(dBId);
        dB.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.dBId) {
      retrieveDB(route.params.dBId);
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
    const v$ = useVuelidate(validationRules, dB as any);
    v$.value.$validate();

    return {
      dBService,
      alertService,
      dB,
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
      if (this.dB.id) {
        this.dBService()
          .update(this.dB)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.dB.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.dBService()
          .create(this.dB)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.dB.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
