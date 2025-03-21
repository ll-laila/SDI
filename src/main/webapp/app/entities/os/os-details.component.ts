import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import OSService from './os.service';
import { type IOS } from '@/shared/model/os.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OSDetails',
  setup() {
    const oSService = inject('oSService', () => new OSService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const oS: Ref<IOS> = ref({});

    const retrieveOS = async oSId => {
      try {
        const res = await oSService().find(oSId);
        oS.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.oSId) {
      retrieveOS(route.params.oSId);
    }

    return {
      alertService,
      oS,

      previousState,
      t$: useI18n().t,
    };
  },
});
