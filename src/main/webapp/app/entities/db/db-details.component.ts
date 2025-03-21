import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import DBService from './db.service';
import { type IDB } from '@/shared/model/db.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DBDetails',
  setup() {
    const dBService = inject('dBService', () => new DBService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const dB: Ref<IDB> = ref({});

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

    return {
      alertService,
      dB,

      previousState,
      t$: useI18n().t,
    };
  },
});
