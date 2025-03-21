export interface IRegion {
  id?: number;
  name?: string;
  code?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public name?: string,
    public code?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
