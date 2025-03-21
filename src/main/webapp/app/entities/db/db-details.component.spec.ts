import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DBDetails from './db-details.vue';
import DBService from './db.service';
import AlertService from '@/shared/alert/alert.service';

type DBDetailsComponentType = InstanceType<typeof DBDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const dBSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('DB Management Detail Component', () => {
    let dBServiceStub: SinonStubbedInstance<DBService>;
    let mountOptions: MountingOptions<DBDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      dBServiceStub = sinon.createStubInstance<DBService>(DBService);

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
          dBService: () => dBServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        dBServiceStub.find.resolves(dBSample);
        route = {
          params: {
            dBId: `${123}`,
          },
        };
        const wrapper = shallowMount(DBDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.dB).toMatchObject(dBSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        dBServiceStub.find.resolves(dBSample);
        const wrapper = shallowMount(DBDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
