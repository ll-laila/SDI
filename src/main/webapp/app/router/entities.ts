import { Authority } from '@/shared/security/authority';
const Entities = () => import('@/entities/entities.vue');

const DeployementType = () => import('@/entities/deployement-type/deployement-type.vue');
const DeployementTypeUpdate = () => import('@/entities/deployement-type/deployement-type-update.vue');
const DeployementTypeDetails = () => import('@/entities/deployement-type/deployement-type-details.vue');

const CustomisationLevel = () => import('@/entities/customisation-level/customisation-level.vue');
const CustomisationLevelUpdate = () => import('@/entities/customisation-level/customisation-level-update.vue');
const CustomisationLevelDetails = () => import('@/entities/customisation-level/customisation-level-details.vue');

const Product = () => import('@/entities/product/product.vue');
const ProductUpdate = () => import('@/entities/product/product-update.vue');
const ProductDetails = () => import('@/entities/product/product-details.vue');

const ProductLine = () => import('@/entities/product-line/product-line.vue');
const ProductLineUpdate = () => import('@/entities/product-line/product-line-update.vue');
const ProductLineDetails = () => import('@/entities/product-line/product-line-details.vue');

const Module = () => import('@/entities/module/module.vue');
const ModuleUpdate = () => import('@/entities/module/module-update.vue');
const ModuleDetails = () => import('@/entities/module/module-details.vue');

const Domaine = () => import('@/entities/domaine/domaine.vue');
const DomaineUpdate = () => import('@/entities/domaine/domaine-update.vue');
const DomaineDetails = () => import('@/entities/domaine/domaine-details.vue');

const Feature = () => import('@/entities/feature/feature.vue');
const FeatureUpdate = () => import('@/entities/feature/feature-update.vue');
const FeatureDetails = () => import('@/entities/feature/feature-details.vue');

const ProductDeployement = () => import('@/entities/product-deployement/product-deployement.vue');
const ProductDeployementUpdate = () => import('@/entities/product-deployement/product-deployement-update.vue');
const ProductDeployementDetails = () => import('@/entities/product-deployement/product-deployement-details.vue');

const ModuleDeployement = () => import('@/entities/module-deployement/module-deployement.vue');
const ModuleDeployementUpdate = () => import('@/entities/module-deployement/module-deployement-update.vue');
const ModuleDeployementDetails = () => import('@/entities/module-deployement/module-deployement-details.vue');

const FeatureDeployement = () => import('@/entities/feature-deployement/feature-deployement.vue');
const FeatureDeployementUpdate = () => import('@/entities/feature-deployement/feature-deployement-update.vue');
const FeatureDeployementDetails = () => import('@/entities/feature-deployement/feature-deployement-details.vue');

const Region = () => import('@/entities/region/region.vue');
const RegionUpdate = () => import('@/entities/region/region-update.vue');
const RegionDetails = () => import('@/entities/region/region-details.vue');

const ClientCertification = () => import('@/entities/client-certification/client-certification.vue');
const ClientCertificationUpdate = () => import('@/entities/client-certification/client-certification-update.vue');
const ClientCertificationDetails = () => import('@/entities/client-certification/client-certification-details.vue');

const ClientSize = () => import('@/entities/client-size/client-size.vue');
const ClientSizeUpdate = () => import('@/entities/client-size/client-size-update.vue');
const ClientSizeDetails = () => import('@/entities/client-size/client-size-details.vue');

const Client = () => import('@/entities/client/client.vue');
const ClientUpdate = () => import('@/entities/client/client-update.vue');
const ClientDetails = () => import('@/entities/client/client-details.vue');

const ClientType = () => import('@/entities/client-type/client-type.vue');
const ClientTypeUpdate = () => import('@/entities/client-type/client-type-update.vue');
const ClientTypeDetails = () => import('@/entities/client-type/client-type-details.vue');

const Certification = () => import('@/entities/certification/certification.vue');
const CertificationUpdate = () => import('@/entities/certification/certification-update.vue');
const CertificationDetails = () => import('@/entities/certification/certification-details.vue');

const ClientEvent = () => import('@/entities/client-event/client-event.vue');
const ClientEventUpdate = () => import('@/entities/client-event/client-event-update.vue');
const ClientEventDetails = () => import('@/entities/client-event/client-event-details.vue');

const ClientEventType = () => import('@/entities/client-event-type/client-event-type.vue');
const ClientEventTypeUpdate = () => import('@/entities/client-event-type/client-event-type-update.vue');
const ClientEventTypeDetails = () => import('@/entities/client-event-type/client-event-type-details.vue');

const Country = () => import('@/entities/country/country.vue');
const CountryUpdate = () => import('@/entities/country/country-update.vue');
const CountryDetails = () => import('@/entities/country/country-details.vue');

