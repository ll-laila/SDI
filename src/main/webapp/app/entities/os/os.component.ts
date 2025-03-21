import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import OSService from './os.service';
import { type IOS } from '@/shared/model/os.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OS',
  setup() {
    const { t: t$ } = useI18n();
    const oSService = inject('oSService', () => new OSService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const oS: Ref<IOS[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveOSs = async () => {
      isFetching.value = true;
      try {
        const res = await oSService().retrieve();
        oS.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOSs();
    };

    onMounted(async () => {
      await retrieveOSs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOS) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOS = async () => {
      try {
        await oSService().delete(removeId.value);
        const message = t$('sdiApp.oS.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOSs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      oS,
      handleSyncList,
      isFetching,
      retrieveOSs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOS,
      t$,
    };
  },
});
