import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ApplicationServerService from './application-server.service';
import { type IApplicationServer } from '@/shared/model/application-server.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApplicationServerDetails',
  setup() {
    const applicationServerService = inject('applicationServerService', () => new ApplicationServerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const applicationServer: Ref<IApplicationServer> = ref({});

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

    return {
      alertService,
      applicationServer,

      previousState,
      t$: useI18n().t,
    };
  },
});
