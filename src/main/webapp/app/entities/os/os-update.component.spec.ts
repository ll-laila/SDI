import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OSUpdate from './os-update.vue';
import OSService from './os.service';
import AlertService from '@/shared/alert/alert.service';

type OSUpdateComponentType = InstanceType<typeof OSUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const oSSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OSUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('OS Management Update Component', () => {
    let comp: OSUpdateComponentType;
    let oSServiceStub: SinonStubbedInstance<OSService>;

    beforeEach(() => {
      route = {};
      oSServiceStub = sinon.createStubInstance<OSService>(OSService);
      oSServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          oSService: () => oSServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(OSUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.oS = oSSample;
        oSServiceStub.update.resolves(oSSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(oSServiceStub.update.calledWith(oSSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        oSServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OSUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.oS = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(oSServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        oSServiceStub.find.resolves(oSSample);
        oSServiceStub.retrieve.resolves([oSSample]);

        // WHEN
        route = {
          params: {
            oSId: `${oSSample.id}`,
          },
        };
        const wrapper = shallowMount(OSUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.oS).toMatchObject(oSSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        oSServiceStub.find.resolves(oSSample);
        const wrapper = shallowMount(OSUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
