import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HOSTUpdate from './host-update.vue';
import HOSTService from './host.service';
import AlertService from '@/shared/alert/alert.service';

type HOSTUpdateComponentType = InstanceType<typeof HOSTUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const hOSTSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<HOSTUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('HOST Management Update Component', () => {
    let comp: HOSTUpdateComponentType;
    let hOSTServiceStub: SinonStubbedInstance<HOSTService>;

    beforeEach(() => {
      route = {};
      hOSTServiceStub = sinon.createStubInstance<HOSTService>(HOSTService);
      hOSTServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          hOSTService: () => hOSTServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(HOSTUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hOST = hOSTSample;
        hOSTServiceStub.update.resolves(hOSTSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hOSTServiceStub.update.calledWith(hOSTSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        hOSTServiceStub.create.resolves(entity);
        const wrapper = shallowMount(HOSTUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.hOST = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hOSTServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        hOSTServiceStub.find.resolves(hOSTSample);
        hOSTServiceStub.retrieve.resolves([hOSTSample]);

        // WHEN
        route = {
          params: {
            hOSTId: `${hOSTSample.id}`,
          },
        };
        const wrapper = shallowMount(HOSTUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.hOST).toMatchObject(hOSTSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        hOSTServiceStub.find.resolves(hOSTSample);
        const wrapper = shallowMount(HOSTUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
