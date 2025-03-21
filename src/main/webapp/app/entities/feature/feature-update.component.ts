import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FeatureService from './feature.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ModuleService from '@/entities/module/module.service';
import { type IModule } from '@/shared/model/module.model';
import { Feature, type IFeature } from '@/shared/model/feature.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FeatureUpdate',
  setup() {
    const featureService = inject('featureService', () => new FeatureService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const feature: Ref<IFeature> = ref(new Feature());

    const moduleService = inject('moduleService', () => new ModuleService());

    const modules: Ref<IModule[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveFeature = async featureId => {
      try {
        const res = await featureService().find(featureId);
        feature.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.featureId) {
      retrieveFeature(route.params.featureId);
    }

    const initRelationships = () => {
      moduleService()
        .retrieve()
        .then(res => {
          modules.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      code: {},
      apiVersion: {},
      description: {},
      notes: {},
      creaDate: {},
      updateDate: {},
      featureDeployements: {},
      module: {},
    };
    const v$ = useVuelidate(validationRules, feature as any);
    v$.value.$validate();

    return {
      featureService,
      alertService,
      feature,
      previousState,
      isSaving,
      currentLanguage,
      modules,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.feature.id) {
        this.featureService()
          .update(this.feature)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.feature.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.featureService()
          .create(this.feature)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.feature.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
