import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import HOST from './host.vue';
import HOSTService from './host.service';
import AlertService from '@/shared/alert/alert.service';

type HOSTComponentType = InstanceType<typeof HOST>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('HOST Management Component', () => {
    let hOSTServiceStub: SinonStubbedInstance<HOSTService>;
    let mountOptions: MountingOptions<HOSTComponentType>['global'];

    beforeEach(() => {
      hOSTServiceStub = sinon.createStubInstance<HOSTService>(HOSTService);
      hOSTServiceStub.retrieve.resolves({ headers: {} });

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
          hOSTService: () => hOSTServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        hOSTServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(HOST, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(hOSTServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.hOSTS[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: HOSTComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(HOST, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        hOSTServiceStub.retrieve.reset();
        hOSTServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        hOSTServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeHOST();
        await comp.$nextTick(); // clear components

        // THEN
        expect(hOSTServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(hOSTServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
