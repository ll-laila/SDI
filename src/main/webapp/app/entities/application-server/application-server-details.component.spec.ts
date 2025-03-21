import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApplicationServerDetails from './application-server-details.vue';
import ApplicationServerService from './application-server.service';
import AlertService from '@/shared/alert/alert.service';

type ApplicationServerDetailsComponentType = InstanceType<typeof ApplicationServerDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const applicationServerSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ApplicationServer Management Detail Component', () => {
    let applicationServerServiceStub: SinonStubbedInstance<ApplicationServerService>;
    let mountOptions: MountingOptions<ApplicationServerDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      applicationServerServiceStub = sinon.createStubInstance<ApplicationServerService>(ApplicationServerService);

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
          applicationServerService: () => applicationServerServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        applicationServerServiceStub.find.resolves(applicationServerSample);
        route = {
          params: {
            applicationServerId: `${123}`,
          },
        };
        const wrapper = shallowMount(ApplicationServerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.applicationServer).toMatchObject(applicationServerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        applicationServerServiceStub.find.resolves(applicationServerSample);
        const wrapper = shallowMount(ApplicationServerDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
