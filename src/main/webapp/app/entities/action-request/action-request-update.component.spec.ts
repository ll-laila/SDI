import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import ActionRequestUpdate from './action-request-update.vue';
import ActionRequestService from './action-request.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type ActionRequestUpdateComponentType = InstanceType<typeof ActionRequestUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const actionRequestSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ActionRequestUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ActionRequest Management Update Component', () => {
    let comp: ActionRequestUpdateComponentType;
    let actionRequestServiceStub: SinonStubbedInstance<ActionRequestService>;

    beforeEach(() => {
      route = {};
      actionRequestServiceStub = sinon.createStubInstance<ActionRequestService>(ActionRequestService);
      actionRequestServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          actionRequestService: () => actionRequestServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(ActionRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ActionRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.actionRequest = actionRequestSample;
        actionRequestServiceStub.update.resolves(actionRequestSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(actionRequestServiceStub.update.calledWith(actionRequestSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        actionRequestServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ActionRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.actionRequest = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(actionRequestServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        actionRequestServiceStub.find.resolves(actionRequestSample);
        actionRequestServiceStub.retrieve.resolves([actionRequestSample]);

        // WHEN
        route = {
          params: {
            actionRequestId: `${actionRequestSample.id}`,
          },
        };
        const wrapper = shallowMount(ActionRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.actionRequest).toMatchObject(actionRequestSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        actionRequestServiceStub.find.resolves(actionRequestSample);
        const wrapper = shallowMount(ActionRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
