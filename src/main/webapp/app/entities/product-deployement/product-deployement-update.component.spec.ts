import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ProductDeployementUpdate from './product-deployement-update.vue';
import ProductDeployementService from './product-deployement.service';
import AlertService from '@/shared/alert/alert.service';

import ClientService from '@/entities/client/client.service';
import ProductService from '@/entities/product/product.service';
import DeployementTypeService from '@/entities/deployement-type/deployement-type.service';
import HAService from '@/entities/ha/ha.service';
import HSMService from '@/entities/hsm/hsm.service';
import DBService from '@/entities/db/db.service';
import HOSTService from '@/entities/host/host.service';
import ApplicationServerService from '@/entities/application-server/application-server.service';
import OSService from '@/entities/os/os.service';

type ProductDeployementUpdateComponentType = InstanceType<typeof ProductDeployementUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const productDeployementSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ProductDeployementUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ProductDeployement Management Update Component', () => {
    let comp: ProductDeployementUpdateComponentType;
    let productDeployementServiceStub: SinonStubbedInstance<ProductDeployementService>;

    beforeEach(() => {
      route = {};
      productDeployementServiceStub = sinon.createStubInstance<ProductDeployementService>(ProductDeployementService);
      productDeployementServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          productDeployementService: () => productDeployementServiceStub,
          clientService: () =>
            sinon.createStubInstance<ClientService>(ClientService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          deployementTypeService: () =>
            sinon.createStubInstance<DeployementTypeService>(DeployementTypeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          hAService: () =>
            sinon.createStubInstance<HAService>(HAService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          hSMService: () =>
            sinon.createStubInstance<HSMService>(HSMService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          dBService: () =>
            sinon.createStubInstance<DBService>(DBService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          hOSTService: () =>
            sinon.createStubInstance<HOSTService>(HOSTService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          applicationServerService: () =>
            sinon.createStubInstance<ApplicationServerService>(ApplicationServerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          oSService: () =>
            sinon.createStubInstance<OSService>(OSService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ProductDeployementUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.productDeployement = productDeployementSample;
        productDeployementServiceStub.update.resolves(productDeployementSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productDeployementServiceStub.update.calledWith(productDeployementSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        productDeployementServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ProductDeployementUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.productDeployement = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productDeployementServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        productDeployementServiceStub.find.resolves(productDeployementSample);
        productDeployementServiceStub.retrieve.resolves([productDeployementSample]);

        // WHEN
        route = {
          params: {
            productDeployementId: `${productDeployementSample.id}`,
          },
        };
        const wrapper = shallowMount(ProductDeployementUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.productDeployement).toMatchObject(productDeployementSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        productDeployementServiceStub.find.resolves(productDeployementSample);
        const wrapper = shallowMount(ProductDeployementUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
