import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ProductDeployementService from './product-deployement.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ClientService from '@/entities/client/client.service';
import { type IClient } from '@/shared/model/client.model';
import ProductService from '@/entities/product/product.service';
import { type IProduct } from '@/shared/model/product.model';
import DeployementTypeService from '@/entities/deployement-type/deployement-type.service';
import { type IDeployementType } from '@/shared/model/deployement-type.model';
import HAService from '@/entities/ha/ha.service';
import { type IHA } from '@/shared/model/ha.model';
import HSMService from '@/entities/hsm/hsm.service';
import { type IHSM } from '@/shared/model/hsm.model';
import DBService from '@/entities/db/db.service';
import { type IDB } from '@/shared/model/db.model';
import HOSTService from '@/entities/host/host.service';
import { type IHOST } from '@/shared/model/host.model';
import ApplicationServerService from '@/entities/application-server/application-server.service';
import { type IApplicationServer } from '@/shared/model/application-server.model';
import OSService from '@/entities/os/os.service';
import { type IOS } from '@/shared/model/os.model';
import { type IProductDeployement, ProductDeployement } from '@/shared/model/product-deployement.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProductDeployementUpdate',
  setup() {
    const productDeployementService = inject('productDeployementService', () => new ProductDeployementService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const productDeployement: Ref<IProductDeployement> = ref(new ProductDeployement());

    const clientService = inject('clientService', () => new ClientService());

    const clients: Ref<IClient[]> = ref([]);

    const productService = inject('productService', () => new ProductService());

    const products: Ref<IProduct[]> = ref([]);

    const deployementTypeService = inject('deployementTypeService', () => new DeployementTypeService());

    const deployementTypes: Ref<IDeployementType[]> = ref([]);

    const hAService = inject('hAService', () => new HAService());

    const hAS: Ref<IHA[]> = ref([]);

    const hSMService = inject('hSMService', () => new HSMService());

    const hSMS: Ref<IHSM[]> = ref([]);

    const dBService = inject('dBService', () => new DBService());

    const dBS: Ref<IDB[]> = ref([]);

    const hOSTService = inject('hOSTService', () => new HOSTService());

    const hOSTS: Ref<IHOST[]> = ref([]);

    const applicationServerService = inject('applicationServerService', () => new ApplicationServerService());

    const applicationServers: Ref<IApplicationServer[]> = ref([]);

    const oSService = inject('oSService', () => new OSService());

    const oS: Ref<IOS[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveProductDeployement = async productDeployementId => {
      try {
        const res = await productDeployementService().find(productDeployementId);
        productDeployement.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.productDeployementId) {
      retrieveProductDeployement(route.params.productDeployementId);
    }

    const initRelationships = () => {
      clientService()
        .retrieve()
        .then(res => {
          clients.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
      deployementTypeService()
        .retrieve()
        .then(res => {
          deployementTypes.value = res.data;
        });
      hAService()
        .retrieve()
        .then(res => {
          hAS.value = res.data;
        });
      hSMService()
        .retrieve()
        .then(res => {
          hSMS.value = res.data;
        });
      dBService()
        .retrieve()
        .then(res => {
          dBS.value = res.data;
        });
      hOSTService()
        .retrieve()
        .then(res => {
          hOSTS.value = res.data;
        });
      applicationServerService()
        .retrieve()
        .then(res => {
          applicationServers.value = res.data;
        });
      oSService()
        .retrieve()
        .then(res => {
          oS.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      code: {},
      refContract: {},
      startDeployementDate: {},
      endDeployementDate: {},
      creaDate: {},
      updateDate: {},
      notes: {},
      moduleDeployements: {},
      client: {},
      product: {},
      deployementType: {},
      ha: {},
      hsm: {},
      db: {},
      host: {},
      applicationServer: {},
      os: {},
    };
    const v$ = useVuelidate(validationRules, productDeployement as any);
    v$.value.$validate();

    return {
      productDeployementService,
      alertService,
      productDeployement,
      previousState,
      isSaving,
      currentLanguage,
      clients,
      products,
      deployementTypes,
      hAS,
      hSMS,
      dBS,
      hOSTS,
      applicationServers,
      oS,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.productDeployement.id) {
        this.productDeployementService()
          .update(this.productDeployement)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sdiApp.productDeployement.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.productDeployementService()
          .create(this.productDeployement)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sdiApp.productDeployement.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
