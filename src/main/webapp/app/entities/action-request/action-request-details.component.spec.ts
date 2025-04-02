import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ActionRequestDetails from './action-request-details.vue';
import ActionRequestService from './action-request.service';
import AlertService from '@/shared/alert/alert.service';

type ActionRequestDetailsComponentType = InstanceType<typeof ActionRequestDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const actionRequestSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ActionRequest Management Detail Component', () => {
    let actionRequestServiceStub: SinonStubbedInstance<ActionRequestService>;
    let mountOptions: MountingOptions<ActionRequestDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      actionRequestServiceStub = sinon.createStubInstance<ActionRequestService>(ActionRequestService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          actionRequestService: () => actionRequestServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        actionRequestServiceStub.find.resolves(actionRequestSample);
        route = {
          params: {
            actionRequestId: `${123}`,
          },
        };
        const wrapper = shallowMount(ActionRequestDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.actionRequest).toMatchObject(actionRequestSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        actionRequestServiceStub.find.resolves(actionRequestSample);
        const wrapper = shallowMount(ActionRequestDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
