export interface IHA {
  id?: number;
  name?: string;
  creaDate?: Date | null;
  updateDate?: Date | null;
  notes?: string | null;
}

export class HA implements IHA {
  constructor(
    public id?: number,
    public name?: string,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public notes?: string | null,
  ) {}
}
