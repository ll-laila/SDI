import { type IModule } from '@/shared/model/module.model';
import { type IProductDeployement } from '@/shared/model/product-deployement.model';
import { type ICustomisationLevel } from '@/shared/model/customisation-level.model';

export interface IModuleDeployement {
  id?: number;
  customisationDescription?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
  module?: IModule | null;
  productDeployement?: IProductDeployement | null;
  custoLevel?: ICustomisationLevel | null;
}

export class ModuleDeployement implements IModuleDeployement {
  constructor(
    public id?: number,
    public customisationDescription?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
    public module?: IModule | null,
    public productDeployement?: IProductDeployement | null,
    public custoLevel?: ICustomisationLevel | null,
  ) {}
}
