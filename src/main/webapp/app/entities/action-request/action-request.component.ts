import { defineComponent, inject, onMounted, ref, reactive } from 'vue';
import { useI18n } from 'vue-i18n';

import ActionRequestService from './action-request.service';
import { type IActionRequest } from '@/shared/model/action-request.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActionRequest',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const dataUtils = useDataUtils();
    const actionRequestService = inject('actionRequestService', () => new ActionRequestService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const actionRequests = ref([]);
    const isFetching = ref(false);
    const jsonModal = ref(null);
    const selectedActionRequest = ref(null);
    const copySuccess = ref(false);

    const clear = () => {};

    const retrieveActionRequests = async () => {
      isFetching.value = true;
      try {
        const res = await actionRequestService().retrieve();
        actionRequests.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveActionRequests();
    };

    onMounted(async () => {
      await retrieveActionRequests();
    });

    const removeId = ref(null);
    const removeEntity = ref(null);
    const prepareRemove = instance => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeActionRequest = async () => {
      try {
        await actionRequestService().delete(removeId.value);
        const message = t$('sdiApp.actionRequest.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveActionRequests();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    // JSON formatting functions
    const formatJson = jsonData => {
      try {
        // If it's already a string, parse it first
        const data = typeof jsonData === 'string' ? JSON.parse(jsonData) : jsonData;
        return JSON.stringify(data, null, 2);
      } catch (e) {
        return jsonData; // Return as is if it's not valid JSON
      }
    };

    const getJsonPreview = jsonData => {
      try {
        // If it's already a string, parse it first
        const data = typeof jsonData === 'string' ? JSON.parse(jsonData) : jsonData;
        const str = JSON.stringify(data);
        return str.length > 30 ? str.substring(0, 30) + '...' : str;
      } catch (e) {
        return jsonData; // Return as is if it's not valid JSON
      }
    };

    // JSON Modal functions
    const openJsonModal = actionRequest => {
      selectedActionRequest.value = actionRequest;
      copySuccess.value = false;
      jsonModal.value.show();
    };

    const closeJsonModal = () => {
      jsonModal.value.hide();
      selectedActionRequest.value = null;
    };

    const copyJsonToClipboard = () => {
      if (selectedActionRequest.value) {
        try {
          const formattedJson = formatJson(selectedActionRequest.value.newData);
          navigator.clipboard.writeText(formattedJson).then(() => {
            copySuccess.value = true;
            setTimeout(() => {
              copySuccess.value = false;
            }, 2000);
          });
        } catch (e) {
          alertService.showInfo('Failed to copy to clipboard', { variant: 'danger' });
        }
      }
    };

    // Status color coding
    const getStatusClass = status => {
      switch (status) {
        case 'APPROVED':
          return 'status-approved';
        case 'REJECTED':
          return 'status-rejected';
        case 'PENDING':
          return 'status-pending';
        default:
          return '';
      }
    };

    return {
      actionRequests,
      handleSyncList,
      isFetching,
      retrieveActionRequests,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeActionRequest,
      t$,
      ...dataUtils,
      formatJson,
      getJsonPreview,
      jsonModal,
      selectedActionRequest,
      openJsonModal,
      closeJsonModal,
      copyJsonToClipboard,
      copySuccess,
      getStatusClass,
    };
  },
});
