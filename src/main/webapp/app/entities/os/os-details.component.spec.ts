import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OSDetails from './os-details.vue';
import OSService from './os.service';
import AlertService from '@/shared/alert/alert.service';

type OSDetailsComponentType = InstanceType<typeof OSDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const oSSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('OS Management Detail Component', () => {
    let oSServiceStub: SinonStubbedInstance<OSService>;
    let mountOptions: MountingOptions<OSDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      oSServiceStub = sinon.createStubInstance<OSService>(OSService);

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
          oSService: () => oSServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        oSServiceStub.find.resolves(oSSample);
        route = {
          params: {
            oSId: `${123}`,
          },
        };
        const wrapper = shallowMount(OSDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.oS).toMatchObject(oSSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        oSServiceStub.find.resolves(oSSample);
        const wrapper = shallowMount(OSDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
