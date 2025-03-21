import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HOSTDetails from './host-details.vue';
import HOSTService from './host.service';
import AlertService from '@/shared/alert/alert.service';

type HOSTDetailsComponentType = InstanceType<typeof HOSTDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hOSTSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('HOST Management Detail Component', () => {
    let hOSTServiceStub: SinonStubbedInstance<HOSTService>;
    let mountOptions: MountingOptions<HOSTDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      hOSTServiceStub = sinon.createStubInstance<HOSTService>(HOSTService);

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
          hOSTService: () => hOSTServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hOSTServiceStub.find.resolves(hOSTSample);
        route = {
          params: {
            hOSTId: `${123}`,
          },
        };
        const wrapper = shallowMount(HOSTDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.hOST).toMatchObject(hOSTSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hOSTServiceStub.find.resolves(hOSTSample);
        const wrapper = shallowMount(HOSTDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
