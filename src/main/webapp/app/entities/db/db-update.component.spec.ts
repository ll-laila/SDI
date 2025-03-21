import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DBUpdate from './db-update.vue';
import DBService from './db.service';
import AlertService from '@/shared/alert/alert.service';

type DBUpdateComponentType = InstanceType<typeof DBUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const dBSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DBUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('DB Management Update Component', () => {
    let comp: DBUpdateComponentType;
    let dBServiceStub: SinonStubbedInstance<DBService>;

    beforeEach(() => {
      route = {};
      dBServiceStub = sinon.createStubInstance<DBService>(DBService);
      dBServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          dBService: () => dBServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(DBUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.dB = dBSample;
        dBServiceStub.update.resolves(dBSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dBServiceStub.update.calledWith(dBSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        dBServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DBUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.dB = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dBServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        dBServiceStub.find.resolves(dBSample);
        dBServiceStub.retrieve.resolves([dBSample]);

        // WHEN
        route = {
          params: {
            dBId: `${dBSample.id}`,
          },
        };
        const wrapper = shallowMount(DBUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.dB).toMatchObject(dBSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        dBServiceStub.find.resolves(dBSample);
        const wrapper = shallowMount(DBUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
