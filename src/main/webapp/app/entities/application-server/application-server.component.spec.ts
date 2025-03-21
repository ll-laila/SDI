import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ApplicationServer from './application-server.vue';
import ApplicationServerService from './application-server.service';
import AlertService from '@/shared/alert/alert.service';

type ApplicationServerComponentType = InstanceType<typeof ApplicationServer>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ApplicationServer Management Component', () => {
    let applicationServerServiceStub: SinonStubbedInstance<ApplicationServerService>;
    let mountOptions: MountingOptions<ApplicationServerComponentType>['global'];

    beforeEach(() => {
      applicationServerServiceStub = sinon.createStubInstance<ApplicationServerService>(ApplicationServerService);
      applicationServerServiceStub.retrieve.resolves({ headers: {} });

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
          applicationServerService: () => applicationServerServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        applicationServerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ApplicationServer, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(applicationServerServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.applicationServers[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ApplicationServerComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ApplicationServer, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        applicationServerServiceStub.retrieve.reset();
        applicationServerServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        applicationServerServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeApplicationServer();
        await comp.$nextTick(); // clear components

        // THEN
        expect(applicationServerServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(applicationServerServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
