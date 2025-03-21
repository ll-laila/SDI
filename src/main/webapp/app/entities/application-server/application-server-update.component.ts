import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ApplicationServerService from './application-server.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { ApplicationServer, type IApplicationServer } from '@/shared/model/application-server.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApplicationServerUpdate',
  setup() {
    const applicationServerService = inject('applicationServerService', () => new ApplicationServerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const applicationServer: Ref<IApplicationServer> = ref(new ApplicationServer());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveApplicationServer = async applicationServerId => {
      try {
        const res = await applicationServerService().find(applicationServerId);
        applicationServer.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.applicationServerId) {
      retrieveApplicationServer(route.params.applicationServerId);
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
    const v$ = useVuelidate(validationRules, applicationServer as any);
    v$.value.$validate();

    return {
      applicationServerService,
      alertService,
      applicationServer,
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
      if (this.applicationServer.id) {
        this.applicationServerService()
          .update(this.applicationServer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.applicationServer.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.applicationServerService()
          .create(this.applicationServer)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.applicationServer.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
