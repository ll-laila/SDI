import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ModuleDeployementService from './module-deployement.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ModuleService from '@/entities/module/module.service';
import { type IModule } from '@/shared/model/module.model';
import ProductDeployementService from '@/entities/product-deployement/product-deployement.service';
import { type IProductDeployement } from '@/shared/model/product-deployement.model';
import CustomisationLevelService from '@/entities/customisation-level/customisation-level.service';
import { type ICustomisationLevel } from '@/shared/model/customisation-level.model';
import { type IModuleDeployement, ModuleDeployement } from '@/shared/model/module-deployement.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ModuleDeployementUpdate',
  setup() {
    const moduleDeployementService = inject('moduleDeployementService', () => new ModuleDeployementService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const moduleDeployement: Ref<IModuleDeployement> = ref(new ModuleDeployement());

    const moduleService = inject('moduleService', () => new ModuleService());

    const modules: Ref<IModule[]> = ref([]);

    const productDeployementService = inject('productDeployementService', () => new ProductDeployementService());

    const productDeployements: Ref<IProductDeployement[]> = ref([]);

    const customisationLevelService = inject('customisationLevelService', () => new CustomisationLevelService());

    const customisationLevels: Ref<ICustomisationLevel[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveModuleDeployement = async moduleDeployementId => {
      try {
        const res = await moduleDeployementService().find(moduleDeployementId);
        moduleDeployement.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.moduleDeployementId) {
      retrieveModuleDeployement(route.params.moduleDeployementId);
    }

    const initRelationships = () => {
      moduleService()
        .retrieve()
        .then(res => {
          modules.value = res.data;
        });
      productDeployementService()
        .retrieve()
        .then(res => {
          productDeployements.value = res.data;
        });
      customisationLevelService()
        .retrieve()
        .then(res => {
          customisationLevels.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      customisationDescription: {},
      creaDate: {},
      updateDate: {},
      notes: {},
      module: {},
      productDeployement: {},
      custoLevel: {},
    };
    const v$ = useVuelidate(validationRules, moduleDeployement as any);
    v$.value.$validate();

    return {
      moduleDeployementService,
      alertService,
      moduleDeployement,
      previousState,
      isSaving,
      currentLanguage,
      modules,
      productDeployements,
      customisationLevels,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.moduleDeployement.id) {
        this.moduleDeployementService()
          .update(this.moduleDeployement)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.moduleDeployement.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.moduleDeployementService()
          .create(this.moduleDeployement)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.moduleDeployement.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
