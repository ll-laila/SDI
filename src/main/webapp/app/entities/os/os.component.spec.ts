import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import OS from './os.vue';
import OSService from './os.service';
import AlertService from '@/shared/alert/alert.service';

type OSComponentType = InstanceType<typeof OS>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('OS Management Component', () => {
    let oSServiceStub: SinonStubbedInstance<OSService>;
    let mountOptions: MountingOptions<OSComponentType>['global'];

    beforeEach(() => {
      oSServiceStub = sinon.createStubInstance<OSService>(OSService);
      oSServiceStub.retrieve.resolves({ headers: {} });

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
          oSService: () => oSServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        oSServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(OS, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(oSServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.oS[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: OSComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(OS, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        oSServiceStub.retrieve.reset();
        oSServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        oSServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeOS();
        await comp.$nextTick(); // clear components

        // THEN
        expect(oSServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(oSServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
