import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApplicationServerUpdate from './application-server-update.vue';
import ApplicationServerService from './application-server.service';
import AlertService from '@/shared/alert/alert.service';

type ApplicationServerUpdateComponentType = InstanceType<typeof ApplicationServerUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const applicationServerSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ApplicationServerUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ApplicationServer Management Update Component', () => {
    let comp: ApplicationServerUpdateComponentType;
    let applicationServerServiceStub: SinonStubbedInstance<ApplicationServerService>;

    beforeEach(() => {
      route = {};
      applicationServerServiceStub = sinon.createStubInstance<ApplicationServerService>(ApplicationServerService);
      applicationServerServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          applicationServerService: () => applicationServerServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ApplicationServerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.applicationServer = applicationServerSample;
        applicationServerServiceStub.update.resolves(applicationServerSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServerServiceStub.update.calledWith(applicationServerSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        applicationServerServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ApplicationServerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.applicationServer = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        applicationServerServiceStub.find.resolves(applicationServerSample);
        applicationServerServiceStub.retrieve.resolves([applicationServerSample]);

        // WHEN
        route = {
          params: {
            applicationServerId: `${applicationServerSample.id}`,
          },
        };
        const wrapper = shallowMount(ApplicationServerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.applicationServer).toMatchObject(applicationServerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        applicationServerServiceStub.find.resolves(applicationServerSample);
        const wrapper = shallowMount(ApplicationServerUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
