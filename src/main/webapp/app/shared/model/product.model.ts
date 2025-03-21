import { type IProductLine } from '@/shared/model/product-line.model';

export interface IProduct {
  id?: number;
  name?: string;
  code?: string | null;
  logo?: string | null;
  notes?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  productline?: IProductLine | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public logo?: string | null,
    public notes?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public productline?: IProductLine | null,
  ) {}
}
