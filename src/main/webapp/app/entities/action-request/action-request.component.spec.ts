import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ActionRequest from './action-request.vue';
import ActionRequestService from './action-request.service';
import AlertService from '@/shared/alert/alert.service';

type ActionRequestComponentType = InstanceType<typeof ActionRequest>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ActionRequest Management Component', () => {
    let actionRequestServiceStub: SinonStubbedInstance<ActionRequestService>;
    let mountOptions: MountingOptions<ActionRequestComponentType>['global'];

    beforeEach(() => {
      actionRequestServiceStub = sinon.createStubInstance<ActionRequestService>(ActionRequestService);
      actionRequestServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          actionRequestService: () => actionRequestServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        actionRequestServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ActionRequest, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(actionRequestServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.actionRequests[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ActionRequestComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ActionRequest, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        actionRequestServiceStub.retrieve.reset();
        actionRequestServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        actionRequestServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeActionRequest();
        await comp.$nextTick(); // clear components

        // THEN
        expect(actionRequestServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(actionRequestServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
