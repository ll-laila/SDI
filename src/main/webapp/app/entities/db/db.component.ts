import { type Ref, defineComponent, inject, onMounted, ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DBService from './db.service';
import { type IDB } from '@/shared/model/db.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DB',
  setup() {
    const { t: t$ } = useI18n();
    const dBService = inject('dBService', () => new DBService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dBS: Ref<IDB[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveDBs = async () => {
      isFetching.value = true;
      try {
        const res = await dBService().retrieve();
        dBS.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveDBs();
    };

    onMounted(async () => {
      await retrieveDBs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IDB) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeDB = async () => {
      try {
        await dBService().delete(removeId.value);
        const message = t$('sdiApp.dB.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveDBs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      dBS,
      handleSyncList,
      isFetching,
      retrieveDBs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeDB,
      t$,
    };
  },
});
