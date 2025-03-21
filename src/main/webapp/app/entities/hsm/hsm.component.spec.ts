import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import HSM from './hsm.vue';
import HSMService from './hsm.service';
import AlertService from '@/shared/alert/alert.service';

type HSMComponentType = InstanceType<typeof HSM>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('HSM Management Component', () => {
    let hSMServiceStub: SinonStubbedInstance<HSMService>;
    let mountOptions: MountingOptions<HSMComponentType>['global'];

    beforeEach(() => {
      hSMServiceStub = sinon.createStubInstance<HSMService>(HSMService);
      hSMServiceStub.retrieve.resolves({ headers: {} });

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
          hSMService: () => hSMServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hSMServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(HSM, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(hSMServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.hSMS[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: HSMComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(HSM, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        hSMServiceStub.retrieve.reset();
        hSMServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        hSMServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeHSM();
        await comp.$nextTick(); // clear components

        // THEN
        expect(hSMServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(hSMServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
