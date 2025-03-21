export interface IProductLine {
  id?: number;
  name?: string;
  notes?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
}

export class ProductLine implements IProductLine {
  constructor(
    public id?: number,
    public name?: string,
    public notes?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
  ) {}
}
