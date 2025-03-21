import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ModuleService from './module.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProductService from '@/entities/product/product.service';
import { type IProduct } from '@/shared/model/product.model';
import DomaineService from '@/entities/domaine/domaine.service';
import { type IDomaine } from '@/shared/model/domaine.model';
import { type IModule, Module } from '@/shared/model/module.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ModuleUpdate',
  setup() {
    const moduleService = inject('moduleService', () => new ModuleService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const module: Ref<IModule> = ref(new Module());

    const productService = inject('productService', () => new ProductService());

    const products: Ref<IProduct[]> = ref([]);

    const domaineService = inject('domaineService', () => new DomaineService());

    const domaines: Ref<IDomaine[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveModule = async moduleId => {
      try {
        const res = await moduleService().find(moduleId);
        module.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.moduleId) {
      retrieveModule(route.params.moduleId);
    }

    const initRelationships = () => {
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
      domaineService()
        .retrieve()
        .then(res => {
          domaines.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      code: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      creaDate: {},
      updateDate: {},
      notes: {},
      features: {},
      moduleDeployements: {},
      product: {},
      domaine: {},
    };
    const v$ = useVuelidate(validationRules, module as any);
    v$.value.$validate();

    return {
      moduleService,
      alertService,
      module,
      previousState,
      isSaving,
      currentLanguage,
      products,
      domaines,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.module.id) {
        this.moduleService()
          .update(this.module)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.module.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.moduleService()
          .create(this.module)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.module.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
