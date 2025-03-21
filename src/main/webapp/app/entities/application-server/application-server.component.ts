import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ApplicationServerService from './application-server.service';
import { type IApplicationServer } from '@/shared/model/application-server.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApplicationServer',
  setup() {
    const { t: t$ } = useI18n();
    const applicationServerService = inject('applicationServerService', () => new ApplicationServerService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const applicationServers: Ref<IApplicationServer[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveApplicationServers = async () => {
      isFetching.value = true;
      try {
        const res = await applicationServerService().retrieve();
        applicationServers.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveApplicationServers();
    };

    onMounted(async () => {
      await retrieveApplicationServers();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IApplicationServer) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeApplicationServer = async () => {
      try {
        await applicationServerService().delete(removeId.value);
        const message = t$('sdiApp.applicationServer.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveApplicationServers();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      applicationServers,
      handleSyncList,
      isFetching,
      retrieveApplicationServers,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeApplicationServer,
      t$,
    };
  },
});
