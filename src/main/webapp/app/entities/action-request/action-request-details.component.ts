import { defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ActionRequestService from './action-request.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ActionRequestDetails',
  setup() {
    const dateFormat = useDateFormat();
    const actionRequestService = inject('actionRequestService', () => new ActionRequestService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const actionRequest = ref({});
    const jsonModal = ref(null);
    const copySuccess = ref(false);

    const retrieveActionRequest = async actionRequestId => {
      try {
        const res = await actionRequestService().find(actionRequestId);
        actionRequest.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.actionRequestId) {
      retrieveActionRequest(route.params.actionRequestId);
    }

    // JSON formatting functions
    const formatJson = (jsonData) => {
      try {
        // If it's already a string, parse it first
        const data = typeof jsonData === 'string' ? JSON.parse(jsonData) : jsonData;
        return JSON.stringify(data, null, 2);
      } catch (e) {
        return jsonData; // Return as is if it's not valid JSON
      }
    };

    const getJsonPreview = (jsonData) => {
      try {
        // If it's already a string, parse it first
        const data = typeof jsonData === 'string' ? JSON.parse(jsonData) : jsonData;
        const str = JSON.stringify(data);
        return str.length > 50 ? str.substring(0, 50) + '...' : str;
      } catch (e) {
        return jsonData; // Return as is if it's not valid JSON
      }
    };

    // JSON Modal functions
    const openJsonModal = () => {
      copySuccess.value = false;
      jsonModal.value.show();
    };

    const closeJsonModal = () => {
      jsonModal.value.hide();
    };

    const copyJsonToClipboard = () => {
      if (actionRequest.value && actionRequest.value.newData) {
        try {
          const formattedJson = formatJson(actionRequest.value.newData);
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
    const getStatusClass = (status) => {
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
      ...dateFormat,
      alertService,
      actionRequest,
      ...dataUtils,
      previousState,
      t$: useI18n().t,
      formatJson,
      getJsonPreview,
      jsonModal,
      openJsonModal,
      closeJsonModal,
      copyJsonToClipboard,
      copySuccess,
      getStatusClass,
    };
  },
});
