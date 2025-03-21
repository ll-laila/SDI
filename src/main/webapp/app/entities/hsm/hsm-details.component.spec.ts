import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HSMDetails from './hsm-details.vue';
import HSMService from './hsm.service';
import AlertService from '@/shared/alert/alert.service';

type HSMDetailsComponentType = InstanceType<typeof HSMDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hSMSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('HSM Management Detail Component', () => {
    let hSMServiceStub: SinonStubbedInstance<HSMService>;
    let mountOptions: MountingOptions<HSMDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      hSMServiceStub = sinon.createStubInstance<HSMService>(HSMService);

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
          hSMService: () => hSMServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hSMServiceStub.find.resolves(hSMSample);
        route = {
          params: {
            hSMId: `${123}`,
          },
        };
        const wrapper = shallowMount(HSMDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.hSM).toMatchObject(hSMSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hSMServiceStub.find.resolves(hSMSample);
        const wrapper = shallowMount(HSMDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
