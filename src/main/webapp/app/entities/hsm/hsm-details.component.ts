import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import HSMService from './hsm.service';
import { type IHSM } from '@/shared/model/hsm.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HSMDetails',
  setup() {
    const hSMService = inject('hSMService', () => new HSMService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const hSM: Ref<IHSM> = ref({});

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

    return {
      alertService,
      hSM,

      previousState,
      t$: useI18n().t,
    };
  },
});