const HA = () => import('@/entities/ha/ha.vue');
const HAUpdate = () => import('@/entities/ha/ha-update.vue');
const HADetails = () => import('@/entities/ha/ha-details.vue');

const DB = () => import('@/entities/db/db.vue');
const DBUpdate = () => import('@/entities/db/db-update.vue');
const DBDetails = () => import('@/entities/db/db-details.vue');

const HOST = () => import('@/entities/host/host.vue');
const HOSTUpdate = () => import('@/entities/host/host-update.vue');
const HOSTDetails = () => import('@/entities/host/host-details.vue');

const OS = () => import('@/entities/os/os.vue');
const OSUpdate = () => import('@/entities/os/os-update.vue');
const OSDetails = () => import('@/entities/os/os-details.vue');

const HSM = () => import('@/entities/hsm/hsm.vue');
const HSMUpdate = () => import('@/entities/hsm/hsm-update.vue');
const HSMDetails = () => import('@/entities/hsm/hsm-details.vue');

const ApplicationServer = () => import('@/entities/application-server/application-server.vue');
const ApplicationServerUpdate = () => import('@/entities/application-server/application-server-update.vue');
const ApplicationServerDetails = () => import('@/entities/application-server/application-server-details.vue');

const ActionRequest = () => import('@/entities/action-request/action-request.vue');
const ActionRequestUpdate = () => import('@/entities/action-request/action-request-update.vue');
const ActionRequestDetails = () => import('@/entities/action-request/action-request-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'deployement-type',
      name: 'DeployementType',
      component: DeployementType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deployement-type/new',
      name: 'DeployementTypeCreate',
      component: DeployementTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deployement-type/:deployementTypeId/edit',
      name: 'DeployementTypeEdit',
      component: DeployementTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'deployement-type/:deployementTypeId/view',
      name: 'DeployementTypeView',
      component: DeployementTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customisation-level',
      name: 'CustomisationLevel',
      component: CustomisationLevel,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customisation-level/new',
      name: 'CustomisationLevelCreate',
      component: CustomisationLevelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customisation-level/:customisationLevelId/edit',
      name: 'CustomisationLevelEdit',
      component: CustomisationLevelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customisation-level/:customisationLevelId/view',
      name: 'CustomisationLevelView',
      component: CustomisationLevelDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-line',
      name: 'ProductLine',
      component: ProductLine,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-line/new',
      name: 'ProductLineCreate',
      component: ProductLineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-line/:productLineId/edit',
      name: 'ProductLineEdit',
      component: ProductLineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-line/:productLineId/view',
      name: 'ProductLineView',
      component: ProductLineDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module',
      name: 'Module',
      component: Module,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module/new',
      name: 'ModuleCreate',
      component: ModuleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module/:moduleId/edit',
      name: 'ModuleEdit',
      component: ModuleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module/:moduleId/view',
      name: 'ModuleView',
      component: ModuleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'domaine',
      name: 'Domaine',
      component: Domaine,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'domaine/new',
      name: 'DomaineCreate',
      component: DomaineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'domaine/:domaineId/edit',
      name: 'DomaineEdit',
      component: DomaineUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'domaine/:domaineId/view',
      name: 'DomaineView',
      component: DomaineDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature',
      name: 'Feature',
      component: Feature,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature/new',
      name: 'FeatureCreate',
      component: FeatureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature/:featureId/edit',
      name: 'FeatureEdit',
      component: FeatureUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature/:featureId/view',
      name: 'FeatureView',
      component: FeatureDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-deployement',
      name: 'ProductDeployement',
      component: ProductDeployement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-deployement/new',
      name: 'ProductDeployementCreate',
      component: ProductDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-deployement/:productDeployementId/edit',
      name: 'ProductDeployementEdit',
      component: ProductDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product-deployement/:productDeployementId/view',
      name: 'ProductDeployementView',
      component: ProductDeployementDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module-deployement',
      name: 'ModuleDeployement',
      component: ModuleDeployement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module-deployement/new',
      name: 'ModuleDeployementCreate',
      component: ModuleDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module-deployement/:moduleDeployementId/edit',
      name: 'ModuleDeployementEdit',
      component: ModuleDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'module-deployement/:moduleDeployementId/view',
      name: 'ModuleDeployementView',
      component: ModuleDeployementDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature-deployement',
      name: 'FeatureDeployement',
      component: FeatureDeployement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature-deployement/new',
      name: 'FeatureDeployementCreate',
      component: FeatureDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature-deployement/:featureDeployementId/edit',
      name: 'FeatureDeployementEdit',
      component: FeatureDeployementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'feature-deployement/:featureDeployementId/view',
      name: 'FeatureDeployementView',
      component: FeatureDeployementDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region',
      name: 'Region',
      component: Region,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/new',
      name: 'RegionCreate',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/edit',
      name: 'RegionEdit',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/view',
      name: 'RegionView',
      component: RegionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-certification',
      name: 'ClientCertification',
      component: ClientCertification,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-certification/new',
      name: 'ClientCertificationCreate',
      component: ClientCertificationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-certification/:clientCertificationId/edit',
      name: 'ClientCertificationEdit',
      component: ClientCertificationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-certification/:clientCertificationId/view',
      name: 'ClientCertificationView',
      component: ClientCertificationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-size',
      name: 'ClientSize',
      component: ClientSize,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-size/new',
      name: 'ClientSizeCreate',
      component: ClientSizeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-size/:clientSizeId/edit',
      name: 'ClientSizeEdit',
      component: ClientSizeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-size/:clientSizeId/view',
      name: 'ClientSizeView',
      component: ClientSizeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-type',
      name: 'ClientType',
      component: ClientType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-type/new',
      name: 'ClientTypeCreate',
      component: ClientTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-type/:clientTypeId/edit',
      name: 'ClientTypeEdit',
      component: ClientTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-type/:clientTypeId/view',
      name: 'ClientTypeView',
      component: ClientTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'certification',
      name: 'Certification',
      component: Certification,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'certification/new',
      name: 'CertificationCreate',
      component: CertificationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'certification/:certificationId/edit',
      name: 'CertificationEdit',
      component: CertificationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'certification/:certificationId/view',
      name: 'CertificationView',
      component: CertificationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event',
      name: 'ClientEvent',
      component: ClientEvent,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event/new',
      name: 'ClientEventCreate',
      component: ClientEventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event/:clientEventId/edit',
      name: 'ClientEventEdit',
      component: ClientEventUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event/:clientEventId/view',
      name: 'ClientEventView',
      component: ClientEventDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event-type',
      name: 'ClientEventType',
      component: ClientEventType,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event-type/new',
      name: 'ClientEventTypeCreate',
      component: ClientEventTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event-type/:clientEventTypeId/edit',
      name: 'ClientEventTypeEdit',
      component: ClientEventTypeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client-event-type/:clientEventTypeId/view',
      name: 'ClientEventTypeView',
      component: ClientEventTypeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country',
      name: 'Country',
      component: Country,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/new',
      name: 'CountryCreate',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/edit',
      name: 'CountryEdit',
      component: CountryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'country/:countryId/view',
      name: 'CountryView',
      component: CountryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ha',
      name: 'HA',
      component: HA,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ha/new',
      name: 'HACreate',
      component: HAUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ha/:hAId/edit',
      name: 'HAEdit',
      component: HAUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ha/:hAId/view',
      name: 'HAView',
      component: HADetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'db',
      name: 'DB',
      component: DB,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'db/new',
      name: 'DBCreate',
      component: DBUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'db/:dBId/edit',
      name: 'DBEdit',
      component: DBUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'db/:dBId/view',
      name: 'DBView',
      component: DBDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'host',
      name: 'HOST',
      component: HOST,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'host/new',
      name: 'HOSTCreate',
      component: HOSTUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'host/:hOSTId/edit',
      name: 'HOSTEdit',
      component: HOSTUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'host/:hOSTId/view',
      name: 'HOSTView',
      component: HOSTDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'os',
      name: 'OS',
      component: OS,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'os/new',
      name: 'OSCreate',
      component: OSUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'os/:oSId/edit',
      name: 'OSEdit',
      component: OSUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'os/:oSId/view',
      name: 'OSView',
      component: OSDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hsm',
      name: 'HSM',
      component: HSM,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hsm/new',
      name: 'HSMCreate',
      component: HSMUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hsm/:hSMId/edit',
      name: 'HSMEdit',
      component: HSMUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'hsm/:hSMId/view',
      name: 'HSMView',
      component: HSMDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-server',
      name: 'ApplicationServer',
      component: ApplicationServer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-server/new',
      name: 'ApplicationServerCreate',
      component: ApplicationServerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-server/:applicationServerId/edit',
      name: 'ApplicationServerEdit',
      component: ApplicationServerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'application-server/:applicationServerId/view',
      name: 'ApplicationServerView',
      component: ApplicationServerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'action-request',
      name: 'ActionRequest',
      component: ActionRequest,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'action-request/new',
      name: 'ActionRequestCreate',
      component: ActionRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'action-request/:actionRequestId/edit',
      name: 'ActionRequestEdit',
      component: ActionRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'action-request/:actionRequestId/view',
      name: 'ActionRequestView',
      component: ActionRequestDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
