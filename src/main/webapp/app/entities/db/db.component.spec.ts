import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import DB from './db.vue';
import DBService from './db.service';
import AlertService from '@/shared/alert/alert.service';

type DBComponentType = InstanceType<typeof DB>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('DB Management Component', () => {
    let dBServiceStub: SinonStubbedInstance<DBService>;
    let mountOptions: MountingOptions<DBComponentType>['global'];

    beforeEach(() => {
      dBServiceStub = sinon.createStubInstance<DBService>(DBService);
      dBServiceStub.retrieve.resolves({ headers: {} });

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
          dBService: () => dBServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        dBServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(DB, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(dBServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.dBS[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: DBComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(DB, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        dBServiceStub.retrieve.reset();
        dBServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        dBServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeDB();
        await comp.$nextTick(); // clear components

        // THEN
        expect(dBServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(dBServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
