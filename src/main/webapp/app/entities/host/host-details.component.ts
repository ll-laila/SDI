import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import HOSTService from './host.service';
import { type IHOST } from '@/shared/model/host.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HOSTDetails',
  setup() {
    const hOSTService = inject('hOSTService', () => new HOSTService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const hOST: Ref<IHOST> = ref({});

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

    return {
      alertService,
      hOST,

      previousState,
      t$: useI18n().t,
    };
  },
});
