import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import HSMService from './hsm.service';
import { type IHSM } from '@/shared/model/hsm.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HSM',
  setup() {
    const { t: t$ } = useI18n();
    const hSMService = inject('hSMService', () => new HSMService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const hSMS: Ref<IHSM[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveHSMs = async () => {
      isFetching.value = true;
      try {
        const res = await hSMService().retrieve();
        hSMS.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveHSMs();
    };

    onMounted(async () => {
      await retrieveHSMs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IHSM) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeHSM = async () => {
      try {
        await hSMService().delete(removeId.value);
        const message = t$('sdiApp.hSM.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveHSMs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      hSMS,
      handleSyncList,
      isFetching,
      retrieveHSMs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeHSM,
      t$,
    };
  },
});
