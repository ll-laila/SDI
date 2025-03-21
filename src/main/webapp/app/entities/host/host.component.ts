import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import HOSTService from './host.service';
import { type IHOST } from '@/shared/model/host.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HOST',
  setup() {
    const { t: t$ } = useI18n();
    const hOSTService = inject('hOSTService', () => new HOSTService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const hOSTS: Ref<IHOST[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveHOSTs = async () => {
      isFetching.value = true;
      try {
        const res = await hOSTService().retrieve();
        hOSTS.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveHOSTs();
    };

    onMounted(async () => {
      await retrieveHOSTs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IHOST) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeHOST = async () => {
      try {
        await hOSTService().delete(removeId.value);
        const message = t$('sdiApp.hOST.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveHOSTs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      hOSTS,
      handleSyncList,
      isFetching,
      retrieveHOSTs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeHOST,
      t$,
    };
  },
});
