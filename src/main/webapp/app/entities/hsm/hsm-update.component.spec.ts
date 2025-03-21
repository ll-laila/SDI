import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HSMUpdate from './hsm-update.vue';
import HSMService from './hsm.service';
import AlertService from '@/shared/alert/alert.service';

type HSMUpdateComponentType = InstanceType<typeof HSMUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hSMSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<HSMUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('HSM Management Update Component', () => {
    let comp: HSMUpdateComponentType;
    let hSMServiceStub: SinonStubbedInstance<HSMService>;

    beforeEach(() => {
      route = {};
      hSMServiceStub = sinon.createStubInstance<HSMService>(HSMService);
      hSMServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          hSMService: () => hSMServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(HSMUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hSM = hSMSample;
        hSMServiceStub.update.resolves(hSMSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hSMServiceStub.update.calledWith(hSMSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        hSMServiceStub.create.resolves(entity);
        const wrapper = shallowMount(HSMUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hSM = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hSMServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        hSMServiceStub.find.resolves(hSMSample);
        hSMServiceStub.retrieve.resolves([hSMSample]);

        // WHEN
        route = {
          params: {
            hSMId: `${hSMSample.id}`,
          },
        };
        const wrapper = shallowMount(HSMUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.hSM).toMatchObject(hSMSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hSMServiceStub.find.resolves(hSMSample);
        const wrapper = shallowMount(HSMUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
