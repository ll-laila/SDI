import { type IProduct } from '@/shared/model/product.model';
import { type IDomaine } from '@/shared/model/domaine.model';

export interface IModule {
  id?: number;
  name?: string;
  code?: string;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
  product?: IProduct | null;
  domaine?: IDomaine | null;
}

export class Module implements IModule {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
    public product?: IProduct | null,
    public domaine?: IDomaine | null,
  ) {}
}
